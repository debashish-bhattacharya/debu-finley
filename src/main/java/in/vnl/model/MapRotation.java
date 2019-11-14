package in.vnl.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="map_rotation")
public class MapRotation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;

//	@Column(name="user_id")
	private Long userId;
//	@Column(name="angle")
	private int angle;
	
	public MapRotation() {}

	public MapRotation(Long id, Long userId, int angle) {
		super();
		this.id = id;
		this.userId = userId;
		this.angle = angle;
	}



	public MapRotation( Long userId, int angle) {
		super();
	
	    this.userId = userId;
		this.angle = angle;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getUserId() {
		return userId;
	}



	public void setUserId(Long userId) {
		this.userId = userId;
	}



	public int getAngle() {
		return angle;
	}



	public void setAngle(int angle) {
		this.angle = angle;
	}


}
