package in.vnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.vnl.model.Event;
import in.vnl.model.Nodes;
import in.vnl.model.Users;

@Repository
public interface EventRepository extends CrudRepository<Event,Long>
{
	
	@Query(value = "SELECT * FROM EVENTS WHERE event_date >= ?1 and event_date <= ?2 order by event_date desc limit 500", nativeQuery = true)
	//@Query(value = "SELECT * FROM EVENTS" , nativeQuery = true)
	List<Event> findEventsOnGivenDate(Date startDate,Date endDate);
	
	@Query(value = "SELECT * FROM EVENTS WHERE event_type in( '7','4') and event_date >= ?1 and event_date <= ?2 order by event_date desc limit 500", nativeQuery = true)
	List<Event> findAlarmsOnGivenDate(Date eventDate,Date endDate);
	
	
	@Modifying
	@Transactional
	@Query(value = "delete from EVENTS where node_type = ?1", nativeQuery = true)
	void deleteEventsForNode(String nodeType);
	
	
	
	@Modifying
	@Transactional
	@Query(value = "update EVENTS set Acknowledged = true where event_id = ?1", nativeQuery = true)
	int acknowledgeAlarm(int eventId);
	
	
	
	
}
