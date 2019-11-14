package in.vnl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name="events")
public class Event {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long eventId;
	private Date eventDate;
	private String nodeType;
	private String eventData;
	private String eventType;
	private boolean acknowledged;
	
	public Event() 
	{
		
	}


	public Event(Long eventId, Date eventDate, String nodeType, String eventData,String eventType) {
		super();
		this.eventId = eventId;
		this.eventDate = eventDate;
		this.nodeType = nodeType;
		this.eventData = eventData;
		this.eventType = eventType;
	}
	
	
	


	public Event(Long eventId, Date eventDate, String nodeType, String eventData, String eventType,
			boolean acknowledged) {
		super();
		this.eventId = eventId;
		this.eventDate = eventDate;
		this.nodeType = nodeType;
		this.eventData = eventData;
		this.eventType = eventType;
		this.acknowledged = acknowledged;
	}
	
	
	



	public Long getEventId() {
		return eventId;
	}


	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}


	public Date getEventDate() {
		return eventDate;
	}


	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}


	public String getNodeType() {
		return nodeType;
	}


	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}


	public String getEventData() {
		return eventData;
	}


	public void setEventData(String eventData) {
		this.eventData = eventData;
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
