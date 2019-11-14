package in.vnl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import in.vnl.common.Common;
import in.vnl.contract.Bist;
import in.vnl.model.Nodes;
import in.vnl.model.TmdasInventory;
import in.vnl.model.TrglInventory;
import in.vnl.model.UgsInventory;
import in.vnl.repository.NodesService;

@RestController
@RequestMapping("/bist")
public class BistController implements Bist
{
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulController.class);
	
	@Autowired
	private Common cm;
	
	
	@Autowired
	private Environment env;
	
	@Autowired
	private NodesService ns;
	
	@Autowired
	private InventoryController ic;
	
	@Autowired
	private CommonRestController crc;
	
	@RequestMapping("/start")
	public ResponseEntity startBistOperation()
	{
		HashMap<String,ArrayList<LinkedHashMap<String,String>>> result = new HashMap<String,ArrayList<LinkedHashMap<String,String>>>();
		//result.put("IP Connection",testIpConnections());
		result.put("Power System Status",powerSystemStatus());
		result.put("FALCON System Test",testFalconConnections());
		result.put("HUMMER System Test",testHummerConnections());
		String temporaryDirectory = cm.temporaryDirectory();
		String filePath = temporaryDirectory+"/BIST.xlsx";
		cm.createExcelSheet(filePath, result);
		return cm.createFileAttachmentResponse(filePath);
	}

	
	@Override
	@RequestMapping("/testips")
	public ArrayList<LinkedHashMap<String, Object>> testIpConnections() {
		// TODO Auto-generated method stub
		logger.info("inside testIpConnections method");
		List<Nodes> nodes = ns.getNodesInOrder();
		ArrayList<LinkedHashMap<String,Object>> ls = new ArrayList<LinkedHashMap<String,Object>>();
		Thread[] threads = new Thread[nodes.size()];
		for(int i=0;i<nodes.size();i++) 
		{
				int j = i;
				
				Thread t = new Thread(new Runnable() {
				
				private String id =  nodes.get(j).getNodeId()+"";
				private String ip =  nodes.get(j).getNodeIp();
			    private String name = nodes.get(j).getDisplayName();
			    private String type = nodes.get(j).getNodeName();
			    
			    @Override
			    public void run() 
			    {
			    	try 
			    	{
			    		boolean status = cm.pingIp(this.ip);
			    		LinkedHashMap<String,Object> results =  new LinkedHashMap<String,Object>();
			    		results.put("TYPE", this.type);
			    		results.put("ID", this.id);
			    		results.put("IP", this.ip);
			    		results.put("NAME", name);
			    		results.put("RESULT", status?"OK":"DOWN");
			    		results.put("SUB_DATA", checkIpConectionOfEachSubNodes(type));
			    		//checkIpConectionOfEachSubNodes(type);
			    		ls.add(results);
					} 
			    	catch (Exception e) 
			    	{
			    		e.printStackTrace();
					}
			    }
			});
				
				t.start();
				threads[i]=t;	
		}
		
		
		for(int j=0;j<threads.length;j++) 
		{
			try {
				threads[j].join();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		logger.info("exiting testIpConnections method");
		return ls;
	}

	@Override
	@RequestMapping("/testpower")
	public ArrayList<LinkedHashMap<String,String>> powerSystemStatus() {
		
		logger.info("inside testIpConnections powerSystemStatus");
		RestTemplate restTemplate = new RestTemplate();
		
		ArrayList<LinkedHashMap<String,String>> view = null;
		
		Nodes node = ns.getNodeDetailByNodeType("TRGL");
		try 
		{
			if(cm.pingIp(node.getNodeIp())) 
			{
				String url = "http://"+node.getNodeIp()+node.getUrl()+env.getProperty("status.power.trgl");		
				view = restTemplate.getForObject(url, ArrayList.class);
			}
			
			
		} 
		catch (Exception e) 
		{
			logger.info("Exception in  method : powerSystemStatus ,message : "+e.getMessage());
		}
		logger.info("exiting testIpConnections powerSystemStatus");
        return view;
	}

	@Override
	@RequestMapping("/testfalcon")
	public ArrayList<LinkedHashMap<String,String>> testFalconConnections() {
		logger.info("inside testFalconConnections powerSystemStatus");
		RestTemplate restTemplate = new RestTemplate();
		
		ArrayList<LinkedHashMap<String,String>> view = null;
		
		Nodes node = ns.getNodeDetailByNodeType("TRGL");
		try 
		{
					
			
			if(cm.pingIp(node.getNodeIp())) 
			{
				String url = "http://"+node.getNodeIp()+node.getUrl()+env.getProperty("status.bist.tmdas");
				view = restTemplate.getForObject(url, ArrayList.class);	
			}
			
			
			
		} 
		catch (Exception e) 
		{
			logger.info("Exception in  method : powerSystemStatus ,message : "+e.getMessage());
		}
		logger.info("exiting testIpConnections powerSystemStatus");
        return view;
	}

	@Override
	@RequestMapping("/testhummer")
	public ArrayList<LinkedHashMap<String,String>> testHummerConnections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@RequestMapping("/testadu")
	public ArrayList<LinkedHashMap<String, String>> alarmDisplayTest(@RequestParam("node") String nodeType,@RequestParam("cmd") String cmd) 
	{
		Nodes node = ns.getNodeDetailByNodeType(nodeType);
		String result = cm.sendAdu(nodeType,cmd);
		Nodes node1 =  ns.getNodeDetailByNodeType("ADU");
		LinkedHashMap<String, String> hs = new LinkedHashMap<String, String>();
		hs.put("NAME", node.getDisplayName()+" "+ node1.getDisplayName());
		hs.put("IP", node1.getNodeIp());
		hs.put("RESULT", "DOWN");
		if(result.equalsIgnoreCase("1")) 
		{
			hs.put("RESULT", "OK");
		}
		ArrayList<LinkedHashMap<String, String>> aa = new ArrayList<LinkedHashMap<String, String>>();
		aa.add(hs);
				
		return aa;
		
	}
	
	
	public ArrayList<LinkedHashMap<String,String>> checkIpConectionOfEachSubNodes(String type) 
	{
		switch(type)
		{
			case "UGS":
				UgsInventory ui = ic.getUgsInvetory();
				return null;
			case "TMDAS":
				Nodes nodeTMDAS = ns.getNodeDetailByNodeType("TMDAS");
				return crc.getSubInventory( Math.toIntExact(nodeTMDAS.getNodeId()),nodeTMDAS.getNodeIp());
			case "TRGL":
				Nodes nodeTRGL = ns.getNodeDetailByNodeType("TRGL");
				return crc.getSubInventory((int)(long)nodeTRGL.getNodeId(),nodeTRGL.getNodeIp());
		}
		logger.info("Inventory getting done");
		return null;
		
		
		
		
		
	}
	
	
	
}
