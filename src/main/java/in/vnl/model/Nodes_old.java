package in.vnl.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.fasterxml.jackson.annotation.JsonIgnore;

@EntityScan
@JsonIgnoreProperties(ignoreUnknown = true)
public class Nodes_old {

	private static HashMap<String,String> nodes;

	
	public Nodes_old(HashMap<String, String> nodes) {
		super();
		this.nodes = nodes;
	}

	public Nodes_old()
	{
		
	}
	
	public HashMap<String, String> getNodes() {
		return nodes;
	}

	public void setNodes(HashMap<String, String> nodes) {
		this.nodes = nodes;
	}	
}
