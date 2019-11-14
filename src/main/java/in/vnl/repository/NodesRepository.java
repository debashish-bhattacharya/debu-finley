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

@Repository
public interface NodesRepository extends CrudRepository<Nodes,Long>
{
	
	@Query("select u from Nodes u where node_name =:nodetype")
	Nodes findNodeIpByNodeType(@Param("nodetype") String nodetype);
	
	@Override
	List<Nodes> findAll();
	 
	@Modifying
	@Query("update Nodes node set status = 0,node_ip = :ip where node_id = :id")
	int updateIp(@Param("id") Long id,@Param("ip") String ip);
	 
	@Modifying
	@Transactional
	@Query(value= "update nodes set status = ?1  where node_name = ?2", nativeQuery = true)
	int updateStatus(@Param("id") String status,@Param("nodetype") String nodetype);
	 
	@Query("select u from Nodes u where node_ip =:ip")
	Nodes findNodeIpByIp(@Param("ip") String ip);
	
	@Query(value= "SELECT * from nodes order by node_id asc", nativeQuery = true)
	List<Nodes> findNodesInSortOrderOfId();
	 
	 
}
