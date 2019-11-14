package in.vnl.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.vnl.model.MapRotation;

@Repository
public interface MapRotationRepository extends CrudRepository<MapRotation,Long>{

	MapRotation findByUserId(@Param("userId") Long userId );

}
