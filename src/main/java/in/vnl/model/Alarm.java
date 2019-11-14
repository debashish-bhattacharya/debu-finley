package in.vnl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name="alarms")
public class Alarm 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String ip;
	private int 	component_id;	
	private int 	mangedoject_id;	
	private int 	eventId;	
	private int 	severity;
	private Date 	generationTime;
	private String 	eventDesctiption;
	private String 	componen_type;
	private String 	managedobject_type;
	private String 	eventType;	
	private boolean acknowledged;
	
	
	public Alarm() 
	{
		
	}


	public Alarm(Long id, String ip, int component_id, int mangedoject_id, int eventId, int severity,
			Date generationTime, String eventDesctiption, String componen_type, String managedobject_type,
			String eventType, boolean acknowledged) 
	{
		super();
		this.id = id;
		this.ip = ip;
		this.component_id = component_id;
		this.mangedoject_id = mangedoject_id;
		this.eventId = eventId;
		this.severity = severity;
		this.generationTime = generationTime;
		this.eventDesctiption = eventDesctiption;
		this.componen_type = componen_type;
		this.managedobject_type = managedobject_type;
		this.eventType = eventType;
		this.acknowledged = acknowledged;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public int getComponent_id() {
		return component_id;
	}


	public void setComponent_id(int component_id) {
		this.component_id = component_id;
	}


	public int getMangedoject_id() {
		return mangedoject_id;
	}


	public void setMangedoject_id(int mangedoject_id) {
		this.mangedoject_id = mangedoject_id;
	}


	public int getEventId() {
		return eventId;
	}


	public void setEventId(int eventId) {
		this.eventId = eventId;
	}


	public int getSeverity() {
		return severity;
	}


	public void setSeverity(int severity) {
		this.severity = severity;
	}


	public Date getGenerationTime() {
		return generationTime;
	}


	public void setGenerationTime(Date generationTime) {
		this.generationTime = generationTime;
	}


	public String getEventDesctiption() {
		return eventDesctiption;
	}


	public void setEventDesctiption(String eventDesctiption) {
		this.eventDesctiption = eventDesctiption;
	}


	public String getComponen_type() {
		return componen_type;
	}


	public void setComponen_type(String componen_type) {
		this.componen_type = componen_type;
	}


	public String getManagedobject_type() {
		return managedobject_type;
	}


	public void setManagedobject_type(String managedobject_type) {
		this.managedobject_type = managedobject_type;
	}


	public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}


	public boolean isAcknowledged() {
		return acknowledged;
	}


	public void setAcknowledged(boolean acknowledged) {
		this.acknowledged = acknowledged;
	}
	
	
}
