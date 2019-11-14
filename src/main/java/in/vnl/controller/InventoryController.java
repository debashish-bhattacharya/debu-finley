package in.vnl.controller;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import in.vnl.common.Common;
import in.vnl.model.Cell;
import in.vnl.model.Inventory;
import in.vnl.model.Nodes;
import in.vnl.model.Nodes_old;
import in.vnl.model.TmdasDevice;
import in.vnl.model.TmdasInventory;
import in.vnl.model.TrglDevice;
import in.vnl.model.TrglInventory;
import in.vnl.model.UgsDevice;
import in.vnl.model.UgsInventory;
import in.vnl.repository.CellService;
import in.vnl.repository.NodesService;
import in.vnl.security.ValidNodeIp;


@RestController
@RequestMapping("/inventory")
public class InventoryController 
{
	public static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
	
	//@Autowired	
	//private Inventory inventory;
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private NodesService ns;
	
	

	
	@Autowired
	private Common com;
	
	@Autowired
	private CellService cs;
	
	@ValidNodeIp
	@RequestMapping("/ugs")
	public UgsInventory getUgsInvetory() 
	{
		
		logger.info("Inside getUgsInvetory method");
		RestTemplate restTemplate = new RestTemplate();
		
		Inventory in = new Inventory();
		//String ugsUrl = in.createAddedNodesList().get("UGS");
		
		Nodes UGS = ns.getNodeDetailByNodeType("UGS");
		
		String ugsUrl = UGS.getNodeIp();
		String UGSPath = UGS.getUrl();
		
		UgsInventory view = new UgsInventory();
		view.setDeviceInfo(new UgsDevice[0]);
		view.setIp(ugsUrl);
		in.createUgsInvetory(view);
		
		try 
		{
			String url = createNodeUrl("UGS",ugsUrl,UGSPath);
			if(com.pingIp(ugsUrl)) 
			{
				view = restTemplate.getForObject(url, UgsInventory.class);
				view.setIp(ugsUrl);
				in.createUgsInvetory(view);
			}
		} 
		catch (Exception e) 
		{
			logger.info("Exception while getting Ineventory for UGS : "+e.getMessage());
		}
		
		logger.info("Exiting getUgsInvetory method");
        return view;
	}
	
	
	@RequestMapping("/trgl")
	public TrglInventory getTrglInvetory() 
	{
		logger.info("Inside getTrglInvetory method");
		RestTemplate restTemplate = new RestTemplate();
		Inventory in = new Inventory();
		
		Nodes node = ns.getNodeDetailByNodeType("TRGL");
		
		String trglUrl = node.getNodeIp();
		String nodePath = node.getUrl();
		
		TrglInventory view = new TrglInventory();
		view.setDeviceInfo(new TrglDevice[0]);
		view.setIp(trglUrl);
		
		
		try 
		{
				//view = restTemplate.getForObject("http://"+trglUrl+"/cms/trgl.json", TrglInventory.class);
			
				String url = createNodeUrl("TRGL",trglUrl,nodePath);
				if(com.pingIp(trglUrl)) 
				{
					view = restTemplate.getForObject(url, TrglInventory.class);				
					view.setIp(trglUrl);
					in.createTrglInvetory(view);
				}
				
		}
		catch (Exception e) 
		{
			logger.info("Exception while getting Ineventory for TRGL : "+e.getMessage());
			e.printStackTrace();
		}
		
		logger.info("Exiting getTrglInvetory method");
        return view;
	}
	
