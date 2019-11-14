package in.vnl.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.Cue;
import in.vnl.model.Event;

@Service
public class CueService 
{
	
	@Autowired
	private CueRepository cr;
	
	public void saveCue(Cue cue) 
	{
		cr.save(cue);
	}
	
	public ArrayList<Cue> getAll() {
		Iterator<Cue> itr=cr.findAll().iterator();
		ArrayList<Cue> cueList=new ArrayList<Cue>();
		while(itr.hasNext()) {
			cueList.add(itr.next());
		}
		return cueList;
	}
	
	public List<Cue> getCueForCurrentDay()
	{
		return cr.getCueData(new Date());
	}
	
	public void saveCues(ArrayList<Cue> cues) 
	{
		cr.saveAll(cues);
	}
	
	public void turncate() 
	{
		cr.deleteAll();
	}
	
}
