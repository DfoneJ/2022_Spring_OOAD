package ClassAnalyze;

/*�s��class�����ݩ�*/
public class Attribute
{
	private String name; //�ݩʦW��
	private String datatype; //�ݩʸ�ƫ��A
	private String accesslevel; //�ݩʦs���h��
		
	public String getAccessLevel()
	{
		return accesslevel;
	}
	
	public String getDatatype()
	{
		return datatype;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setAccessLevel(String _accesslevel)
	{
		accesslevel = _accesslevel;
	}
	
	public void setDatatype(String _datatype)
	{
		datatype = _datatype;
	}
	
	public void setName(String _name)
	{
		name = _name;
	}
}