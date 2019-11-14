package in.vnl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.Command;

@Service
public class CommandService  
{
	
	@Autowired
	private CommandRepository cr;
	
	public Command getCommand(Long nodeId,String command) 
	{
		return cr.getCommand(nodeId, command);
	}	
}
