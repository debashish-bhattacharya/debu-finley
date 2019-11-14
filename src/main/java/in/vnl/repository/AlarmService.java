package in.vnl.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.Aduack;
import in.vnl.model.Alarm;
import in.vnl.model.Event;
import in.vnl.model.Users;

@Service
public class AlarmService 
{
	@Autowired
	private AlarmRepository ar;
	
	public void saveAlarm(Alarm am) 
	{
		ar.save(am);
	}
	
	public int acknowledgeAlarm(boolean ack,Long alarmId) 
	{
		return ar.acknowledgeAlarm(ack,alarmId);
	}
	
}

