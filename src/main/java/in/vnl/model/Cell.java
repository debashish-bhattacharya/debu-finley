package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="cell")
public class Cell {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String operator;
	private String country;
	private double longitude;
	private double latitude;
	private String mnc;
	private String mcc;
	private String lac;
	private String cell;
	private int rssi;
	private String antenaId;
	
		
	
	public Cell() {
		super();
		// TODO Auto-generated constructor stub
	}

	

		
	




	public Cell(Long id, String operator, String country, double longitude, double latitude, String mnc, String mcc,
			String lac, String cell, int rssi, String antenaId) {
		super();
		this.id = id;
		this.operator = operator;
		this.country = country;
		this.longitude = longitude;
		this.latitude = latitude;
		this.mnc = mnc;
		this.mcc = mcc;
		this.lac = lac;
		this.cell = cell;
		this.rssi = rssi;
		this.antenaId = antenaId;
	}



	public Long getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public String getOperator() {
		return operator;
	}

	@JsonProperty("operators")
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCountry() {
		return country;
	}

	@JsonProperty("country")
	public void setCountry(String country) {
		this.country = country;
	}

	public double getLongitude() {
		return longitude;
	}

	@JsonProperty("lon")
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	@JsonProperty("lat")
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getMnc() {
		return mnc;
	}

	@JsonProperty("mcc")
	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getMcc() {
		return mcc;
	}

	@JsonProperty("mnc")
	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getLac() {
		return lac;
	}

	@JsonProperty("lac")
	public void setLac(String lac) {
		this.lac = lac;
	}

	public String getCell() {
		return cell;
	}

	@JsonProperty("cell")
	public void setCell(String cell) {
		this.cell = cell;
	}

	public int getRssi() {
		return rssi;
	}

	@JsonProperty("rssi")
	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	@JsonProperty("antenna")
	public String getAntenaId() {
		return antenaId;
	}
	
	public void setAntenaId(String antenaId) {
		this.antenaId = antenaId;
	}
	
}
