package in.vnl.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;

import org.hibernate.mapping.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//import ch.qos.logback.core.pattern.parser.Node;
import in.vnl.common.Common;
import in.vnl.model.Cue;
import in.vnl.model.Event;
import in.vnl.model.Nodes;
import in.vnl.repository.CueService;
import in.vnl.repository.EventService;
import in.vnl.repository.NodesService;

@RestController
@RequestMapping("/reports")
public class ReportController 
{
	
	
	private static final Logger log = LoggerFactory.getLogger(SchedulController.class);
	
	
	@Autowired
	private Environment env;
	
	@Autowired
	private NodesService ns;
	
	@Autowired
	private EventService es;
	
	@Autowired
	private CueService cs;
	
	@Autowired
	private Common com;
	
	@Value("${app.dayforevents}")
	private String numberOfdays;
	
	@Value("${app.loadEventsOnStartup}")
	private String loadEventsOnStartup;
	
	
	
	
	@RequestMapping("/{type}")
	public ResponseEntity<Object> getReport(@RequestBody String jsonData,@PathVariable("type") String type) 
	{
		
		log.info("inside method : getReport" );
		log.debug("Data "+jsonData);
		List<Nodes> nodes = null;
		if(type.equalsIgnoreCase("ALL")) 
		{
			nodes = ns.getNodes();
		}
		else 
		{
			nodes = new ArrayList<Nodes>();
			nodes.add(ns.getNodeDetailByNodeType(type));
		}
		 
		
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		
		//Resource resource = new ClassPathResource("/static/");
		
		String tempPathOfDirectory = com.temporaryDirectory();
		
		for (int i = 0; i < nodes.size(); i++) 
		{
			
			String nodeIp =nodes.get(i).getNodeIp(); 
			String nodeName =nodes.get(i).getNodeName();
			String zipName =nodes.get(i).getDisplayName();
			String nodePath=nodes.get(i).getUrl()!=null?nodes.get(i).getUrl():"";
			String datap = jsonData;
			String url = createReportUrl(nodeName,nodeIp,nodePath);
			if(com.pingIp(nodeIp)) 
			{
				Thread t = new Thread()
			     {
			          
					public String ip;
					public Long systime;
					public String name;
					
					{
						this.ip = nodeIp;
						this.name = zipName;
					}
					
					public void run()
					{
						FileOutputStream fos = null;
						try 
						{
						
						/*String url = "http://"+this.ip+"/"+this.name+".zip";
						RestTemplate restTemplate = new RestTemplate();
						byte[] imageBytes = restTemplate.getForObject(url, byte[].class);*/
						
						
						
						
						//RestTemplate restTemplate = new RestTemplate();
								
						 int downloadTimeOut = Integer.parseInt(env.getProperty("app.report.download_timeout"));
						 int connectionTimeOut = Integer.parseInt(env.getProperty("app.report.download_timeout"));
					     RestTemplate restTemplate = com.getRestTemplate(connectionTimeOut,downloadTimeOut);
					     
						//String url = "http://"+this.ip+"/"+this.name+".zip";
						String requestJson = jsonData;
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.APPLICATION_JSON);
						HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
						
						byte[] imageBytes = new byte[0];
						
						if(com.pingIp(nodeIp)) 
						{
							imageBytes = restTemplate.postForObject(url, entity, byte[].class);
						}
						
						log.info("data recived"+imageBytes.toString());
						
						File file = new File(tempPathOfDirectory + "/"+this.name+".zip");
						file.createNewFile();
						fos = new FileOutputStream(file);
						fos.write(imageBytes);
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						finally 
						{
							try {
								fos.close();
							} catch (Exception e2) {
								e2.printStackTrace();
							}
						}
		           }
			     };
			     t.start();	    
			     threadList.add(t);
			}
			
		}
		
		for (Thread thread : threadList) {
	        try {
				thread.join();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    }
		try {
			zipFolder(tempPathOfDirectory, tempPathOfDirectory + ".zip");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		/*File ff = new File(tempPathOfDirectory + ".zip");
	    InputStreamResource resource1 = null;
		try {
			resource1 = new InputStreamResource(new FileInputStream(ff));
		} catch (Exception e) {
			// TODO: handle exception
		}

	    HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", ff.getName()));
		headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
		headers.add("Pragma", "no-cache");
		headers.add("Expires", "0");
	    
		
		try {
			//directory.delete();
		} catch (Exception e) {
			log.info("Exception while deleting  directory "+e.getMessage());
		}
		return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(ff.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource1);*/
		return com.createFileAttachmentResponse(tempPathOfDirectory + ".zip");
		
	}
	
	
	
	@RequestMapping("/eventReport")
	//@PostConstruct
	public int eventReport() 
	{
		
		List<Nodes> nodes = ns.getNodes();
		
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		
		Resource resource = new ClassPathResource("/static/");
		
		String dirPath = null;
		
		try 
		{
			dirPath = resource.getURL().getPath();
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		} 
		
		long currenttime = System.currentTimeMillis();
		File directory = new File(dirPath+"/"+currenttime);
	    if (! directory.exists()){
	        directory.mkdir();
	        // If you require it to make the entire directory path including parents,
	        // use directory.mkdirs(); here instead.
	    }
	    ObjectMapper objectMapper = new ObjectMapper();
	    String requestData = null;
	    try {
			requestData = objectMapper.writeValueAsString(getReportsStatrAndEndDate(2,"1"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	    
		for (int i = 0; i < nodes.size(); i++) {
			
			String nodeIp =nodes.get(i).getNodeIp(); 
			String nodeName =nodes.get(i).getNodeName();
			
			String pp = dirPath;
			String datap = requestData;
			
			Thread t = new Thread()
		     {
		          
				public String ip;
				public String path; 
				public Long systime;
				public String name;
				public String jsonData;
				
				
				
				
				
				{
					this.ip = nodeIp;
					this.path = pp;
					this.systime = currenttime;
					this.name = nodeName;
					this.jsonData = datap;
				}
				public void run()
				{
					
					RestTemplate restTemplate = new RestTemplate();

					String url = "http://"+this.ip+"/EVENT_REPORT";
					
					String requestJson = jsonData;
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);

					
					
					HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
					
					
					byte[] imageBytes = new byte[0];
					
					if(com.pingIp(nodeIp)) 
					{
						imageBytes = restTemplate.postForObject(url, entity, byte[].class);
					}
					
					//byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

					FileOutputStream fos = null;
					try {
						
						File file = new File(path+"/"+systime + "/"+this.name+".json");
						file.createNewFile();
						fos = new FileOutputStream(file);
						fos.write(imageBytes);
						
						ObjectMapper objectMapper = new ObjectMapper();
						//List<HashMap<String,String>> myObjects = objectMapper.readValue(file, List.class);
						List<HashMap<String,Object>> myObjects = objectMapper.readValue(file, List.class);
						
						
						insertEvntsBatch(myObjects,this.name,this.ip);
						System.out.println("sunil");
						
						
						
						
					} 
					catch (Exception e) 
					{
						// TODO: handle exception
						e.printStackTrace();
					}
					finally 
					{
						try {
							fos.close();
						} catch (Exception e2) {
							// TODO: handle exception
						}
					}
	           }
		     };
		     t.start();	    
		     threadList.add(t);
		}
		
		for (Thread thread : threadList) {
	        try {
				thread.join();
			} catch (Exception e) {
				// TODO: handle exception
			}
	    }
		
		return 1;
		
	}
	
	
	
	@RequestMapping("cue-history/{type}")
	public int getCueReport(@RequestBody int day,@PathVariable("type") String type) 
	{
		
		log.info("inside method : getCueReport" );
		log.debug("Parm day ="+day);
		log.debug("Parm type ="+type);
		List<Nodes> nodes = null;
		if(type.equalsIgnoreCase("ALL")) 
		{
			nodes = ns.getNodes();
		}
		else 
		{
			nodes = new ArrayList<Nodes>();
			nodes.add(ns.getNodeDetailByNodeType(type));
		}
		 
		
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		
		for (int i = 0; i < nodes.size(); i++) 
		{
			
			String nodeIp =nodes.get(i).getNodeIp(); 
			String nodeName =nodes.get(i).getNodeName();
			String nodeDisplayName =nodes.get(i).getDisplayName();
			String nodePath=nodes.get(i).getUrl()!=null?nodes.get(i).getUrl():"";
			ObjectMapper objectMapper = new ObjectMapper();
			
			String url = createCueHistorUrl(nodeName,nodeIp,nodePath);
			if(com.pingIp(nodeIp)) 
			{
				Thread t = new Thread()
			     {
					public void run()
					{
						FileOutputStream fos = null;
						try 
						{
						
							RestTemplate restTemplate = new RestTemplate();
							
							
							String requestJson = objectMapper.writeValueAsString(getReportsStatrAndEndDate(day, "4"));
							HttpHeaders headers = new HttpHeaders();
							headers.setContentType(MediaType.APPLICATION_JSON);
							
							HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
						
							byte[] imageBytes = new byte[0];
						
							if(com.pingIp(nodeIp)) 
							{
								//imageBytes = restTemplate.postForObject(url, entity, byte[].class);
								imageBytes = restTemplate.postForObject(url, entity, byte[].class);
								
								
								try 
								{
									List<HashMap<String,Object>> myObjects = objectMapper.readValue(imageBytes, List.class);								
									insertCueBatch(myObjects,nodeDisplayName,nodeIp,nodeName);
								}
								catch(Exception e) 
								{
									log.info("Exception while inserting events"+e.getMessage());
								}
	
							}
						
							log.info("data recived"+imageBytes.toString());
							
						} 
						catch (Exception e) 
						{
							log.info("Exception method  getCueReport "+e.getMessage());
							e.printStackTrace();
						}
		           }
			     };
			     t.start();	    
			     threadList.add(t);
			}
			
		}
		
		for (Thread thread : threadList) {
	        try {
				thread.join();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
	    }
		log.info("Exiting method getCueReport");
		return 1;
		
	}
	
	
	
	
	

	
	
	static public void zipFolder(String srcFolder, String destZipFile) throws Exception {
	    ZipOutputStream zip = null;
	    FileOutputStream fileWriter = null;

	    fileWriter = new FileOutputStream(destZipFile);
	    zip = new ZipOutputStream(fileWriter);

	    addFolderToZip("", srcFolder, zip);
	    zip.flush();
	    zip.close();
	  }

	  static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
	      throws Exception {

	    File folder = new File(srcFile);
	    if (folder.isDirectory()) {
	      addFolderToZip(path, srcFile, zip);
	    } else {
	      byte[] buf = new byte[1024];
	      int len;
	      FileInputStream in = new FileInputStream(srcFile);
	      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
	      while ((len = in.read(buf)) > 0) {
	        zip.write(buf, 0, len);
	      }
	    }
	    
	  }

	  static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
	      throws Exception {
	    File folder = new File(srcFolder);

	    for (String fileName : folder.list()) {
	      if (path.equals("")) {
	        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
	      } else {
	        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
	      }
	    }
	  }
	  
	  
	  public void insertEvntsBatch(List<HashMap<String,Object>> aa,String type,String ip) 
	  {
		  
		  ArrayList<Event> events = new ArrayList<Event>();
		  try 
		  {
			for (int i = 0; i < aa.size(); i++) 
			{
				ObjectMapper objectMapper = new ObjectMapper();
				Event obj = new Event();
				HashMap<String,Object> data = aa.get(i);
				data.put("ip", ip);
				data.put("DEVICE_IP", ip);
				obj.setEventDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.get("TIME_STAMP").toString()));
				obj.setNodeType(type);
				obj.setEventType(data.get("EVENT_TYPE").toString());
				obj.setEventData(objectMapper.writeValueAsString(aa.get(i)));
				events.add(obj);
			}
			es.saveEvents(events);
		} 
		  catch (Exception e) 
		  {
			e.printStackTrace();
			log.error(e.getMessage());
			  // TODO: handle exception
		  }
	  }
	  
	  
	  public void insertCueBatch(List<HashMap<String,Object>> cueList,String type,String ip,String nodeName) 
	  {
		  
		  ArrayList<Cue> cues = new ArrayList<Cue>();
		  try 
		  {
			for (int i = 0; i < cueList.size(); i++) 
			{
				ObjectMapper objectMapper = new ObjectMapper();
				Cue obj = new Cue();
				HashMap<String,Object> data = cueList.get(i);
				obj.setNodeType(nodeName);
				
				if(!nodeName.equalsIgnoreCase("ugs")) 
				{
					obj.setReportedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.get("DATE").toString()));
					obj.setSource(data.get("SOURCE").toString());
					obj.setDetail(data.get("DETAIL").toString());
					obj.setCueId(data.get("CUE_ID").toString());
				}
				else 
				{
					obj.setReportedTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(data.get("TIME_STAMP").toString()));
					obj.setSource(type);
					obj.setDetail(data.get("TRANS_ID").toString()+","+data.get("LATITUDE").toString()+","+data.get("LONGITUDE").toString()+","+data.get("TIMEOUT"));
					obj.setCueId(data.get("TRANS_ID").toString());
				}
				
				obj.setReportedBy(type);
				cues.add(obj);
			}
			cs.saveCues(cues);
		} 
		  catch (Exception e) 
		  {
			e.printStackTrace();
			log.error(e.getMessage());
			  // TODO: handle exception
		  }
	  }
	  
	  
	  
