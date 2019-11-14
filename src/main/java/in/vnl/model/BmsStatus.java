package in.vnl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Table;

@Entity(name="bmsstatus")
public class BmsStatus 
{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id		;
	private String ip 	;
	private int bcv1 	;
	private int bcv2 	;
	private int bcv3 	;
	private int bcv4 	;
	private int bcv5 	;
	private int bcv6 	;
	private int bcv7 	;
	private int bcv8 	;
	private int bcv9 	;
	private int bcv10 	;
	private int bcv11 	;
	private int bcv12 	;
	private int bcv13 	;
	private int bcv14 	;
	private int btv 	;
	private int tbc 	;
	private int soc 	;
	private int btemp 	;
	private int alarmword;
	
	public BmsStatus() 
	{
		
	}


	public BmsStatus(Long id, String ip, int bcv1, int bcv2, int bcv3, int bcv4, int bcv5, int bcv6, int bcv7, int bcv8,
			int bcv9, int bcv10, int bcv11, int bcv12, int bcv13, int bcv14, int btv, int tbc, int soc, int btemp,
			int alarmword) {
		super();
		this.id = id;
		this.ip = ip;
		this.bcv1 = bcv1;
		this.bcv2 = bcv2;
		this.bcv3 = bcv3;
		this.bcv4 = bcv4;
		this.bcv5 = bcv5;
		this.bcv6 = bcv6;
		this.bcv7 = bcv7;
		this.bcv8 = bcv8;
		this.bcv9 = bcv9;
		this.bcv10 = bcv10;
		this.bcv11 = bcv11;
		this.bcv12 = bcv12;
		this.bcv13 = bcv13;
		this.bcv14 = bcv14;
		this.btv = btv;
		this.tbc = tbc;
		this.soc = soc;
		this.btemp = btemp;
		this.alarmword = alarmword;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public String getIp() {
		return ip;
	}




	public void setIp(String ip) {
		this.ip = ip;
	}




	public int getBcv1() {
		return bcv1;
	}




	public void setBcv1(int bcv1) {
		this.bcv1 = bcv1;
	}




	public int getBcv2() {
		return bcv2;
	}




	public void setBcv2(int bcv2) {
		this.bcv2 = bcv2;
	}




	public int getBcv3() {
		return bcv3;
	}




	public void setBcv3(int bcv3) {
		this.bcv3 = bcv3;
	}




	public int getBcv4() {
		return bcv4;
	}




	public void setBcv4(int bcv4) {
		this.bcv4 = bcv4;
	}




	public int getBcv5() {
		return bcv5;
	}




	public void setBcv5(int bcv5) {
		this.bcv5 = bcv5;
	}




	public int getBcv6() {
		return bcv6;
	}




	public void setBcv6(int bcv6) {
		this.bcv6 = bcv6;
	}




	public int getBcv7() {
		return bcv7;
	}




	public void setBcv7(int bcv7) {
		this.bcv7 = bcv7;
	}




	public int getBcv8() {
		return bcv8;
	}




	public void setBcv8(int bcv8) {
		this.bcv8 = bcv8;
	}




	public int getBcv9() {
		return bcv9;
	}




	public void setBcv9(int bcv9) {
		this.bcv9 = bcv9;
	}




	public int getBcv10() {
		return bcv10;
	}




	public void setBcv10(int bcv10) {
		this.bcv10 = bcv10;
	}




	public int getBcv11() {
		return bcv11;
	}




	public void setBcv11(int bcv11) {
		this.bcv11 = bcv11;
	}




	public int getBcv12() {
		return bcv12;
	}




	public void setBcv12(int bcv12) {
		this.bcv12 = bcv12;
	}




	public int getBcv13() {
		return bcv13;
	}




	public void setBcv13(int bcv13) {
		this.bcv13 = bcv13;
	}




	public int getBcv14() {
		return bcv14;
	}




	public void setBcv14(int bcv14) {
		this.bcv14 = bcv14;
	}




	public int getBtv() {
		return btv;
	}




	public void setBtv(int btv) {
		this.btv = btv;
	}




	public int getTbc() {
		return tbc;
	}




	public void setTbc(int tbc) {
		this.tbc = tbc;
	}




	public int getSoc() {
		return soc;
	}




	public void setSoc(int soc) {
		this.soc = soc;
	}




	public int getBtemp() {
		return btemp;
	}




	public void setBtemp(int btemp) {
		this.btemp = btemp;
	}




	public int getAlarmword() {
		return alarmword;
	}




	public void setAlarmword(int alarmword) {
		this.alarmword = alarmword;
	}
		
}
