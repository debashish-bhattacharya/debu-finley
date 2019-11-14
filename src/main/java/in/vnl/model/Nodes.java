package in.vnl.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Nodes 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long    nodeId;
	private String  nodeName;
	private String  nodeIp;
	private String  status;
	private String  url;
	private String  statusUpdateType;
	private boolean active;
	private String  displayName;
	private String  nodeIcon;
	private String  loginPath;
	
	public Nodes()
	{
		
	}	
	
	public Nodes(Long nodeId, String nodeName, String nodeIp, String status,String url,String statusUpdateType) 
	{
		super();
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeIp = nodeIp;
		this.status = status;
		this.url=url;
		this.statusUpdateType=statusUpdateType;
	}
	
	public Nodes(Long nodeId, String nodeName, String nodeIp, String status, String url, String statusUpdateType,boolean active, String displayName, String nodeIcon) 
	{
		super();
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeIp = nodeIp;
		this.status = status;
		this.url = url;
		this.statusUpdateType = statusUpdateType;
		this.active = active;
		this.displayName = displayName;
		this.nodeIcon = nodeIcon;
	}
	
	
	public Nodes(Long nodeId, String nodeName, String nodeIp, String status, String url, String statusUpdateType,boolean active, String displayName, String nodeIcon,String loginPath) 
	{
		super();
		this.nodeId = nodeId;
		this.nodeName = nodeName;
		this.nodeIp = nodeIp;
		this.status = status;
		this.url = url;
		this.statusUpdateType = statusUpdateType;
		this.active = active;
		this.displayName = displayName;
		this.nodeIcon = nodeIcon;
		this.loginPath = loginPath;
	}

	public Long getNodeId() 
	{
		return nodeId;
	}

	public void setNodeId(Long nodeId) 
	{
		this.nodeId = nodeId;
	}

	public String getNodeName() 
	{
		return nodeName;
	}

	public void setNodeName(String nodeName) 
	{
		this.nodeName = nodeName;
	}
	
	public String getNodeIp() 
	{
		return nodeIp;
	}

	public void setNodeIp(String nodeIp) 
	{
		this.nodeIp = nodeIp;
	}
	
	public String getStatus() 
	{
		return status;
	}

	public void setStatus(String status) 
	{
		this.status = status;
	}

	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}

	public String getStatusUpdateType() 
	{
		return statusUpdateType;
	}

	public void setStatusUpdateType(String statusUpdateType) 
	{
		this.statusUpdateType = statusUpdateType;
	}

	public boolean getActive() 
	{
		return active;
	}

	public void setActive(boolean active) 
	{
		this.active = active;
	}

	public String getDisplayName() 
	{
		return displayName;
	}

	public void setDisplayName(String displayName) 
	{
		this.displayName = displayName;
	}

	public String getNodeIcon() 
	{
		return nodeIcon;
	}

	public void setNodeIcon(String nodeIcon) 
	{
		this.nodeIcon = nodeIcon;
	}
	
	public String getLoginPath() 
	{
		return loginPath;
	}

	public void setLoginPath(String loginPath) 
	{
		this.loginPath = loginPath;
	}
	
}
