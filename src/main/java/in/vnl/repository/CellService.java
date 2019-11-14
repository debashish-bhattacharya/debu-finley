package in.vnl.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.Cell;

@Service
public class CellService 
{
	
	@Autowired
	private CellRepository cr;
	
	public void saveCells(Cell[] cells) 
	{
		cr.deleteAll();		
		List<Cell> cellsList = new ArrayList<Cell>();
		
		for(int i=0;i<cells.length;i++) 
		{
			cellsList.add(cells[i]);
		}
		
		cr.saveAll(cellsList);
	}
	
	
	public List<Cell> getCells()
	{
		return cr.findAll();
	}
}
