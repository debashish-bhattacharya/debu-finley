package in.vnl.model;

public class DeviceStatus 
{
	
	private String  IP;
	private String  TYPE;
	private String  MODE;
	private String  SPACE;
	private String  STATUS;
	private String  FREEZE;
	
	public DeviceStatus() {}
	
	/*public DeviceStatus(String iP, String tYPE, String mODE, String sPACE, String sTATUS) {
		super();
		IP = iP;
		TYPE = tYPE;
		MODE = mODE;
		SPACE = sPACE;
		STATUS = sTATUS;
	}*/
	
	

	public DeviceStatus(String iP, String tYPE, String mODE, String sPACE, String sTATUS, String fREEZE) {
		super();
		IP = iP;
		TYPE = tYPE;
		MODE = mODE;
		SPACE = sPACE;
		STATUS = sTATUS;
		FREEZE = fREEZE;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getTYPE() {
		return TYPE;
	}

	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}

	public String getMODE() {
		return MODE;
	}

	public void setMODE(String mODE) {
		MODE = mODE;
	}

	public String getSPACE() {
		return SPACE;
	}

	public void setSPACE(String sPACE) {
		SPACE = sPACE;
	}

	public String getSTATUS() {
		return STATUS;
	}

	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}

	public String getFREEZE() {
		return FREEZE;
	}

	public void setFREEZE(String fREEZE) {
		FREEZE = fREEZE;
	}
	
	
	
	
}
