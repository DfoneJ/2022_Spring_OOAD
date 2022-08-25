package ClassAnalyze;

/*存放class內的屬性*/
public class Attribute
{
	private String name; //屬性名稱
	private String datatype; //屬性資料型態
	private String accesslevel; //屬性存取層級
		
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