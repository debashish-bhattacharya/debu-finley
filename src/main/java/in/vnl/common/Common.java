package in.vnl.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import in.vnl.controller.SchedulController;
import in.vnl.model.Command;
import in.vnl.model.Nodes;
import in.vnl.repository.CommandService;
import in.vnl.repository.NodesService;
@Service
public class Common {
	
	private static final Logger logger = LoggerFactory.getLogger(SchedulController.class);
	
	@Autowired
	private NodesService ns;
	
	@Autowired
	private CommandService cs;
	
	@Autowired
	private Environment env;
	
	public static HashMap<String,String> nodes = new HashMap<String,String>();
	
	
	public String sendAdu(String nodeType,String command) 
	  {
		  try 
		  {
			  Nodes node =  ns.getNodeDetailByNodeType("ADU");
			  Long nodeId =  ns.getNodeDetailByNodeType(nodeType).getNodeId();
			  Command cmd = cs.getCommand(nodeId, command);
			  return new UdpClient(node.getNodeIp(),Integer.parseInt(node.getUrl())).sendEcho(cmd.getCmd());
		  }
		  catch(Exception e) 
		  {
			  e.printStackTrace();
		  }
		  return null;
	  }
	
	public boolean pingIp(String ip) 
	{
		boolean result = false;
		logger.info("inside pingIp Method");
		try {
			InetAddress inetAddr = InetAddress.getByName(ip);
			logger.info("Sending Ping Request to : " + ip);
			
			if (inetAddr.isReachable(3000)) {
				result = true;
			} else {
				result = false;
			} 
		} catch (Exception e) {
			logger.info("Error while pinging ip : "+ip+" , Error : "+e.getMessage());
		}
		logger.info("exiting pingIp Method");
		return result;
	}
	
	
	/**
	 * Program will create a new excel sheet 
	 * 
	 * Param file name and data
	 * 
	 * data key will be used for sheet name and value will be used for sheet data
	 * 
	 * 
	 * */
	
	public void createExcelSheet(String fileName,HashMap<String,ArrayList<LinkedHashMap<String,String>>> data) 
	{
		
		
		FileOutputStream out = null;
		XSSFWorkbook workbook = new XSSFWorkbook(); 

		Set ss = data.keySet();
		Iterator itr  = ss.iterator();
		while(itr.hasNext()) 
		{
			String key = (String)itr.next();
			XSSFSheet spreadsheet = workbook.createSheet(key);
			ArrayList<LinkedHashMap<String,String>> sheetData = data.get(key);
			for(int i=0;i<sheetData.size();i++) 
			{
				LinkedHashMap<String,String> rowData = sheetData.get(i);
				XSSFRow row = spreadsheet.createRow(i);
				int col = 0;
				for(Map.Entry<String,String> packet:rowData.entrySet()) 
				{
					String paketKey = packet.getKey();
					String paketData = packet.getValue();
					Cell cell = row.createCell(col++);
		            cell.setCellValue(paketData);
				}
				
			}
			
		}
		
		 try 
		 {
			out = new FileOutputStream(new File(fileName));
			workbook.write(out);
			out.close();
		 } 
		 catch (Exception e) {
			// TODO: handle exception
		}
		  finally 
		  {
			  if(out != null) 
			  {
				  try {
					out.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			  }
		  }
		System.out.println("Writesheet.xlsx written successfull");
	}
	
	/**
	 * Returns temporary directory path
	 * */
	public String temporaryDirectory() 
	{
		logger.info("inside temporaryDirectory");
		String dirPath = null;
		try 
		{
			dirPath = env.getProperty("app.temppath")==null?"":env.getProperty("app.temppath");
			logger.info("Directory path "+dirPath);
			File directory = new File(dirPath);
			
		    if (!directory.exists())
		    {
		        directory.mkdir();
		    }
		} 
		catch (Exception e) 
		{
			logger.info("Exception method getReport :"+e.getMessage());
			e.printStackTrace();
			dirPath="";
		}
		
		long currenttime = System.currentTimeMillis();
		
		
		
		String tempPathOfDirectory = dirPath.equalsIgnoreCase("")?currenttime+"":dirPath+"/"+currenttime;
		File directory = new File(tempPathOfDirectory);
		
	    if (!directory.exists())
	    {
	        directory.mkdir();
	    }
	    
	    logger.info("Exiting temporaryDirectory");
		return tempPathOfDirectory;
	}
	
	
	/**
	 * returns response for the file as attachent in response
	 * */
	
	
	public ResponseEntity createFileAttachmentResponse(String filepath) 
	{
		
		File ff = new File(filepath);
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
	    
		return ResponseEntity.ok()
	            .headers(headers)
	            .contentLength(ff.length())
	            .contentType(MediaType.parseMediaType("application/octet-stream"))
	            .body(resource1);
	}
	
	
	public RestTemplate getRestTemplate(int connectionTimeOut,int readTimeOut) 
	{
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectionTimeOut);
        factory.setReadTimeout(readTimeOut);
        return new RestTemplate(factory);
    }
	
	
	@PostConstruct
	public void initializeNodeCache() 
	{
		List<Nodes> nds = ns.getNodes();
		for(int i=0;i<nds.size();i++) 
		{
			Nodes node = nds.get(i);
			nodes.put(node.getNodeIp(), node.getNodeName());
		}
	}
	
	
	
	
	

}
