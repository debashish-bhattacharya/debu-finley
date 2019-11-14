package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class test {

	private int DEVICE_ID;
	
	public test() 
	{
		
	}
	
	public test(int DEVICE_ID) {
		super();
		this.DEVICE_ID = DEVICE_ID;
	}

	public int getDeviceId() {
		return DEVICE_ID;
	}

	public void setDeviceId(int DEVICE_ID) {
		this.DEVICE_ID = DEVICE_ID;
	}	
	@Override
    public String toString() {
        return "Id-"+DEVICE_ID;
    }	
}
