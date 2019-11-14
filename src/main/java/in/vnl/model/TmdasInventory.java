package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)

public class TmdasInventory {

	
	
	private String ip;
	private TmdasDevice[] DeviceInfo;
	
	
	public TmdasInventory() 
	{
		
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}

	
	public TmdasDevice[] getDeviceInfo() {
		return DeviceInfo;
	}

	
	@JsonProperty("DeviceInfo")
	public void setDeviceInfo(TmdasDevice[] DeviceInfo) {
		this.DeviceInfo = DeviceInfo;
	}
	
	
	}
