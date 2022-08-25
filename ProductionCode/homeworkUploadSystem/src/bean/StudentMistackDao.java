package bean;

public class StudentMistackDao {
	private String stdID;
	private String hwID;
	private int type0;
	private int type1;
	private int type2;
	private int type3;
	private int type4;
	private int type5;
	private int type6;
	
	
	
	public StudentMistackDao(String stdID, String hwID, int type0, int type1, int type2, int type3, int type4,
			int type5, int type6) {
		super();
		this.stdID = stdID;
		this.hwID = hwID;
		this.type0 = type0;
		this.type1 = type1;
		this.type2 = type2;
		this.type3 = type3;
		this.type4 = type4;
		this.type5 = type5;
		this.type6 = type6;
	}
	
	public void count(String type) {
		if(type.equals("0"))
			type0 += 1;
		else if(type.equals("1"))
			type1 += 1;
		else if(type.equals("2"))
			type2 += 1;
		else if(type.equals("3"))
			type3 += 1;
		else if(type.equals("4"))
			type4 += 1;
		else if(type.equals("5"))
			type5 += 1;
		else if(type.equals("6"))
			type6 += 1;
	}
	
	public void marge(StudentMistackDao smd) {
		type0 += smd.getType0();
		type1 += smd.getType1();
		type2 += smd.getType2();
		type3 += smd.getType3();
		type4 += smd.getType4();
		type5 += smd.getType5();
		type6 += smd.getType6();
	}
	
	public int getTotal() {
		return type0 + type1 + type2 + type3 + type4 + type5 + type6; 
	}
	
	
	public String getStdID() {
		return stdID;
	}
	public void setStdID(String stdID) {
		this.stdID = stdID;
	}
	public String getHwID() {
		return hwID;
	}
	public void setHwID(String hwID) {
		this.hwID = hwID;
	}
	public int getType0() {
		return type0;
	}
	public void setType0(int type0) {
		this.type0 = type0;
	}
	public int getType1() {
		return type1;
	}
	public void setType1(int type1) {
		this.type1 = type1;
	}
	public int getType2() {
		return type2;
	}
	public void setType2(int type2) {
		this.type2 = type2;
	}
	public int getType3() {
		return type3;
	}
	public void setType3(int type3) {
		this.type3 = type3;
	}
	public int getType4() {
		return type4;
	}
	public void setType4(int type4) {
		this.type4 = type4;
	}
	public int getType5() {
		return type5;
	}
	public void setType5(int type5) {
		this.type5 = type5;
	}
	public int getType6() {
		return type6;
	}
	public void setType6(int type6) {
		this.type6 = type6;
	}
	
	
	
}