package in.vnl.controller;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.vnl.common.Common;
import in.vnl.model.Cell;
import in.vnl.model.Cue;
import in.vnl.model.Inventory;
import in.vnl.model.Nodes;
import in.vnl.repository.CueService;
import in.vnl.repository.MapRotationService;
import in.vnl.repository.NodesService;

@RestController
@RequestMapping("/api")
public class CommonRestController 
{
	private static final Logger log = LoggerFactory.getLogger(SchedulController.class);
	private static String applicationStartupTime = null;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private NodesService ns;
	
	@Autowired
	private CueService cs;
	
	@Autowired
	private MapRotationService mRS;

	// @Autowired
	// private Inventory inv;

	@Autowired
	private Common com;
	
	@RequestMapping(value="/GET_STATUS")
	public HashMap<String,String> getStatus()
	{
		HashMap<String,String> status = new HashMap<String,String>();
		status.put("STATUS", "Running");
		return status;
	}
	
	
	
	@RequestMapping(value="/subinventory")
	public ArrayList<LinkedHashMap<String,String>> getSubInventory(@RequestParam("id") int id,@RequestParam("ip") String ip)
	{
		RestTemplate restTemplate = new RestTemplate();
		
		ArrayList<LinkedHashMap<String,String>> data = new ArrayList<LinkedHashMap<String,String>>();
		if(com.pingIp(ip)) 
		{
			data = restTemplate.getForObject(createUrl(id,ip,"subinventory"),ArrayList.class);
		}
		return data;
	}
	
	@RequestMapping(value="/cues")
	public ArrayList<Cue> getCuesData()
	{

		return cs.getAll();
	}
	
	
	
	
	
	@RequestMapping(value="/restart")
	public void getStatus(@RequestParam("id") int id,@RequestParam("ip") String ip)
	{
		RestTemplate restTemplate = new RestTemplate();
		
		
		if(com.pingIp(ip)) 
		{
			String data = restTemplate.getForObject(createUrl(id,ip,"restart"),String.class);
		}
		log.info(ip+" Restarting...");
	}
	
	
	
	@RequestMapping(value="/node/{type}")
	public Nodes getNodeData(@PathVariable("type") String type)
	{
		return ns.getNodeDetailByNodeType(type);
	}
	
	
	@RequestMapping(value="/system/location")
	public HashMap<String,String> location()
	{
		return null;
		//return ns.getNodeDetailByNodeType(type);
	}
	
	
	public String createUrl(int id,String ip,String propName) 
	  {
		 Long idOfNode = Long.valueOf(id); 
		 Nodes node = ns.getNodeById(idOfNode);
		 String path = node.getUrl();
		 String name = node.getNodeName();
		 String url= "http://"+ip+path+env.getProperty(propName+"."+name);
		 return url;
	  }
	
	@RequestMapping(value="/system/start_time")
	public String startupTime()
	{
		return this.applicationStartupTime;
	}
	
	@PostConstruct
	public void recordStartupTime() 
	{
		SimpleDateFormat timeFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		this.applicationStartupTime = timeFormat.format(new Date());
	}
	
	@PostConstruct
	@RequestMapping(value="/get_time")
	public void getSystemTimeFromServer() 
	{
		log.info("inside getSystemTimeFromServer");
		String timeserverIp = env.getProperty("timeserver.ip");
		String timeserverUrl = env.getProperty("timeserver.url");
		String requestJson = env.getProperty("timeserver.command");
		
		String url= "http://"+timeserverIp+timeserverUrl;
		RestTemplate restTemplate = new RestTemplate();	
		Thread t = new Thread()
	     {
			public void run()
			{
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
				
				try 
				{
					boolean flag = true;
					while(flag) 
					{
						if(com.pingIp(timeserverIp)) 
						{
							byte[] data = restTemplate.postForObject(url,entity,byte[].class);
							ObjectMapper om = new ObjectMapper();
							HashMap<String,String> myObjects = om.readValue(data, HashMap.class);
							String  dd= om.writeValueAsString(myObjects.get("REPORT"));
							List<HashMap<String,Object>> reportData = om.readValue(dd,List.class);
							Long time = Long.parseLong(reportData.get(0).get("GPS_TIMESTAMP").toString());
							Date revcivedTime = new Date(time);
							if(time != -1) 
							{
								setSystemTime(revcivedTime);
								flag=false;
							}
						}
					}
				}
				catch(Exception E) 
				{
					log.info("exception while seting time"+E.getMessage());
					
				}							
           }
	     };
	     t.start();
	     log.info("exiting from getSystemTimeFromServer");
	}
	
	public void setSystemTime(Date revcivedTime)
	{
		log.info("inside setSystemTime");
		SimpleDateFormat convertedTimeFormat = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
		String systemRequiredTime = convertedTimeFormat.format(revcivedTime);
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("bash", "-s", systemRequiredTime);
		log.info("exiting from setSystemTime");
	}
	
	
/*************************UPDATE MAP ANGLE ***************************************/
	
    @RequestMapping(value="/updateAngle")
	public void updateAngle(@RequestParam("angle") int angle,Principal principal)
	{
    	mRS.saveAndUpdateRotation(principal.getName(), angle);
    	//log.info("Angle {}",angle," update successfully for {} ",principal.getName());
	}
    
 /*************************UPDATE MAP ANGLE ***************************************/
    
 /*************************GET CURRENT ANGLE***************************************/
    @RequestMapping(value="/getCurrentAngle")
    public int getCurrentAngle(Principal principal) {
    	return mRS.getCurrentAngleFromDatabase(principal.getName());
    }
 /*************************GET CURRENT ANGLE***************************************/
	
	
	
}
