package in.vnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.vnl.model.Aduack;
import in.vnl.model.Event;
import in.vnl.model.Nodes;
import in.vnl.model.Users;

@Repository
public interface AduRepository extends CrudRepository<Aduack,Long>
{
	
	@Query(value = "SELECT * FROM EVENTS WHERE event_date >= ?1 and event_date <= ?2 order by event_date asc", nativeQuery = true)
	List<Event> findEventsOnGivenDate(Date startDate,Date endDate);
	
}
