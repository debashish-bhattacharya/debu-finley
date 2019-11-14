package in.vnl.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.Event;
import in.vnl.model.Users;

@Service
public class EventService {
	
	@Autowired
	private EventRepository er;
	
	public void saveEvents(ArrayList<Event> events) 
	{
		er.saveAll(events);
	}
	
	public List<Event> findEventsOnGiveDate(Date startDate,Date endDate) 
	{
		return er.findEventsOnGivenDate(startDate,endDate);
	}
	
	public List<Event> findAlarmsOnGiveDate(Date startDate,Date endDate) 
	{
		return er.findAlarmsOnGivenDate(startDate,endDate);
	}
	
	
	
	public int acknowledgeAlarm(String eventId) 
	{
		return er.acknowledgeAlarm(Integer.parseInt(eventId));
	}
	
	
	public void deleteEventForNode(String nodeType) 
	{
		er.deleteEventsForNode(nodeType);
	}
	
	
	/*public List<Event> findEventsOnGiveDate(String eventdate) 
	{
		return er.findEventsOnGivenDate(eventdate);
	}
	
	public List<Event> findAlarmsOnGiveDate(String eventdate) 
	{
		return er.findAlarmsOnGivenDate(eventdate);
	}*/
	
	
	
	public Event saveEvent(Event ev) 
	{
				
		return er.save(ev);
	}
	
	public void trucateEvets() 
	{
		er.deleteAll();
	}
	
}
