package in.vnl.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.BmsStatus;
import in.vnl.model.Event;
import in.vnl.model.Users;

@Service
public class BmsStatusService 
{
	@Autowired
	private BmsStatusRepository br;
	
	public void save(BmsStatus bs) 
	{
		br.save(bs);
	}
}
