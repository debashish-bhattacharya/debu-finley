package in.vnl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="commands")
public class Command 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long cmdId;
	private int nodeId;
	private String cmdTag;
	private String cmd;
	
	
	
	
	public Command() {
	
	}

	public Command(Long cmdId, int nodeId, String cmdTag, String cmd) {
		super();
		this.cmdId = cmdId;
		this.nodeId = nodeId;
		this.cmdTag = cmdTag;
		this.cmd = cmd;
	}

	public Long getCmdId() {
		return cmdId;
	}

	public void setCmdId(Long cmdId) {
		this.cmdId = cmdId;
	}

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getCmdTag() {
		return cmdTag;
	}

	public void setCmdTag(String cmdTag) {
		this.cmdTag = cmdTag;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) 
	{
		this.cmd = cmd;
	}
}
