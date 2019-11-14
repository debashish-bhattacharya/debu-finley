package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UgsDevice {

	private int DEVICE_ID;
	private double LATITUDE;
	private double LONGITUDE;
	public int DEVICE_TYPE;
	public int COVERAGE;
	private String ip;
	private int state;
	
	public UgsDevice() 
	{
		
	}
	

	public UgsDevice(int dEVICE_ID, double lATITUDE, double lONGITUDE, int dEVICE_TYPE, int cOVERAGE, String ip,int state) {
		super();
		DEVICE_ID = dEVICE_ID;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		DEVICE_TYPE = dEVICE_TYPE;
		COVERAGE = cOVERAGE;
		this.ip = ip;
		this.state = state;
	}

	public int getDeviceId() {
		return DEVICE_ID;
	}
	
	@JsonProperty("DEVICE_ID")
	public void setDeviceId(int DEVICE_ID) {
		this.DEVICE_ID = DEVICE_ID;
	}
	
	public double getLatitude() {
		return LATITUDE;
	}
	
	@JsonProperty("LATITUDE")
	public void setLatitude(double LATITUDE) {
		this.LATITUDE = LATITUDE;
	}
	
	public double getLongitude() {
		return LONGITUDE;
	}
	
	@JsonProperty("LONGITUDE")
	public void setLongitude(double LONGITUDE) {
		this.LONGITUDE = LONGITUDE;
	}

	public String getIp() {
		return ip;
	}	

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getState() {
		return state;
	}
	
	public int getDEVICE_TYPE() {
		return DEVICE_TYPE;
	}
	
	@JsonProperty("DEVICE_TYPE")
	public void setDEVICE_TYPE(int dEVICE_TYPE) {
		DEVICE_TYPE = dEVICE_TYPE;
	}

	public int getCOVERAGE() {
		return COVERAGE;
	}
	@JsonProperty("COVERAGE")
	public void setCOVERAGE(int cOVERAGE) {
		COVERAGE = cOVERAGE;
	}

	@JsonProperty("STATE")
	public void setState(int state) {
		this.state = state;
	}
	
	@Override
    public String toString() {
        return "Id-"+DEVICE_ID+",lat-"+LATITUDE+",lon"+LONGITUDE+",ip"+ip+",state"+state;
    }	
}
