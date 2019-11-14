package in.vnl.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.vnl.common.Common;
import in.vnl.common.UdpClient;
import in.vnl.common.UdpServer;
import in.vnl.model.Event;
import in.vnl.model.Inventory;
import in.vnl.model.Nodes;
import in.vnl.model.TmdasDevice;
import in.vnl.model.TmdasEvent;
import in.vnl.model.TrglDevice;
import in.vnl.model.TrglEvent;
import in.vnl.model.UgsDevice;
import in.vnl.model.UgsEvent;
import in.vnl.model.Command;
import in.vnl.model.Cue;
import in.vnl.repository.CommandService;
import in.vnl.repository.CueService;
import in.vnl.repository.EventService;
import in.vnl.repository.NodesService;



@RestController
@RequestMapping("/event")
public class EventController 
{
	public static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private EventService es;
	
	
	@Autowired
	private Environment env;
	
	@Autowired
	private NodesService ns;
	
	@Autowired
	private CommandService cs;
	
	@Autowired
	private CueService cues;
	
	
	@Autowired
	private Common com;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	
	@RequestMapping("/ugs/event")
	public void ugsEvent(HttpServletRequest request,@RequestBody UgsEvent ue) 
	{
		logger.info("inside method : ugsEvent" );
		logger.debug("Event Type "+ue.getEVENT_TYPE());
		logger.debug("request ip adrress "+request.getRemoteAddr());
		ue.setDEVICE_IP(request.getRemoteAddr());
		ue.setIp(request.getRemoteAddr());
		switch(ue.getEVENT_TYPE()) 
		{
			case 1:
				//wirteEventToFile(ue,"UGS");
				break;
			case 2:
				updateUgsDeviceStatus(ue,"UGS");
				break;
			case 3:
				updateUgsDeviceStatus(ue,"UGS");
				break;
			case 4:
				addRemoveUgsDevice(ue,true);
				break;
			case 5:
				addRemoveUgsDevice(ue,false);
				break;
			case 7:
				//wirteEventToFile(ue,"UGS");
				com.sendAdu("UGS","ADU_ON");
				break;
			case 9:
			try {
				//addRemoveUgsDevice(ue,false);
				Cue obj = new Cue();
				String type = ns.getNodeDetailByNodeType("UGS").getDisplayName();
				obj.setCueId(ue.getTRANS_ID());
				obj.setReportedBy(type);
				obj.setReportedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(ue.getTIME_STAMP()));
				obj.setSource(type);
				obj.setDetail(ue.getTRANS_ID()+","+ue.getLatitude()+","+ue.getLatitude()+","+ue.getTIMEOUT());
				obj.setNodeType("UGS");
				cues.saveCue(obj);
				this.template.convertAndSend("/topic/cue", obj);
			} catch (Exception e) {
				logger.info("Error Method : ugsEvent "+e.getMessage());
			}
			break;
			
		}
		
