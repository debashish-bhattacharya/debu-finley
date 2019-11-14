package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdasEvent {

	private String ip;
	private String TIME_STAMP;
	private String TRANS_ID;
	private int OBJECT_TYPE;
	private int TA;
	private double SIGNAL_STRENGTH;
	private int EVENT_TYPE;
	private double LATITUDE;
	private double LONGITUDE;
	private int OFFSET;
	private String IMEI;
	private String IMSI;
	private double FREQ;
	private int RXL;
	private String CUE_ID;
	private double PROBABLE_DISTANCE;
	private double ANGLE;
	private String SECTOR;
	
	

	public TmdasEvent() 
	{
	
	}
	
	

	public TmdasEvent(String ip, String tIME_STAMP, String tRANS_ID, int oBJECT_TYPE, int tA, double sIGNAL_STRENGTH,
			int eVENT_TYPE, double lATITUDE, double lONGITUDE, int oFFSET) {
		super();
		this.ip = ip;
		TIME_STAMP = tIME_STAMP;
		TRANS_ID = tRANS_ID;
		OBJECT_TYPE = oBJECT_TYPE;
		TA = tA;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		EVENT_TYPE = eVENT_TYPE;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		OFFSET = oFFSET;
	}
	
	
	
	
	

	public TmdasEvent(String ip, String tIME_STAMP, String tRANS_ID, int oBJECT_TYPE, int tA, double sIGNAL_STRENGTH,
			int eVENT_TYPE, double lATITUDE, double lONGITUDE, int oFFSET, String iMEI, String iMSI, double fREQ, int rXL) {
		super();
		this.ip = ip;
		TIME_STAMP = tIME_STAMP;
		TRANS_ID = tRANS_ID;
		OBJECT_TYPE = oBJECT_TYPE;
		TA = tA;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		EVENT_TYPE = eVENT_TYPE;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		OFFSET = oFFSET;
		IMEI = iMEI;
		IMSI = iMSI;
		FREQ = fREQ;
		RXL = rXL;
	}
	

	public TmdasEvent(String ip, String tIME_STAMP, String tRANS_ID, int oBJECT_TYPE, int tA, double sIGNAL_STRENGTH,
			int eVENT_TYPE, double lATITUDE, double lONGITUDE, int oFFSET, String iMEI, String iMSI, double fREQ, int rXL,
			String cUE_ID, double pROBABLE_DISTANCE) {
		super();
		this.ip = ip;
		TIME_STAMP = tIME_STAMP;
		TRANS_ID = tRANS_ID;
		OBJECT_TYPE = oBJECT_TYPE;
		TA = tA;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		EVENT_TYPE = eVENT_TYPE;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		OFFSET = oFFSET;
		IMEI = iMEI;
		IMSI = iMSI;
		FREQ = fREQ;
		RXL = rXL;
		CUE_ID = cUE_ID;
		PROBABLE_DISTANCE = pROBABLE_DISTANCE;
	}



	public TmdasEvent(String ip, String tIME_STAMP, String tRANS_ID, int oBJECT_TYPE, int tA, double sIGNAL_STRENGTH,
			int eVENT_TYPE, double lATITUDE, double lONGITUDE, int oFFSET, String iMEI, String iMSI, double fREQ, int rXL,
			String cUE_ID) {
		super();
		this.ip = ip;
		TIME_STAMP = tIME_STAMP;
		TRANS_ID = tRANS_ID;
		OBJECT_TYPE = oBJECT_TYPE;
		TA = tA;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		EVENT_TYPE = eVENT_TYPE;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		OFFSET = oFFSET;
		IMEI = iMEI;
		IMSI = iMSI;
		FREQ = fREQ;
		RXL = rXL;
		CUE_ID = cUE_ID;
	}
	
	



	public TmdasEvent(String ip, String tIME_STAMP, String tRANS_ID, int oBJECT_TYPE, int tA, double sIGNAL_STRENGTH,
			int eVENT_TYPE, double lATITUDE, double lONGITUDE, int oFFSET, String iMEI, String iMSI, double fREQ, int rXL,
			String cUE_ID, double pROBABLE_DISTANCE, double aNGLE, String sECTOR) {
		super();
		this.ip = ip;
		TIME_STAMP = tIME_STAMP;
		TRANS_ID = tRANS_ID;
		OBJECT_TYPE = oBJECT_TYPE;
		TA = tA;
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
		EVENT_TYPE = eVENT_TYPE;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		OFFSET = oFFSET;
		IMEI = iMEI;
		IMSI = iMSI;
		FREQ = fREQ;
		RXL = rXL;
		CUE_ID = cUE_ID;
		PROBABLE_DISTANCE = pROBABLE_DISTANCE;
		ANGLE = aNGLE;
		SECTOR = sECTOR;
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
	
	public int getEVENT_TYPE() {
		return EVENT_TYPE;
	}
	
	@JsonProperty("EVENT_TYPE")
	public void setEVENT_TYPE(int eVENT_TYPE) {
		EVENT_TYPE = eVENT_TYPE;
	}
	
	
	public int getOBJECT_TYPE() {
		return OBJECT_TYPE;
	}
	@JsonProperty("OBJECT_TYPE")
	public void setOBJECT_TYPE(int oBJECT_TYPE) {
		OBJECT_TYPE = oBJECT_TYPE;
	}

	public int getTA() {
		return TA;
	}

	
	@JsonProperty("TA")
	public void setTA(int tA) {
		TA = tA;
	}

	public double getSIGNAL_STRENGTH() {
		return SIGNAL_STRENGTH;
	}

	
	@JsonProperty("SIGNAL_STRENGTH")
	public void setSIGNAL_STRENGTH(double sIGNAL_STRENGTH) {
		SIGNAL_STRENGTH = sIGNAL_STRENGTH;
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
	
	
	public int getOFFSET() {
		return OFFSET;
	}
	
	@JsonProperty("OFFSET")
	public void setOFFSET(int oFFSET) {
		OFFSET = oFFSET;
	}
	
	public String getIMEI() {
		return IMEI;
	}

	
	@JsonProperty("IMEI")
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	

	public String getIMSI() {
		return IMSI;
	}
	
	@JsonProperty("IMSI")
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	
	
	public double getFREQ() {
		return FREQ;
	}
	
	@JsonProperty("FREQ")
	public void setFREQ(double fREQ) {
		FREQ = fREQ;
	}
	
	public int getRXL() {
		return RXL;
	}
	
	@JsonProperty("RXL")
	public void setRXL(int rXL) {
		RXL = rXL;
	}



	public String getCUE_ID() {
		return CUE_ID;
	}


	@JsonProperty("CUE_ID")
	public void setCUE_ID(String cUE_ID) {
		CUE_ID = cUE_ID;
	}



	public double getPROBABLE_DISTANCE() {
		return PROBABLE_DISTANCE;
	}

	@JsonProperty("PROBABLE_DISTANCE")
	public void setPROBABLE_DISTANCE(double pROBABLE_DISTANCE) {
		PROBABLE_DISTANCE = pROBABLE_DISTANCE;
	}



	public double getANGLE() {
		return ANGLE;
	}


	@JsonProperty("ANGLE")
	public void setANGLE(double aNGLE) {
		ANGLE = aNGLE;
	}



	public String getSECTOR() {
		return SECTOR;
	}


	@JsonProperty("SECTOR")
	public void setSECTOR(String sECTOR) {
		SECTOR = sECTOR;
	}
	
	
	
	
}