	  public HashMap<String,String> getReportsStatrAndEndDate(int day,String eventType)
	  {
		  HashMap<String,String> dates = new HashMap<String,String>();
		  
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date date = new Date();
		  DateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  if(day == 0 || Boolean.parseBoolean(this.loadEventsOnStartup)) 
		  {
			  dates.put("STOP_TIME", dateFormat.format(date));
		  }
		  else 
		  {
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(new Date());
			  cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-day);
			  Date endDate = cal.getTime();
			  dates.put("STOP_TIME", endDateFormat.format(endDate)+" 23:59:59");
		  }
		  
		  
		  
		  // get Calendar instance
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(new Date());
		  // If we give 7 there it will give 8 days back
		  cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-day);
		  // convert to date
		  Date myDate = cal.getTime();
		  
		  dates.put("START_TIME", endDateFormat.format(myDate)+" 00:00:00");
		  System.out.println(dateFormat.format(date));
		  
		  dates.put("TYPE", eventType);
		  
		  return dates;
		  
		  
	  }
	  
	  
	  public HashMap<String,String> getStatrAndEndDate(int day,String eventType)
	  {
		  HashMap<String,String> dates = new HashMap<String,String>();
		  
		  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date date = new Date();
		  DateFormat endDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  if(day == 0) 
		  {
			  dates.put("STOP_TIME", dateFormat.format(date));
		  }
		  else 
		  {
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(new Date());
			  cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-day);
			  Date endDate = cal.getTime();
			  dates.put("STOP_TIME", endDateFormat.format(endDate)+" 23:59:59");
		  }
		  
		  
		  
		  // get Calendar instance
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(new Date());
		  // If we give 7 there it will give 8 days back
		  cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)-day);
		  // convert to date
		  Date myDate = cal.getTime();
		  
		  dates.put("START_TIME", endDateFormat.format(myDate)+" 00:00:00");
		  System.out.println(dateFormat.format(date));
		  
		  dates.put("TYPE", eventType);
		  
		  return dates;
		  
		  
	  } 
	  
	  
	  
	  @RequestMapping("/event")
	  public List<Event> event(@RequestParam("date") String eventdate,@RequestParam("type") int eventtype,@RequestParam("offset") int offset) 
	  {
		  
		log.info("inside method events"+System.currentTimeMillis());
		log.info("parm : ");
		log.info("event type : "+eventtype);
		log.info("offset : "+offset);
		log.info("date : "+eventdate);
		
		Date startDate = null;
		Date endDate = null;
		try 
		{
			/*startDate = new SimpleDateFormat("yyyy-MM-dd").parse(eventdate);
			Calendar c = Calendar.getInstance(); 
			c.setTime(startDate); 
			c.add(Calendar.DATE, 1);
			endDate = c.getTime();*/
			
			String eventType = offset==0?"1":"2";
			HashMap<String,String> dates = getStatrAndEndDate(offset,eventType);
			startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates.get("START_TIME"));
			endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dates.get("STOP_TIME"));
			if(!Boolean.parseBoolean(this.loadEventsOnStartup))
			{
				
				log.info("calling getReportforlastDay: "+System.currentTimeMillis());
				getReportforlastDay(offset,eventType);
				log.info("END calling getReportforlastDay: "+System.currentTimeMillis());
			}
		} 
		catch (Exception e) 
		{
			log.info("Exception event : "+e.getMessage());
		}
		  if(eventtype == 2) 
		  {
			  log.info("calling findAlarmsOnGiveDate: "+System.currentTimeMillis());
			  List<Event> event = es.findAlarmsOnGiveDate(startDate,endDate);
			  log.info("END calling findAlarmsOnGiveDate: "+System.currentTimeMillis());
			  return event;
			  
		  }  
		  else
		  {
			  log.info("calling findEventsOnGiveDate: "+System.currentTimeMillis());
			  List<Event> event = es.findEventsOnGiveDate(startDate,endDate);
			  log.info("END calling findEventsOnGiveDate: "+System.currentTimeMillis());
			  return event;
		  }
			  
	  }
	  
	  
	  
	  @RequestMapping("/ackAlarm")
	  public int ackKnowledgeAlarms(@RequestBody HashMap<String, String> data) 
	  {
		  
		  ObjectMapper om = new ObjectMapper();
		  
		  HashMap<String,String> hm = new HashMap<String,String>();
		  
		  hm.put("DEVICE_ID", data.get("DEVICE_ID"));
		  hm.put("TRANS_ID", data.get("TRANS_ID"));
		  hm.put("ACTION", data.get("ACTION"));
		  String nodeType = data.get("NODE");
		  
		  Nodes node = ns.getNodeDetailByNodeType(nodeType);
		  
		  com.sendAdu(nodeType, "ADU_OFF");
		  try 
		  {
			  
			String jsonData = om.writeValueAsString(hm);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> entity = new HttpEntity<String>(jsonData,headers);
			RestTemplate restTemplate = new RestTemplate();
			
			String url = createAckUrl(nodeType,node.getNodeIp(),node.getUrl());
			
			HashMap<String,String> result = new HashMap<String,String>();
			result.put("RESULT", "FAIL");
			if(com.pingIp(node.getNodeIp())) 
			{
				result  = restTemplate.postForObject(url, entity, HashMap.class);
			}
			
			
			if(result.get("RESULT").equalsIgnoreCase("success")) 
			{
				return es.acknowledgeAlarm(data.get("ID"));
			} 
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  
		  
		  return 0;
	  }
	  
	  
	  /*
	   * day starts from 0 
	   * 0=1(current)
	   * */
	  public int getReportforlastDay(int day,String eventType) 
		{
			
			
		  	log.info("inside method getReportforlastDay "+System.currentTimeMillis());
		  	List<Nodes> nodes = ns.getNodes();
			
			
		  	ArrayList<Thread> threadList = new ArrayList<Thread>();
			
			//Resource resource = new ClassPathResource("/static/");
			
			String dirPath = null;
			try 
			{
				//dirPath = resource.getURL().getPath();
				
				dirPath = env.getProperty("app.temppath")==null?"":env.getProperty("app.temppath");
				log.info("Directory path "+dirPath);
			} 
			catch (Exception e) 
			{
				log.info("Exception while getting directory path method : getReportforlastDay "+e.getMessage());
				
			} 
			
			long currenttime = System.currentTimeMillis();
			
			String tempPathOfDirectory = dirPath.equalsIgnoreCase("")?currenttime+"":dirPath+"/"+currenttime;
			File directory = new File(tempPathOfDirectory);
		    
			if (! directory.exists()){
		        directory.mkdir();
		    }
		    
			ObjectMapper objectMapper = new ObjectMapper();
		    String requestData = null;
		    
		    try 
		    {
				requestData = objectMapper.writeValueAsString(getReportsStatrAndEndDate(day,eventType));
			}
		    catch (Exception e) 
		    {
				log.info("Exception while convering data to string "+e.getMessage());
			}
		    
		    
		    
			for (int i = 0; i < nodes.size(); i++) 
			{
				
				String nodeIp =nodes.get(i).getNodeIp(); 
				String nodeName =nodes.get(i).getNodeName();
				String nodePath=nodes.get(i).getUrl()!=null?nodes.get(i).getUrl():"";
				String pp = dirPath;
				String datap = requestData;
				String url = createEventUrl(nodeName,nodeIp,nodePath);
				
				
				if(com.pingIp(nodeIp)) 
				{
				
					Thread t = new Thread()
				     {
				          
						public String ip;
						public String path; 
						public Long systime;
						public String name;
						public String jsonData;
				
						{
							this.ip = nodeIp;
							this.path = pp;
							this.systime = currenttime;
							this.name = nodeName;
							this.jsonData = datap;
						}
						
						public void run()
						{
							
							RestTemplate restTemplate = new RestTemplate();
	
							//String url = "http://"+this.ip+nodePath+"/EVENT_REPORT";
							String requestJson = jsonData;
							HttpHeaders headers = new HttpHeaders();
							
							headers.setContentType(MediaType.APPLICATION_JSON);
							HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
							
							
							byte[] imageBytes = new byte[0];
							
							if(com.pingIp(ip)) 
							{
								log.info("Getting data for "+ip+" , time of call : "+System.currentTimeMillis());
								imageBytes = restTemplate.postForObject(url, entity, byte[].class);
								
								log.info("data return time for "+ip+" , time of return : "+System.currentTimeMillis());
								
								try 
								{
									List<HashMap<String,Object>> myObjects = objectMapper.readValue(imageBytes, List.class);								
									insertEvntsBatch(myObjects,this.name,this.ip);
								}
								catch(Exception e) 
								{
									log.info("Exception while inserting events"+e.getMessage());
								}
								
							
								/*FileOutputStream fos = null;
								File file = null;
								try 
								{
										file = new File(tempPathOfDirectory + "/"+this.name+".json");
										file.createNewFile();
										fos = new FileOutputStream(file);
										log.info("writing data to file "+ip+" , time of writing : "+System.currentTimeMillis());
										fos.write(imageBytes);
										log.info("End writing data to file "+ip+" , time of End : "+System.currentTimeMillis());
									
									//List<HashMap<String,String>> myObjects = objectMapper.readValue(file, List.class);
									List<HashMap<String,Object>> myObjects = objectMapper.readValue(file, List.class);
									
									
									insertEvntsBatch(myObjects,this.name,this.ip);
									log.info("Time of complete for : "+ip+" ,time : "+System.currentTimeMillis());
								
								} 
								catch (Exception e) 
								{
									log.info("Exception while getting or writing data "+e.getMessage());
									
									e.printStackTrace();
								}
								finally 
								{
									try {
										fos.close();
										directory.delete();
									} catch (Exception e2) {
										// TODO: handle exception
									}
								}*/
							}
			           }
				     };
				     t.start();	    
				     threadList.add(t);
				}
			}
			
			for (Thread thread : threadList) {
		        try {
					thread.join();
				} catch (Exception e) {
					log.info("Exception inside method getReportforlastDay");
				}
		    }
			log.info("Ending method getReportforlastDay "+System.currentTimeMillis());
			return 1;
			
		}

	  @PostConstruct
	  @DependsOn("EventController")
	  public void  loadEventsOnStartup() 
	  {
	  	log.info("inside method : createCache" );
	  	log.info("Clearing events..........");
		
		es.trucateEvets();
		
		log.info("done");
		
		  if(this.loadEventsOnStartup.equalsIgnoreCase("true")) 
		  {
			  
			  int keyValue = Integer.parseInt(this.numberOfdays);
			  getReportforlastDay(keyValue-1,"1");
		  }
	  }
	  
	  
	  @PostConstruct
	  @DependsOn("EventController")
	  public void  loadCuesOnStartup() 
	  {
		  log.info("Clearing cue..........");  
		  //cues.turncate();
		  log.info("done");
		  if(this.loadEventsOnStartup.equalsIgnoreCase("true")) 
		  {
			 int keyValue = Integer.parseInt(this.numberOfdays);
			  getCueReport(0,"ALL");
		  }
	  }
	  
	  public String createEventUrl(String node,String ip,String path) 
	  {
		  String url = null;
		  switch(node) 
		  {
		  	case "UGS":
		  		url= "http://"+ip+path+env.getProperty("event.ugs");
		  		break;
		  	case "TRGL":
		  		url= "http://"+ip+path+env.getProperty("event.trgl");
		  		break;
		  	case "TMDAS":
		  		url= "http://"+ip+path+env.getProperty("event.tmdas");
		  		break;
		  }
		  return url;
		  
	  }
	  
	  
	  
	  public String createAckUrl(String node,String ip,String path) 
	  {
		  String url = null;
		  switch(node) 
		  {
		  	case "UGS":
		  		url= "http://"+ip+path+env.getProperty("acknowledge.ugs");
		  		break;
		  	case "TRGL":
		  		url= "http://"+ip+path+env.getProperty("acknowledge.trgl");
		  		break;
		  	case "TMDAS":
		  		url= "http://"+ip+path+env.getProperty("acknowledge.tmdas");
		  		break;
		  }
		  return url;
		  
	  }
	  
	  
	  public String createReportUrl(String node,String ip,String path) 
	  {
		  String url = null;
		  switch(node) 
		  {
		  	case "UGS":
		  		url= "http://"+ip+path+env.getProperty("report.ugs");
		  		break;
		  	case "TRGL":
		  		url= "http://"+ip+path+env.getProperty("report.trgl");
		  		break;
		  	case "TMDAS":
		  		url= "http://"+ip+path+env.getProperty("report.tmdas");
		  		break;
		  }
		  return url;
	  }
	  
	  public String createCueHistorUrl(String node,String ip,String path) 
	  {
		  String url = null;
		  switch(node) 
		  {
		  	case "UGS":
		  		url= "http://"+ip+path+env.getProperty("cue.ugs");
		  		break;
		  	case "TRGL":
		  		url= "http://"+ip+path+env.getProperty("cue.trgl");
		  		break;
		  	case "TMDAS":
		  		url= "http://"+ip+path+env.getProperty("cue.tmdas");
		  		break;
		  }
		  return url;
	  }
	  
}
