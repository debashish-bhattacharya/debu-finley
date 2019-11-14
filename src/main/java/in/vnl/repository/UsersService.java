package in.vnl.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.vnl.model.Users;

@Service
public class UsersService implements UserDetailsService {
	
	@Autowired
	private UsersRepository ur;
	
	public Users autenticateUser(Users user)
	{
		return ur.findByUserNameAndPassword(user.getUserName(), user.getPassword());
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Users user = ur.findByUserByUserName(userName);
		// TODO Auto-generated method stub
		
		 if (user == null) {
	            System.out.println("User not found! " + userName);
	            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
	        }
		 List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		 GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		 grantList.add(authority);
		 
		return (UserDetails) new User(userName, user.getPassword(), grantList);
	}
}
