package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TmdasDevice {

	private String DEVICE_IP;
	private double LATITUDE;
	private double LONGITUDE;
	private double OFFSET;
	private double COVERAGE;
	//private Cell[] CELLS;
	private int state;
	
	public TmdasDevice() 
	{
		
	}
	
	

		/*public TmdasDevice(String dEVICE_IP, double lATITUDE, double lONGITUDE, double oFFSET, double cOVERAGE, Cell[] cell,
			int state) {
		super();
		DEVICE_IP = dEVICE_IP;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		OFFSET = oFFSET;
		COVERAGE = cOVERAGE;
		this.CELLS = cell;
		this.state = state;
	}*/

		
		


		public TmdasDevice(String dEVICE_IP, double lATITUDE, double lONGITUDE, double oFFSET, double cOVERAGE, int state) {
		super();
		DEVICE_IP = dEVICE_IP;
		LATITUDE = lATITUDE;
		LONGITUDE = lONGITUDE;
		OFFSET = oFFSET;
		COVERAGE = cOVERAGE;
		this.state = state;
	}



		public String getDEVICE_IP() {
			return DEVICE_IP;
		}


		@JsonProperty("DEVICE_IP")
		public void setDEVICE_IP(String dEVICE_IP) {
			DEVICE_IP = dEVICE_IP;
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



		public double getOFFSET() {
			return OFFSET;
		}


		@JsonProperty("OFFSET")
		public void setOFFSET(double oFFSET) {
			OFFSET = oFFSET;
		}



		public double getCOVERAGE() {
			return COVERAGE;
		}


		@JsonProperty("COVERAGE")
		public void setCOVERAGE(double cOVERAGE) {
			COVERAGE = cOVERAGE;
		}



		/*public Cell[] getCell() {
			return CELLS;
		}


		@JsonProperty("CELLS")
		public void setCell(Cell[] cell) {
			this.CELLS = cell;
		}*/



		public int getState() {
			return state;
		}


		@JsonProperty("STATE")
		public void setState(int state) {
			this.state = state;
		}



		@Override
		public String toString() 
		{
			return "IP-"+DEVICE_IP+",lat-"+LATITUDE+",lon-"+LONGITUDE+",OFFSET-"+OFFSET+",COVERAGE-"+COVERAGE+",state-"+state;
		}
}
