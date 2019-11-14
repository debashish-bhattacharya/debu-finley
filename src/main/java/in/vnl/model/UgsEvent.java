package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UgsEvent {

	private String ip;
	private String DEVICE_IP;
	private String TIME_STAMP;
	private int DEVICE_ID;
	private double LATITUDE;
	private double LONGITUDE;
	private double SIGNAL_STRENGTH;
	private int DEVICE_TYPE;
	private int EVENT_TYPE;
	private int COVERAGE;
	private String CLASIFICATION;
	private String TRANS_ID;
	private String CUE_ID;
	private int TIMEOUT;
	
	public UgsEvent() 
	{
		
	}
	

	public UgsEvent(String ip, int dEVICE_ID, double lATITUDE, double lONGITUDE, double sIGNAL_STRENGTH,
			int dEVICE_TYPE, int eVENT_TYPE) {
		super();
		DEVICE_IP = ip;
		DEVICE_ID = dEVICE_ID;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		DEVICE_TYPE = dEVICE_TYPE;
		EVENT_TYPE = eVENT_TYPE;
	}
	
	
	


	
	public UgsEvent(String ip, String dEVICE_IP, String tIME_STAMP, int dEVICE_ID, double lATITUDE, double lONGITUDE,
			double sIGNAL_STRENGTH, int dEVICE_TYPE, int eVENT_TYPE, int cOVERAGE, String cLASIFICATION,
			String tRANS_ID) {
		super();
		this.ip = ip;
		DEVICE_IP = dEVICE_IP;
		TIME_STAMP = tIME_STAMP;
		DEVICE_ID = dEVICE_ID;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		DEVICE_TYPE = dEVICE_TYPE;
		EVENT_TYPE = eVENT_TYPE;
		COVERAGE = cOVERAGE;
		CLASIFICATION = cLASIFICATION;
		TRANS_ID = tRANS_ID;
		CUE_ID = tRANS_ID;
	}
	
	


	public UgsEvent(String ip, String dEVICE_IP, String tIME_STAMP, int dEVICE_ID, double lATITUDE, double lONGITUDE,
			double sIGNAL_STRENGTH, int dEVICE_TYPE, int eVENT_TYPE, int cOVERAGE, String cLASIFICATION,
			String tRANS_ID, String cUE_ID, int tIMEOUT) {
		super();
		this.ip = ip;
		DEVICE_IP = dEVICE_IP;
		TIME_STAMP = tIME_STAMP;
		DEVICE_ID = dEVICE_ID;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		DEVICE_TYPE = dEVICE_TYPE;
		EVENT_TYPE = eVENT_TYPE;
		COVERAGE = cOVERAGE;
		CLASIFICATION = cLASIFICATION;
		TRANS_ID = tRANS_ID;
		CUE_ID = cUE_ID;
		TIMEOUT = tIMEOUT;
	}


	public String getDEVICE_IP() {
		return DEVICE_IP;
	}


	@JsonProperty("DEVICE_IP")
	public void setDEVICE_IP(String dEVICE_IP) {
		DEVICE_IP = dEVICE_IP;
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

	
	public double getSIGNAL_STRENGTH() {
		return SIGNAL_STRENGTH;
	}

	@JsonProperty("SIGNAL_STRENGTH")
	public void setSIGNAL_STRENGTH(double sIGNAL_STRENGTH) {
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
	}


	public int getDEVICE_TYPE() {
		return DEVICE_TYPE;
	}

	@JsonProperty("DEVICE_TYPE")
	public void setDEVICE_TYPE(int dEVICE_TYPE) {
		DEVICE_TYPE = dEVICE_TYPE;
	}


	public int getEVENT_TYPE() {
		return EVENT_TYPE;
	}

	@JsonProperty("EVENT_TYPE")
	public void setEVENT_TYPE(int eVENT_TYPE) {
		EVENT_TYPE = eVENT_TYPE;
	}


	public int getCOVERAGE() {
		return COVERAGE;
	}

	@JsonProperty("COVERAGE")
	public void setCOVERAGE(int cOVERAGE) {
		COVERAGE = cOVERAGE;
	}
	
	
	public String getTIME_STAMP() {
		return TIME_STAMP;
	}

	@JsonProperty("TIME_STAMP")
	public void setTIME_STAMP(String tIME_STAMP) {
		TIME_STAMP = tIME_STAMP;
	}
	
	
	


	public String getCLASIFICATION() {
		return CLASIFICATION;
	}

	@JsonProperty("CLASIFICATION")
	public void setCLASIFICATION(String cLASIFICATION) {
		CLASIFICATION = cLASIFICATION;
	}


	public String getTRANS_ID() {
		return TRANS_ID;
	}

	@JsonProperty("TRANS_ID")
	public void setTRANS_ID(String tRANS_ID) {
		TRANS_ID = tRANS_ID;
	}
	
	
	public String getCUE_ID() {
		return TRANS_ID;
	}

	@JsonProperty("CUE_ID")
	public void setCUE_ID(String cUE_ID) {
		CUE_ID = CUE_ID;
	}
	
	
	


	public int getTIMEOUT() {
		return TIMEOUT;
	}

	@JsonProperty("TIMEOUT")
	public void setTIMEOUT(int tIMEOUT) {
		TIMEOUT = tIMEOUT;
	}


	@Override
    public String toString() {
        return "Id-"+DEVICE_ID+",lat-"+LATITUDE+",lon"+LONGITUDE+",ip"+ip+",SIGNAL_STRENGTH"+SIGNAL_STRENGTH;
    }	
}