	@RequestMapping("/tmdas")
	public TmdasInventory getTmdasInvetory() 
	{
		logger.info("Inside getTmdasInvetory method");
		
		RestTemplate restTemplate = new RestTemplate();
		Inventory in = new Inventory();		
		Nodes node = ns.getNodeDetailByNodeType("TMDAS");
		
		String tmdasUrl = node.getNodeIp();
		String tmdasPath = node.getUrl();
		
		TmdasInventory view = new TmdasInventory();
		view.setDeviceInfo(new TmdasDevice[0]);
		view.setIp(tmdasUrl);
		
		try 
		{
			String url = createNodeUrl("TMDAS",tmdasUrl,tmdasPath);
			
			if(com.pingIp(tmdasUrl)) 
			{
				view = restTemplate.getForObject(url, TmdasInventory.class);			
				view.setIp(tmdasUrl);
				in.createTMDASInvetory(view);
			}
			
		} 
		catch (Exception e) 
		{
			logger.info("Exception while getting Ineventory for TMDAS : "+e.getMessage());
			e.printStackTrace();			
		}
		
		logger.info("Exiting getTmdasInvetory method");
        return view;
	}
	
	@RequestMapping("/")
	public  HashMap<String,HashMap<String,HashMap<String,Object>>> getInventory(String url) 
	{
		return new Inventory().getInventory();
	} 
	
	
	@RequestMapping("/nodes")
	public Nodes_old createAddedNodesList() 
	{
		 Nodes_old ns = new Nodes_old();
		 ns.setNodes(new Inventory().createAddedNodesList());
		 return ns;
	}
	
	
	@RequestMapping("/updateNodes")
	public int genrateNodeList(Nodes node) 
	{
		
		int result = ns.updateNodeIp(node);
		
		String type = ns.findNodeByIp(node.getNodeIp()).getNodeName();
		if(type != null && type.equalsIgnoreCase("tmdas")) 
		{
			getCells();
		}
		return result;
	}
	
	
	@RequestMapping("/tmdas/offset")
	public String updatTmdasOffset() 
	{
		getTmdasInvetory();
		this.template.convertAndSend("/topic/reload", "{\"status\":\"reload\"}");
		return "ok";
	}
	
	@RequestMapping("/trgl/offset")
	public String updatTrglOffset() 
	{
		getTrglInvetory();
		this.template.convertAndSend("/topic/reload", "{\"status\":\"reload\"}");
		return "ok";
	}
	
	
	public String createNodeUrl(String node,String ip,String path) 
	{
		
		if(path == null) 
		{
			path = "";
		}
		
		
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
		  return url;
	}
	
	
	@PostConstruct
    @Scheduled(fixedRateString="86400000")
	public void getCells() 
	{
		
		Nodes tmdas = ns.getNodeDetailByNodeType("TMDAS");
		
		String tmdasUrl = tmdas.getNodeIp();
		String tmdasPath = tmdas.getUrl();
		RestTemplate restTemplate = new RestTemplate();
		try {
			
			if(com.pingIp(tmdasUrl)) 
			{
				String url = "http://"+tmdasUrl+tmdasPath+env.getProperty("cells.tmdas");
				Cell[] view = restTemplate.getForObject(url, Cell[].class);
				//List<Cell> view = restTemplate.getForObject(url, List.class);
				cs.saveCells(view);
				this.template.convertAndSend("/topic/reload", "{\"status\":\"reload\"}");
			}
			else 
			{
				throw new Exception();
			}
			
		} catch (Exception e) {
			
		}
	}
	
	
	@PostConstruct
    @Scheduled(fixedRateString="10")
	public void testCells() 
	{
		try
		{
			this.template.convertAndSend("/topic/testingQueue", "Hello Sunil!");
		} 
		catch (Exception e) 
		{
			
		}
	}
	
	
	
	@PostConstruct
	public void createCache() 
	{
		//createAddedNodesList();
		getUgsInvetory();
		getTmdasInvetory();
		getTrglInvetory();
	}
	
	@SendTo("/topic/cell")
    public String greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Status Socket Hello";
    }
	
	@SendTo("/topic/testingQueue")
    public String testingQueue() throws Exception {
        return "testingQueue";
    }
	
	
	@RequestMapping("/cells")
	public List<Cell> cells()
	{
		return cs.getCells();
	}
	
	
}
