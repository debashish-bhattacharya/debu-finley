package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import in.vnl.repository.NodesService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Inventory 
{
	
	@Autowired
	private NodesService ns;
	
	public static HashMap<String,HashMap<String,HashMap<String,Object>>> inventory = new HashMap<String,HashMap<String,HashMap<String,Object>>>();
	
	
	
	
	public void createUgsInvetory (UgsInventory ugsInventory) 
	{
		HashMap<String,HashMap<String,Object>> devices = new HashMap<String,HashMap<String,Object>>();
		
		try {
			HashMap<String, Object> device = new HashMap<String, Object>();
			UgsDevice[] ugsDevices = ugsInventory.getDeviceInfo();
			String ip = ugsInventory.getIp();
			for (UgsDevice ugsDevice : ugsDevices) {
				int id = ugsDevice.getDeviceId();
				device.put(ip + "-" + id, ugsDevice);
			}
			devices.put(ip, device);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.inventory.put("UGS", devices);
	}
	
	
	public void createTrglInvetory (TrglInventory trglInventory) 
	{
		HashMap<String,HashMap<String,Object>> devices = new HashMap<String,HashMap<String,Object>>();
		
		try {
			HashMap<String, Object> device = new HashMap<String, Object>();
			TrglDevice[] trglDevices = trglInventory.getDeviceInfo();
			String ip = trglInventory.getIp();
			for (TrglDevice trglDevice : trglDevices) {
				String id = trglDevice.getDEVICE_IP();
				device.put(ip + "-" + id, trglDevice);
			}
			devices.put(ip, device);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.inventory.put("TRGL", devices);
	}
	
	
	
	public void createTMDASInvetory (TmdasInventory tmdasInventory) 
	{
		HashMap<String,HashMap<String,Object>> devices = new HashMap<String,HashMap<String,Object>>();
		
		try {
			HashMap<String, Object> device = new HashMap<String, Object>();
			TmdasDevice[] tmdasDevices = tmdasInventory.getDeviceInfo();
			String ip = tmdasInventory.getIp();
			for (TmdasDevice tmdasDevice : tmdasDevices) {
				String id = tmdasDevice.getDEVICE_IP();
				device.put(ip + "-" + id, tmdasDevice);
			}
			devices.put(ip, device);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.inventory.put("TMDAS", devices);
	}
	
	
	public HashMap<String,HashMap<String,HashMap<String,Object>>> getInventory()
	{
		return this.inventory;
	}
	
	
	public void createInventoryCache() 
	{
		HashMap<String,String> hm = new HashMap<String,String>();
		List<Nodes> nodes= ns.getNodes();
		
		for(int i=0;i<nodes.size();i++) 
		{
			nodes.get(i).getNodeIp();
			hm.put(nodes.get(i).getNodeName(), nodes.get(i).getNodeIp());
		}
		
		
	}
	
	
	public HashMap<String,String> createAddedNodesList()
	{
		Resource resource = new ClassPathResource("/static/nodes.csv");		 
		String url = null;
		BufferedReader br = null;
		
		HashMap<String,String> hm = new HashMap<String,String>();
		try 
		{
			
			
			
			url = resource.getURL().getPath();
			br = new BufferedReader(new FileReader(url));
			String line = br.readLine();
			
			
			while(line != null) 
			{
				String a[] = line.split(",");
				
				hm.put(a[0], a[1]);
				
				System.out.println(line);
				line  = br.readLine();
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(br != null) 
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return hm;
		
	}
	
	
	public void createNewNodesList(Nodes_old node) 
	{
					
					
					FileWriter fw=null;
					PrintWriter pw = null;
	   
		         try
		         {    
		        	 Resource resource = new ClassPathResource("/static/nodes.csv");
		        	 
		        	 String url = resource.getURL().getPath();
		        	 url = resource.getURL().getPath();
		        	 fw=new FileWriter(url);
		        	 pw = new PrintWriter(fw);
		        	 HashMap<String,String> data = node.getNodes();
		        	 
		        	 for (String key : data.keySet()) 
		        	 {
		        		 //fw.write();
		        		 pw.println(key+","+data.get(key));
		        	 }
		        	 
		        	     
		        	     
		          }
		         catch(Exception e)
		         {System.out.println(e);}
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
	
}
