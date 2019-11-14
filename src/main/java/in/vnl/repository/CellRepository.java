package in.vnl.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.vnl.model.Cell;

@Repository
public interface CellRepository extends CrudRepository<Cell,Long>
{
	//@Query(value = "SELECT * FROM EVENTS WHERE event_date >= ?1 and event_date <= ?2 order by event_date asc", nativeQuery = true)
	//List<Event> findEventsOnGivenDate(Date startDate,Date endDate);
	
	@Override
	List<Cell> findAll();
}