		createEventObject(ue,"UGS",ue.getTIME_STAMP(),ue.getEVENT_TYPE());
		logger.info("Exiting Method : ugsEvent");
		
	}
	
	@RequestMapping("/trgl")
	public void trglEvent(HttpServletRequest request,@RequestBody TrglEvent te) 
	{
		logger.info("inside method : trglEvent" );
		logger.debug("Event Type "+te.getEVENT_TYPE());
		switch(te.getEVENT_TYPE()) 
		{
			case 1:
				//wirteEventToFile(te,"TRGL");
				break;
			case 2:
				updateTrglDeviceStatus(te,"TRGL");
				break;
			case 3:
				updateTrglDeviceStatus(te,"TRGL");
				break;
			case 4:
				addRemoveTrglDevice(te,true);
				break;
			case 5:
				addRemoveTrglDevice(te,false);
				break;
			case 6:
				//wirteEventToFile(te,"TRGL");
				break;
			case 7:
				//wirteEventToFile(te,"TRGL");
				com.sendAdu("TRGL","ADU_ON");
				break;
			
		}
		createEventObject(te,"TRGL",te.getTIME_STAMP(),te.getEVENT_TYPE());
		logger.info("Exiting Method : trglEvent");
	}
	
	
	@RequestMapping("/tmdas")
	public void tmdasEvent(HttpServletRequest request,@RequestBody TmdasEvent te) 
	{
		//te.setIp(request.getRemoteAddr());
		
		logger.info("inside method : tmdasEvent" );
		logger.debug("Event Type "+te.getEVENT_TYPE());
		switch(te.getEVENT_TYPE()) 
		{
			case 1:
				//wirteEventToFile(te,"TMDAS");
				break;
			case 2:
				updateTmdasDeviceStatus(te,"TMDAS");
				break;
			case 3:
				updateTmdasDeviceStatus(te,"TMDAS");
				break;
			case 4:
				addRemoveTmdasDevice(te,true);
				break;
			case 5:
				addRemoveTmdasDevice(te,false);
				break;
			case 6:
				//wirteEventToFile(te,"TMDAS");
				break;
			case 7:
				//wirteEventToFile(te,"TMDAS");
				com.sendAdu("TMDAS","ADU_ON");
				break;
		}
		createEventObject(te,"TMDAS",te.getTIME_STAMP(),te.getEVENT_TYPE());
		logger.info("Exiting Method : tmdasEvent");
	}
	
	
	
	public void updateUgsDeviceStatus(UgsEvent ue,String type) 
	{
		logger.info("inside method : updateUgsDeviceStatus" );
		logger.debug("Event Data "+ue.toString()+" , Type"+type);
		int id = ue.getDeviceId();
		int state = ue.getEVENT_TYPE() == 2?1:2;
		String ip = ue.getIp();
		UgsDevice ud=(UgsDevice)Inventory.inventory.get(type).get(ip).get(ip+"-"+id);
		ud.setState(state);
		//wirteEventToFile(ue,type);
		logger.info("Exiting Method : updateUgsDeviceStatus");
	}
	
	public void updateTrglDeviceStatus(TrglEvent te,String type) 
	{
		logger.info("inside method : updateTrglDeviceStatus" );
		logger.debug("Event Data "+te.toString()+" , Type"+type);
		String id = te.getIp();
		int state = te.getEVENT_TYPE() == 2?1:2;
		String ip = id;
		TrglDevice td=(TrglDevice)Inventory.inventory.get(type).get(ip).get(ip+"-"+id);
		td.setState(state);
		//wirteEventToFile(te,type);
		logger.info("Exiting Method : updateTrglDeviceStatus");
	}
	
	public void updateTmdasDeviceStatus(TmdasEvent te,String type) 
	{
		logger.info("inside method : updateTmdasDeviceStatus" );
		String id = te.getIp();
		int state = te.getEVENT_TYPE() == 2?1:2;
		String ip = id;
		TmdasDevice td=(TmdasDevice)Inventory.inventory.get(type).get(ip).get(ip+"-"+id);
		td.setState(state);
		//wirteEventToFile(te,type);
		logger.info("Exiting Method : updateTmdasDeviceStatus");
	}

	public void addRemoveUgsDevice(UgsEvent ue,boolean addFlag) 
	{
		
		logger.info("inside method : addRemoveUgsDevice" );
		logger.debug("Event Data "+ue.toString()+" , Flag"+addFlag);
		if(addFlag) 
		{
			UgsDevice ud = new UgsDevice(ue.getDeviceId(), ue.getLatitude(), ue.getLongitude(), ue.getDEVICE_TYPE(), ue.getCOVERAGE(),  ue.getIp(), 1);
			//Inventory.inventory.get("UGS").get(ue.getIp()).put(ue.getIp()+"-"+ue.getDeviceId(),ud);
			
			
			if(Inventory.inventory.get("UGS").get(ue.getIp()) !=null) 
			{
				Inventory.inventory.get("UGS").get(ue.getIp()).put(ue.getIp()+"-"+ue.getDeviceId(),ud);
			}
			else 
			{
				HashMap<String,Object> device = new HashMap<String,Object>();
				device.put(ue.getIp()+"-"+ue.getDeviceId(),ud);
				Inventory.inventory.get("UGS").put(ue.getIp(),device);
			}
			
			
		}
		else 
		{
			Inventory.inventory.get("UGS").get(ue.getIp()).remove(ue.getIp()+"-"+ue.getDeviceId());
		}
		logger.info("Exiting Method : addRemoveUgsDevice");
	}
	
	public void addRemoveTrglDevice(TrglEvent te,boolean addFlag) 
	{
		
		logger.info("inside method : addRemoveTrglDevice" );
		logger.debug("Event Data "+te.toString()+" , Flag"+addFlag);
		if(addFlag) 
		{
			TrglDevice td = new TrglDevice(te.getIp(), te.getLATITUDE(), te.getLONGITUDE(), te.getANGLE(), 1) ;
			
			if(Inventory.inventory.get("TRGL").get(te.getIp()) !=null) 
			{
				Inventory.inventory.get("TRGL").get(te.getIp()).put(te.getIp()+"-"+te.getIp(),td);
			}
			else 
			{
				HashMap<String,Object> device = new HashMap<String,Object>();
				device.put(te.getIp()+"-"+te.getIp(),td);
				Inventory.inventory.get("TRGL").put(te.getIp(),device);
			}
			
			
		}
		else 
		{
			Inventory.inventory.get("TRGL").get(te.getIp()).remove(te.getIp()+"-"+te.getIp());
		}
		logger.info("Exiting Method : addRemoveTrglDevice");
	}
	public void addRemoveTmdasDevice(TmdasEvent te,boolean addFlag) 
	{
		
		logger.info("inside Method : addRemoveTmdasDevice");
		logger.debug("Event Data "+te.toString()+" , Flag"+addFlag);
		if(addFlag) 
		{
			TmdasDevice td = new TmdasDevice(te.getIp(), te.getLATITUDE(), te.getLONGITUDE(), te.getOFFSET(), te.getTA(), 1);
			
			if(Inventory.inventory.get("TMDAS").get(te.getIp()) !=null) 
			{
				Inventory.inventory.get("TMDAS").get(te.getIp()).put(te.getIp()+"-"+te.getIp(),td);
			}
			else 
			{
				HashMap<String,Object> device = new HashMap<String,Object>();
				device.put(te.getIp()+"-"+te.getIp(),td);
				Inventory.inventory.get("TMDAS").put(te.getIp(),device);
			}
		}
		else 
		{
			Inventory.inventory.get("TMDAS").get(te.getIp()).remove(te.getIp()+"-"+te.getIp());
		}
		//wirteEventToFile(te,"TMDAS");
		logger.info("Exiting Method : addRemoveTmdasDevice");
	}

	
	public synchronized <T> void wirteEventToFile(T obj,String type)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			String jsonInString = mapper.writeValueAsString(obj);
						
			Date date = new Date();
			
			FileWriter fw=null;
			PrintWriter pw = null;
			File file = null;
			try 
			{
				Resource resource1 = new ClassPathResource("/static/");
				String url1 = resource1.getURL().getPath();
				file = new File(url1+"event_"+formatDate("yyyy_MM_dd",date)+".csv");
				if (file.createNewFile()) {
				    
				    System.out.println("File has been created.");
				} else {
				
				    System.out.println("File already exists.");
				}
				
				fw = new FileWriter(file,true);
				pw = new PrintWriter(fw);
				String data  = type+";"+jsonInString;
				pw.println(data);
				this.template.convertAndSend("/topic/event", data);
	
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				try {
					if(fw != null)
						fw.close();
					if(pw != null)
						pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
		catch (JsonProcessingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public synchronized <T> void wirteEventToFile(T obj,String type,int id)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			
			String jsonInString = mapper.writeValueAsString(obj);
						
			Date date = new Date();
			
			try 
			{
				String data  = type+";"+jsonInString+";"+id;
				this.template.convertAndSend("/topic/event", data);
	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		} 
		catch (JsonProcessingException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SendTo("/topic/event")
    public String greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello";
    }
	
	public String formatDate(String format,Date date)
	{
		
		logger.info("inside method : formatDate" );
		logger.debug("Format : "+format+", Date"+date);
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		
		logger.info("Exiting method : formatDate" );
		return dateFormat.format(date);
		
	}
	
	
	public <T> void createEventObject(T data,String type,String timeStamp,int eventType) 
	{
		logger.info("inside method : createEventObject" );
		logger.debug("Data : "+data.toString()+", Type"+type+", Time Stamp"+timeStamp+", Event Type"+eventType);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try 
		{
			String jsonInString = objectMapper.writeValueAsString(data);
			Event obj = new Event();
			obj.setEventDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStamp));
			obj.setNodeType(type);
			obj.setEventType(eventType+"");
			obj.setEventData(jsonInString);
			Event savedObject =  es.saveEvent(obj);
			String dataforSend  = type+";"+jsonInString+";"+savedObject.getEventId();
			this.template.convertAndSend("/topic/event", dataforSend);
		} 
		catch (Exception e) 
		{
			logger.error("Exception inside method createEventObject "+e.getMessage());
			e.printStackTrace();
		}
		logger.info("Exiting method : createEventObject" );
	}
	
	
	
	@PostConstruct
	public void clearEevnts() 
	{
		
		
		
		logger.info("starting udpserver at port "+env.getProperty("app.udpport")+"..........");	
		
		UdpServer us = new UdpServer(Integer.parseInt(env.getProperty("app.udpport")));
		applicationContext.getAutowireCapableBeanFactory().autowireBean(us);
		us.start();
		
		logger.info("done");
		
		logger.info("Exiting method : createCache" );
	}
	
	
	  public String createEventUrl(String node,String ip,String path) 
	  {
		  logger.info("inside method : createEventUrl" );
		  logger.debug("Parms Node : "+node+",ip : "+ ip+",Path : "+ path );
		  
		  String url = null;
		  switch(node) 
		  {
		  	case "UGS":
		  		url= "http://"+ip+path+env.getProperty("inventory.ugs");
		  		break;
		  	case "TRGL":
		  		url= "http://"+ip+path+env.getProperty("inventory.trgl");
		  		break;
		  	case "TMDAS":
		  		url= "http://"+ip+path+env.getProperty("inventory.tmdas");
		  		break;
		  }
		  logger.info("Genrated URL :"+url );
		  logger.info("Exiting method : createEventUrl" );
		  
		  return url;
	  }
	  
	  @RequestMapping("/cue/{node}")
	  public void cueEvent(HttpServletRequest request ,@PathVariable("node") String node,@RequestBody HashMap<String,String> cueData) 
	  {
		  cueData.put("NODE", node);
		  
		  String ip = request.getRemoteAddr();
		  Nodes device = ns.findNodeByIp(ip);
		  
		  Cue cue=new Cue();
		  try 
		  {
			SimpleDateFormat fromatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date reportTime = fromatter.parse(cueData.get("DATE"));
			//Cue cue = new Cue(reportTime, node, cueData.get("SOURCE"), cueData.get("DETAIL"));
			
			cue.setReportedTime(reportTime);
			cue.setSource(cueData.get("SOURCE"));
			cue.setDetail(cueData.get("DETAIL"));
			cue.setCueId(cueData.get("CUE_ID"));
			cue.setReportedBy(node);
			cue.setNodeType(device.getNodeName());
			cues.saveCue(cue);			
		} 
		 catch (Exception e) 
		  {
			logger.info("exception :"+e);
			e.printStackTrace();
			// TODO: handle exception
		}
		this.template.convertAndSend("/topic/cue", cue);
	  }
	  
	  @RequestMapping("/cues")
	  public List<Cue> getCueForCurrentDay()
	  {
		  return cues.getCueForCurrentDay();
	  }
	 
}
