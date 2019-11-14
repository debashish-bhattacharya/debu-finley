package in.vnl.contract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.springframework.web.bind.annotation.RequestParam;

public interface Bist 
{
	public ArrayList<LinkedHashMap<String,Object>> testIpConnections();
	public ArrayList<LinkedHashMap<String,String>> powerSystemStatus();
	public ArrayList<LinkedHashMap<String,String>> testFalconConnections();
	public ArrayList<LinkedHashMap<String,String>> testHummerConnections();
	public ArrayList<LinkedHashMap<String, String>> alarmDisplayTest(@RequestParam String nodeType,@RequestParam String cmd);
}
