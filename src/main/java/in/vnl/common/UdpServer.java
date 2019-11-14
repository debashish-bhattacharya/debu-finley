package in.vnl.common;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import in.vnl.controller.InventoryController;
import in.vnl.repository.AduService;

public class UdpServer extends Thread
{
	public static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
	
	@Autowired
	private AduService as;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];
    
    
    
    public UdpServer(int port) 
    {
        try 
        {
        	socket = new DatagramSocket(port);
		} 
        catch (Exception e) 
        {
			logger.info("Exception createing instance of  UdpServer : "+e.getMessage());
		}
    }
    
    public UdpServer() 
    {
        
    }
 
    public void run() {
        running = true;
 
        while (running) 
        {
            try {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				
				String received = new String(buf, 0, packet.getLength());
				PacketParser pp = new PacketParser(); 
				
				pp.packet = received;
				pp.ip = address.getHostAddress();
				
				applicationContext.getAutowireCapableBeanFactory().autowireBean(pp);
				pp.start();
				
				byte[] responseBuffer = "1".getBytes();
				DatagramPacket responsePacket = new DatagramPacket(responseBuffer, responseBuffer.length, address, port);
				socket.send(responsePacket);
				
				
			} catch (Exception e) {
				logger.info("Exception Udpserver : "+e.getMessage());
			}
        }
        socket.close();
    }
}
