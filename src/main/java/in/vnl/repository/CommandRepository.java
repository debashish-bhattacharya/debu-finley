package in.vnl.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.vnl.model.Command;
import in.vnl.model.Event;

@Repository
public interface CommandRepository extends CrudRepository<Command,Long>
{
	
	
	@Query(value = "select * from commands where node_id = ?1 and cmd_tag =?2", nativeQuery = true)
	Command getCommand(Long nodeId,String command);
	
}
