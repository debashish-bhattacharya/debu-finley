package in.vnl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.vnl.model.BmsConfig;

@Repository
public interface BmsConfigRepository extends CrudRepository<BmsConfig,Long>
{
	
	@Query(value = "SELECT * FROM bmsconfig WHERE ip = ?1 order by id asc", nativeQuery = true)
	List<BmsConfig> getBMSProperties(String config);
	
}
