package in.vnl.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.vnl.model.Nodes;

@Service
public class NodesService 
{
	@Autowired
	private NodesRepository nr;
	
	public Nodes getNodeDetailByNodeType(String nodeType)
	{
		return nr.findNodeIpByNodeType(nodeType);
	}
	
	public Nodes getNodeById(Long id)
	{
		return nr.findById(id).orElse(null);
	}
	
	public List<Nodes> getNodes()
	{
		return nr.findAll();
	}
	
	
	public List<Nodes> getNodesInOrder()
	{
		return nr.findNodesInSortOrderOfId();
	}
	
	@Transactional
	public int updateNodeIp(Nodes node) 
	{
		return nr.updateIp(node.getNodeId(),node.getNodeIp());
	}
	
	@Transactional
	public int updateNodeStatus(String status ,String nodeType) 
	{
		return nr.updateStatus(status,nodeType);
	}
	
	public Nodes findNodeByIp(String ip) 
	{
		return nr.findNodeIpByIp(ip);
	}
}
