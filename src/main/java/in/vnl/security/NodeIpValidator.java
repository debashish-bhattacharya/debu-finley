package in.vnl.security;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.vnl.model.Nodes;
import in.vnl.repository.NodesService;

@Component
public class NodeIpValidator implements ConstraintValidator<ValidNodeIp, HttpServletRequest> 
{
 
	@Autowired
	private NodesService ns;
    
	@Override
    public void initialize(ValidNodeIp constraint) {
    }
 
   /* public boolean isValid(String login, ConstraintValidatorContext context) {
        return login != null && !userRepository.findByLogin(login).isPresent();
    }*/
    
	@Override
	public boolean isValid(HttpServletRequest request, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		String ip = request.getRemoteAddr();
		Nodes node = ns.findNodeByIp(ip);
		
		if(node != null)
			return true;
		else
			return false;
	}
 
}