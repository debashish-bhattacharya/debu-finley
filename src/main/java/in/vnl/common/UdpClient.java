package in.vnl.common;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.vnl.controller.InventoryController;

public class UdpClient 
{
	
	public static final Logger logger = LoggerFactory.getLogger(InventoryController.class);
	private DatagramSocket socket;
    private InetAddress address;
    private int port = 4445;
 
    private byte[] buf;
 
    public UdpClient() 
    {
    	logger.info("Inside UdpClient Cunstructor");
    	try 
        {
			socket = new DatagramSocket();
			address = InetAddress.getByName("localhost");
			this.port = port;
		} 
        catch (Exception e) {
        	logger.info("Error while createing data garam socket");
		}
    	logger.info("Exiting UdpClient Cunstructor");
    }
    
    
    
    public UdpClient(String ipAddress,int port) 
    {
    	logger.info("Inside UdpClient Cunstructor");
    	logger.info("IP : "+ipAddress+", Port : "+port);
    	try 
        {
			this.socket = new DatagramSocket();
			
			String[] ipStr=ipAddress.split("\\.");
			 byte[] bb = new byte[4]; 
			 bb[0]=(byte)Integer.parseInt(ipStr[0]);
			 bb[1]=(byte)Integer.parseInt(ipStr[1]);
			 bb[2]=(byte)Integer.parseInt(ipStr[2]);
			 bb[3]=(byte)Integer.parseInt(ipStr[3]);
			  
			 this.address = InetAddress.getByAddress(bb);
			
			
			this.port = port;
		} 
        catch (Exception e) {
        	logger.info("Error while createing data garam socket");
		}
    	logger.info("Exiting UdpClient Cunstructor");
    }
 
    public String sendEcho(String msg) 
    {
    	logger.info("Inside sendEcho Method");
    	logger.info("Message : "+msg);
    	String received = "fail";
    	try 
    	{
			buf = msg.getBytes();
			DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
			socket.send(packet);
			packet = new DatagramPacket(buf, buf.length);
			socket.setSoTimeout(10000);
			socket.receive(packet);
			received = new String(packet.getData(), 0, packet.getLength());
		} 
    	catch (Exception e) 
    	{
    		logger.info("Exception while sendig the data");
		}
        return received;
    }
 
    public void close() 
    {
        socket.close();
    }
}
