package in.vnl.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import in.vnl.model.Nodes;
import in.vnl.model.Users;
import in.vnl.repository.NodesService;

@Controller
public class ViewController {

	@Autowired
	private NodesService ns;
	
	@Autowired
	private Environment env;
	
	//@RequestMapping(value="/login")
	public String getLoginPage(Model model) 
	{
		
		return "login";
	}
	
	
	 @RequestMapping(value = "/login", method = RequestMethod.GET)
	    public String loginPage(@RequestParam(value = "error", required = false) String error,
	                            @RequestParam(value = "logout", required = false) String logout,
	                            Model model) {
	        String errorMessge = null;
	        if(error != null) {
	            errorMessge = "Username or Password is incorrect !!";
	        }
	        if(logout != null) {
	            errorMessge = "You have been successfully logged out !!";
	        }
	        model.addAttribute("errorMessge", errorMessge);
	        model.addAttribute("version", env.getProperty("app.version"));
	        return "login";
	    }
	
	

		
	    @RequestMapping(value="/")
	    public String approot () {
	        
	        return "redirect:/dashboard";
	    }
	 
	@RolesAllowed({ "ROLE_ADMIN", "ROLE_USER" })
	@RequestMapping(value="/dashboard")
	public String getDashboardPage(Model model) 
	{
		List<Nodes> nodes = ns.getNodesInOrder();
		model.addAttribute("nodes",nodes);
		model.addAttribute("loadGooleMap", env.getProperty("app.load.goolemap"));
		model.addAttribute("mapserverIp", env.getProperty("app.mapserver.ip"));
		return "dashboard";
	}
	
	@RolesAllowed({ "ROLE_ADMIN"})
	@RequestMapping(value="/operation")
	public String getOperationPage(Model model) 
	{
		List<Nodes> nodes = ns.getNodesInOrder();
		model.addAttribute("nodes",nodes);
		return "operations";
	}
	
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){   
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }
    
    @RequestMapping(value="/403", method = RequestMethod.GET)
    public String accessDenied()
    {
    	return "403Page";
    }
	
    /*@MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello";
    }*/
}
