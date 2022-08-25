package ClassAnalyze;

import java.util.ArrayList;

/*�s���k����T*/
public class Method 
{
	private boolean isVirtual; //�O�_�������禡�A�_�h��null
	private String name; //��k���W��
	private String returntype; //�^�ǫ��A
	private String accesslevel; //�i�Q�s�����h��
	private ArrayList<String> parameterlist = new ArrayList<String>(); //�ǤJ���Ѽ�
	
	public Method()
	{
		isVirtual = false;
	}
	
	public boolean getisVirtual()
	{
		return isVirtual;
	}
	
	public String getAccessLevel()
	{
		return accesslevel;
	}
	
	public String getReturnType()
	{
		return returntype;
	}
	
	public String getName()
	{
		return name;
	}
	
	public ArrayList<String> getParameterList()
	{
		return parameterlist;
	}	
	
	public void setAccessLevel(String _accesslevel)
	{
		accesslevel = _accesslevel;
	}
	
	public void setReturnType(String _returntype)
	{
		returntype = _returntype;
	}
	
	public void setName(String _name)
	{
		name = _name;
	}
	
	public void setisVirtual(boolean _isVirtual)
	{
		isVirtual = _isVirtual;
	}
	
	public void addParameter(String parameter)
	{
		parameterlist.add(parameter);
	}
	
	public boolean equals(Method comparison)
	{
		boolean same = true;
		
		if(!name.equals(comparison.getName()))
		{
			same = false;
		}
		
		if(!returntype.equals(comparison.getReturnType()))
		{
			same = false;
		}
		
		if(parameterlist.size()!=comparison.getParameterList().size())
		{
			same = false;
		}
		else
		{
			for(int i=0;i<parameterlist.size();i++)
			{
				if(!parameterlist.get(i).equals(comparison.getParameterList().get(i)))
				{
					same = false;
				}
			}
		}
		
		return same;
	}
}