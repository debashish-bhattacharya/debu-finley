package in.vnl.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.Alarm;
import in.vnl.model.BmsStatus;



@Service
public class Bms {


	@Autowired
	private AlarmRepository ar;
	
	@Autowired
	private BmsStatusService bmss;
	
	public Bms() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void handlePacket(String ip,String packet) 
	{
		String[] data = packet.split(",");
		switch(Integer.parseInt(data[1])) 
		{
			case 1:
				
			break;
			
			case 2:
			break;
			
			case 3:
			break;
			
			case 4:
			break;
			
			case 5:
				saveStatus(ip,data);
			break;
			case 6:
				saveAlarm(ip,data);
			break;
		}
	}
	
	public void saveAlarm(String ip,String[] data) 
	{
		Alarm am = new Alarm();
		Date dd = new Date(Long.parseLong(data[10]));
		am.setIp(ip);
		am.setAcknowledged(false);
		am.setComponen_type(data[3]);
		am.setComponent_id(Integer.parseInt(data[2]));
		am.setEventDesctiption(data[9]);
		am.setEventId(Integer.parseInt(data[6]));
		am.setEventType(data[7]);
		am.setGenerationTime(dd);
		am.setManagedobject_type(data[5]);
		am.setMangedoject_id(Integer.parseInt(data[4]));
		am.setSeverity(Integer.parseInt(data[8]));
		ar.save(am);
	}
	
	
	public void saveStatus(String ip,String[] data) 
	{
		BmsStatus bs = new BmsStatus();
		
		//Date dd = new Date(Long.parseLong(data[10]));
		
		bs.setIp(ip);
		bs.setAlarmword(Integer.parseInt(data[18]));
		bs.setBcv1(Integer.parseInt(data[0]));
		bs.setBcv2(Integer.parseInt(data[1]));
		bs.setBcv3(Integer.parseInt(data[2]));
		bs.setBcv4(Integer.parseInt(data[3]));
		bs.setBcv5(Integer.parseInt(data[4]));
		bs.setBcv6(Integer.parseInt(data[5]));
		bs.setBcv7(Integer.parseInt(data[6]));
		bs.setBcv8(Integer.parseInt(data[7]));
		bs.setBcv9(Integer.parseInt(data[8]));
		bs.setBcv10(Integer.parseInt(data[9]));
		bs.setBcv11(Integer.parseInt(data[10]));
		bs.setBcv12(Integer.parseInt(data[11]));
		bs.setBcv13(Integer.parseInt(data[12]));
		bs.setBcv14(Integer.parseInt(data[13]));
		bs.setBtemp(Integer.parseInt(data[17]));
		bs.setBtv(Integer.parseInt(data[14]));
		bs.setSoc(Integer.parseInt(data[16]));
		bs.setTbc(Integer.parseInt(data[15]));
		bmss.save(bs);
	}

}
