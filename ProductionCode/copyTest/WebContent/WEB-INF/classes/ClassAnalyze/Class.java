package ClassAnalyze;

import java.util.ArrayList;

/*�s�����O����T*/
public class Class 
{
	private String name; //���O���W��
	private String parentName; //���O���~�Ӫ������O�W��
	private String inheritanceLevel; //���O���~�Ӫ��h��
	private ArrayList<Method> methodlist = new ArrayList<Method>();; //���O���Ҧ���k
	private ArrayList<Attribute> attributelist = new ArrayList<Attribute>();; //���O���Ҧ��ݩ�

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