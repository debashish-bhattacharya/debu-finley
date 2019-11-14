package in.vnl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import in.vnl.model.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users,Long>
{
	
	@Query("select u from Users u where user_name =:username and password =:password")
    Users findByUserNameAndPassword(@Param("username") String username,@Param("password") String password);
	
	
	@Query("select u from Users u where user_name =:username")
    Users findByUserByUserName(@Param("username") String username);
	
}
