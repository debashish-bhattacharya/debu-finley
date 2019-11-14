package in.vnl.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.Aduack;
import in.vnl.model.Event;
import in.vnl.model.Users;

@Service
public class AduService 
{
	
	@Autowired
	private AduRepository ar;
	
	public void saveAduAck(String aduack) 
	{
		Aduack ak = new Aduack();
		ak.setResponse(aduack);
		ar.save(ak);
	}
	
}
