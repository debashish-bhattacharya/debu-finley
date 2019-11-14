package in.vnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.vnl.model.Cue;

@Repository
public interface CueRepository extends CrudRepository<Cue,Long>
{
	@Query(value = "SELECT * FROM CUE WHERE reported_time <=  ?1 order by reported_time desc limit 500", nativeQuery = true)
	List<Cue> getCueData(Date curretTime);
}
