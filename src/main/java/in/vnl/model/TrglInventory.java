package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TrglInventory {

	
	
	private String ip;
	private TrglDevice[] DeviceInfo;
	
	
	public TrglInventory() 
	{
		
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}

	
	public TrglDevice[] getDeviceInfo() {
		return DeviceInfo;
	}

	
	@JsonProperty("DeviceInfo")
	public void setDeviceInfo(TrglDevice[] DeviceInfo) {
		this.DeviceInfo = DeviceInfo;
	}
	
	
	}
