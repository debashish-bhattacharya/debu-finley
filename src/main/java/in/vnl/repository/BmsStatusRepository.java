package in.vnl.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.vnl.model.Alarm;
import in.vnl.model.BmsStatus;
import in.vnl.model.Event;
import in.vnl.model.Nodes;
import in.vnl.model.Users;

@Repository
public interface BmsStatusRepository extends CrudRepository<BmsStatus,Long>
{
	
}
