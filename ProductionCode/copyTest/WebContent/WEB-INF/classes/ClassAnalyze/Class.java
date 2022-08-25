package ClassAnalyze;

import java.util.ArrayList;

/*存放類別的資訊*/
public class Class 
{
	private String name; //類別的名稱
	private String parentName; //類別所繼承的父類別名稱
	private String inheritanceLevel; //類別所繼承的層級
	private ArrayList<Method> methodlist = new ArrayList<Method>();; //類別的所有方法
	private ArrayList<Attribute> attributelist = new ArrayList<Attribute>();; //類別的所有屬性

	public String getName()
	{
		return name;
	}
	
	public String getParentName()
	{
		return parentName;
	}
	
	public String getInheritanceLevel()
	{
		return inheritanceLevel;
	}
	
	public ArrayList<Method> getMethodList()
	{
		return methodlist;
	}
	
	public ArrayList<Attribute> getAttributeList()
	{
		return attributelist;
	}
	
	public void setName(String _name)
	{
		name = _name;
	}
	
	public void setParentName(String _parentName)
	{
		parentName = _parentName;
	}
	
	public void setInheritanceLevel(String _inheritanceLevel)
	{
		inheritanceLevel = _inheritanceLevel;
	}
	
	public void addMethod(Method method)
	{
		methodlist.add(method);
	}
	
	public void addAttribute(Attribute attribute)
	{
		attributelist.add(attribute);
	}
}