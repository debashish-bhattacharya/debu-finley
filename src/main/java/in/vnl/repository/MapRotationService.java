package in.vnl.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.vnl.model.MapRotation;
import in.vnl.model.Users;
	
@Service
public class MapRotationService {
	
	@Autowired
	private MapRotationRepository mrr;
	
	@Autowired
	private UsersRepository usersRepository;
	
	public void saveAndUpdateRotation(String userName,int angle)
	{
		Users user=this.usersRepository.findByUserByUserName(userName);
			
		if(user!=null)
		{
			MapRotation mapRotation=this.mrr.findByUserId(user.getUserId());
			if(mapRotation!=null)
			{
				mapRotation.setAngle(angle);
			}
			else 
			{
				mapRotation=new MapRotation(user.getUserId(), angle);
			}
				mrr.save(mapRotation);
		}
	}
	
	public int getCurrentAngleFromDatabase(String userName) {
		Users user=this.usersRepository.findByUserByUserName(userName);
		if(user!=null)
		{
			MapRotation mapRotation=this.mrr.findByUserId(user.getUserId());
			return mapRotation.getAngle();
		}
		return 0;
	}
}
