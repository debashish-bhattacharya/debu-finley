package in.vnl.model;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Temporal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name="cue")
public class Cue 
{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Date reportedTime;
	
	private String reportedBy;
	private String source;
	private String cueId;
	private String detail;
	private String nodeType;
	public Cue(Long id, Date reportedTime, String reportedBy, String source, String detail) {
		super();
		this.id = id;
		this.reportedTime = reportedTime;
		this.reportedBy = reportedBy;
		this.source = source;
		this.detail = detail;
	}
	
	
	
	public Cue(Long id, Date reportedTime, String reportedBy, String source, String cueId, String detail,
			String nodeType) {
		super();
		this.id = id;
		this.reportedTime = reportedTime;
		this.reportedBy = reportedBy;
		this.source = source;
		this.cueId = cueId;
		this.detail = detail;
		this.nodeType = nodeType;
	}



	public Cue() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public Cue(Long id, Date reportedTime, String reportedBy, String source, String cueId, String detail) {
		super();
		this.id = id;
		this.reportedTime = reportedTime;
		this.reportedBy = reportedBy;
		this.source = source;
		this.cueId = cueId;
		this.detail = detail;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public Date getReportedTime() {
		return reportedTime;
	}
	@JsonProperty("DATE")
	public void setReportedTime(Date reportedTime) {
		this.reportedTime = reportedTime;
	}
	public String getReportedBy() {
		return reportedBy;
	}
	@JsonProperty("NODE")
	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}
	public String getSource() {
		return source;
	}
	@JsonProperty("SOURCE")
	public void setSource(String source) {
		this.source = source;
	}
	public String getDetail() {
		return detail;
	}
	@JsonProperty("DETAIL")
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@JsonProperty("CUE_ID")
	public String getCueId() {
		return cueId;
	}

	public void setCueId(String cueId) {
		this.cueId = cueId;
	}
	
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

}
