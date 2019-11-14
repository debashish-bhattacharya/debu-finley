package in.vnl.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.repository.AduService;
import in.vnl.repository.Bms;

@Service
public class PacketParser extends Thread
{

	@Autowired
	private AduService as;
	
	@Autowired
	private Bms bs;
	
	@Autowired
	private Common com;
	
	String ip = null;
	String packet = null;
	
	public PacketParser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void parse() 
	{
		
		String type = Common.nodes.get(this.ip);
		switch(type) 
		{
			case "BMS":
				bs.handlePacket(this.ip, packet);
			break;
			case "ADU":
				as.saveAduAck(packet);
			break;
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		parse();
	}
	
}
