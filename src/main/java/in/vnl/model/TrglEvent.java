package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrglEvent {

	private String ip;
	private String TIME_STAMP;
	private String TRANS_ID;
	private double FREQ;
	private double ANGLE;
	private double POWER;
	private double PROBABLE_DISTANCE;
	private int EVENT_TYPE;	
	private double LATITUDE;	
	private double LONGITUDE;
	private String CUE_ID;
	
	
	public TrglEvent() 
	{
		
	}
	
	public TrglEvent(String ip, String tIME_STAMP, String tRANS_ID, String dEVICE_IP, double fREQ, double aNGLE,
			double pOWER, double pROBABLE_DISTANCE, int eVENT_TYPE) {
		super();
		this.ip = ip;
		TIME_STAMP = tIME_STAMP;
		TRANS_ID = tRANS_ID;
		//DEVICE_IP = dEVICE_IP;
		FREQ = fREQ;
		ANGLE = aNGLE;
		POWER = pOWER;
		PROBABLE_DISTANCE = pROBABLE_DISTANCE;
		EVENT_TYPE = eVENT_TYPE;
	}
	
	
	
	public TrglEvent(String ip, String tIME_STAMP, String tRANS_ID, double fREQ, double aNGLE, double pOWER,
			double pROBABLE_DISTANCE, int eVENT_TYPE, double lATITUDE, double lONGITUDE, String cUE_ID) {
		super();
		this.ip = ip;
		TIME_STAMP = tIME_STAMP;
		TRANS_ID = tRANS_ID;
		FREQ = fREQ;
		ANGLE = aNGLE;
		POWER = pOWER;
		PROBABLE_DISTANCE = pROBABLE_DISTANCE;
		EVENT_TYPE = eVENT_TYPE;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		CUE_ID = cUE_ID;
	}

	public String getIp() {
		return ip;
	}
	
	@JsonProperty("DEVICE_IP")
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getTIME_STAMP() {
		return TIME_STAMP;
	}
	
	
	@JsonProperty("TIME_STAMP")
	public void setTIME_STAMP(String tIME_STAMP) {
		TIME_STAMP = tIME_STAMP;
	}
	
	public String getTRANS_ID() {
		return TRANS_ID;
	}
	
	@JsonProperty("TRANS_ID")
	public void setTRANS_ID(String tRANS_ID) {
		TRANS_ID = tRANS_ID;
	}

	public double getFREQ() {
		return FREQ;
	}
	
	@JsonProperty("FREQ")
	public void setFREQ(double fREQ) {
		FREQ = fREQ;
	}
	public double getANGLE() {
		return ANGLE;
	}
	
	@JsonProperty("ANGLE")
	public void setANGLE(double aNGLE) {
		ANGLE = aNGLE;
	}
	public double getPOWER() {
		return POWER;
	}
	
	@JsonProperty("POWER")
	public void setPOWER(double pOWER) {
		POWER = pOWER;
	}
	public double getPROBABLE_DISTANCE() {
		return PROBABLE_DISTANCE;
	}
	
	
	@JsonProperty("PROBABLE_DISTANCE")
	public void setPROBABLE_DISTANCE(double pROBABLE_DISTANCE) {
		PROBABLE_DISTANCE = pROBABLE_DISTANCE;
	}
	public int getEVENT_TYPE() {
		return EVENT_TYPE;
	}
	
	@JsonProperty("EVENT_TYPE")
	public void setEVENT_TYPE(int eVENT_TYPE) {
		EVENT_TYPE = eVENT_TYPE;
	}
	
	
	
	public double getLATITUDE() {
		return LATITUDE;
	}

	@JsonProperty("LATITUDE")
	public void setLATITUDE(double lATITUDE) {
		LATITUDE = lATITUDE;
	}

	public double getLONGITUDE() {
		return LONGITUDE;
	}

	@JsonProperty("LONGITUDE")
	public void setLONGITUDE(double lONGITUDE) {
		LONGITUDE = lONGITUDE;
	}

	public String getCUE_ID() {
		return CUE_ID;
	}
	@JsonProperty("CUE_ID")
	public void setCUE_ID(String cUE_ID) {
		CUE_ID = cUE_ID;
	}
	
	
	

}
