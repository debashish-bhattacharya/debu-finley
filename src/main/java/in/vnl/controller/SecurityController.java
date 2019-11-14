package in.vnl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.vnl.model.Users;
import in.vnl.repository.UsersService;

@RestController
@RequestMapping("/security")
public class SecurityController 
{
	public static final Logger logger = LoggerFactory.getLogger(SecurityController.class);
	
	/*
	 * All denpendency will be injected here
	 * */
	@Autowired
	private UsersService userServiceObj;
	
	@RequestMapping(value="/authenticate", method=RequestMethod.POST)
	public int authenticateUser(Users user,HttpServletRequest request)
	{
	
		HttpSession session = request.getSession();
		
		Users userData = userServiceObj.autenticateUser(user);
		
		if(userData == null) 
		{
			return 0;
		}
		else 
		{
			session.setAttribute("userName", userData.getUserName());
			return 1;
		}
	}
	
	/*
	@RequestMapping("/test/{user}")
	public String authenticateUser(@PathVariable("user") String id) 
	{
		return id;
	} 
	 @RequestMapping(value="/adduser" ,method=RequestMethod.POST)
	public Users createNewUser(Users userModal) 
	{
		return userServiceObj.createNewUser(userModal);
	}
	
	@RequestMapping(value="/findall" ,method=RequestMethod.POST)
	public HashMap<Users> getAllUsers(Users userModal) 
	{
		return userServiceObj.getAllUsers();
	}
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
    public String showLoginPage(){
        return "login";
    }*/
	
	
}
