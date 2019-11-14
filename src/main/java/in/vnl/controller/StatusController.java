package in.vnl.controller;

//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.vnl.common.Common;
import in.vnl.model.Cell;
import in.vnl.model.DeviceStatus;
import in.vnl.model.Inventory;
import in.vnl.model.Nodes;
import in.vnl.repository.EventService;
import in.vnl.repository.NodesRepository;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Controller
public class StatusController 
{
	private static final Logger log = LoggerFactory.getLogger(SchedulController.class);
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private NodesRepository nr;
	
	@Autowired
	private EventService es;
	
	@Autowired
	private InventoryController ic;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private Common cm;
	
	@Autowired
	private ReportController rc;
	
	/*@PostConstruct
    @Scheduled(fixedRateString="6000")
	public void updateStatusOfNodes() 
	{
		Inventory in = new Inventory();
		String[] ips = new String[3];
		
		ips[0] = nr.findNodeIpByNodeType("UGS").getNodeIp();
		ips[1] = nr.findNodeIpByNodeType("TMDAS").getNodeIp();
		ips[2] = nr.findNodeIpByNodeType("TRGL").getNodeIp();
		
		for(int i=0;i<=2;i++) 
		{
				int j = i;
				
				new Thread(new Runnable() {
				private String[] localIps;
			    private int index;
			    private String type;
			    
			    {
			        this.localIps = ips;
			        this.index = j;
			        this.type = j==0?"UGS":j==1?"TMDAS":"TRGL";
			        
			    }
			    
			    @Override
			    public void run() 
			    {
			    	try 
			    	{
						boolean status = getStatusForPingTypeDevices(ips[j],this.type);
						status = checkFurtherStatus(ips[j],status);
						checkAndUpdateStatus(status,this.type);
												
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}).start();
		}
	}*/
	
	
	
	@PostConstruct
    @Scheduled(fixedRateString="6000")
	public void updateStatusOfNodes() 
	{
	    
		List<Nodes> nodes= nr.findAll();
		for(int i=0;i<nodes.size();i++) 
		{
				int j = i;
				
				new Thread(new Runnable() {
				
			    private String ip =  nodes.get(j).getNodeIp();
			    private String type = nodes.get(j).getNodeName();
			    private SimpMessagingTemplate templateForThread = template;
			    
			    @Override
			    public void run() 
			    {
			    	try 
			    	{
						
			    		DeviceStatus ds = null;
						
			    		boolean status = getStatusForPingTypeDevices(this.ip,this.type);
			    		
						if(status)
							ds = checkFurtherStatus(this.ip);
						else
							ds = new DeviceStatus(this.ip, type, "Not Available", "Not Available", "Down","0");
						sendStatus(ds);
						checkAndUpdateStatus(status,this.type);						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			}).start();
		}
	}
	
	
	
	
	public void checkAndUpdateStatus(boolean currentSatus,String type) 
	{
		String lastStatus  = nr.findNodeIpByNodeType(type).getStatus();
		
		String latestStatus = (currentSatus)?"1":"0";
		
		if(lastStatus.equalsIgnoreCase("0") && currentSatus) 
		{
			switch(type) 
			{
				case "UGS":
					ic.getUgsInvetory();
					rc.getCueReport(0, "UGS");
					break;
				case "TMDAS":
					ic.getTmdasInvetory();
					rc.getCueReport(0, "TMDAS");
					break;
				case "TRGL":
					ic.getTrglInvetory();
					rc.getCueReport(0, "TRGL");
					break;
			}
			
			this.template.convertAndSend("/topic/reload", "{\"status\":\"reload\"}");
		}
		
		if(!currentSatus && !lastStatus.equalsIgnoreCase("0")) 
		{
			//es.deleteEventForNode(type);
			//this.template.convertAndSend("/topic/reload", "{\"status\":\"reload\"}");
		}
		
		nr.updateStatus(latestStatus,type);
	}
	
	
	public boolean getStatusForPingTypeDevices(String ip,String type) throws Exception
	{
		log.info("inside getStatusForPingTypeDevices");
		boolean result = cm.pingIp(ip);
		log.info("exiting getStatusForPingTypeDevices");
		return result;
	}
	
	
	@SendTo("/topic/status")
    public String greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Status Socket Hello";
    }
	
	@SendTo("/topic/nodeinfo")
    public String nodeInfo() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Status Socket Hello";
    }
	
	@SendTo("/topic/reload")
    public String reload() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Status Socket Hello";
    }
	
	@SendTo("/topic/http")
    public String http() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Status Socket Hello";
    }
	
	
	
	public DeviceStatus checkFurtherStatus(String ip) 
	{
		Nodes node = nr.findNodeIpByIp(ip);
		String statusUpdateType = node.getStatusUpdateType();
		DeviceStatus ds = null;
		switch(statusUpdateType) 
		{
			case "http":
				ds = getHttpstatus(node);
				break;
			case "ping":
				ds = new DeviceStatus(ip, node.getNodeName(), "Running", "Not Available", "UP","0");
			default:
				ds = new DeviceStatus(ip, node.getNodeName(), "Running", "Not Available", "UP","0");
			break;
		}
		return ds;
	}
	
	
	public DeviceStatus getHttpstatus(Nodes node) 
	{
		String ip = node.getNodeIp();
		String path = node.getUrl();
		HashMap<String,String> view = null;
		DeviceStatus ds = null;
		RestTemplate restTemplate = new RestTemplate();
		
		try 
		{
			String url = createHttpNodeUrl(node.getNodeName(),ip,path);
			view = restTemplate.getForObject(url, HashMap.class);
			ds = new DeviceStatus(ip, node.getNodeName(), view.get("MODE"), view.get("SPACE"), "UP",view.get("FREEZE"));
		} 
		catch (Exception e) 
		{
			view = new HashMap<String,String>();
			ds = new DeviceStatus(ip, node.getNodeName(), "Not Available", "Not Available", "Down","0");
		}
		return ds;
	}
	
	public void sendStatus(DeviceStatus ds) 
	{
		
		log.info("Inside method sendStatus");
		try 
		{
			ObjectMapper om =new ObjectMapper();
			String result = om.writeValueAsString(ds);
			this.template.convertAndSend("/topic/status", result);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("Problem while sending the status details method send status");
		}
		log.info("Exiting method sendStatus");
	}
	
	public String createHttpNodeUrl(String node,String ip,String path) 
	{
		
		if(path == null) 
		{
			path = "";
		}
		
		
		String url = null;
		  switch(node) 
		  {
		  	case "UGS":
		  		url= "http://"+ip+path+env.getProperty("status.ugs");
		  		break;
		  	case "TRGL":
		  		url= "http://"+ip+path+env.getProperty("status.trgl");
		  		break;
		  	case "TMDAS":
		  		url= "http://"+ip+path+env.getProperty("status.tmdas");
		  		break;
		  }
		  return url;
	}
	
}
