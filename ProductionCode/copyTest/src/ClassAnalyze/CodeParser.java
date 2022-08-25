package ClassAnalyze;

import java.util.ArrayList;

import Parser.Parser;
import StructureAnalyze.Token;

public class CodeParser 
{
	private String str; //程式碼內容
	private ArrayList<Token> tokenArrayList = new ArrayList<Token>(); //程式碼轉換而得的tokens
	private ArrayList<Class> classArrayList = new ArrayList<Class>(); //tokens轉換而得的所有class資訊
	
	CodeParser(String _str)
	{
		str = _str;
	}
	
	public void execute()
	{
		Parser tokener = new Parser();
		tokener.parserCodeToToken(str,tokenArrayList); //去掉空白、註解，並轉成tokens
		changePointPosition(); //將*放置在其應該放的位置
		parseTokens(); //抽取tokens中class的資訊放置於classArrayList中
		/*
		for(int i=0; i<classArrayList.size();i++)
		{
			System.out.println(classArrayList.get(i).getName());
			System.out.println(classArrayList.get(i).getInheritanceLevel());
			System.out.println(classArrayList.get(i).getParentName());
			for(int j=0;j<classArrayList.get(i).getAttributeList().size();j++)
			{
				System.out.println("Attribute:");
				System.out.println(classArrayList.get(i).getAttributeList().get(j).getName());
				System.out.println(classArrayList.get(i).getAttributeList().get(j).getAccessLevel());
				System.out.println(classArrayList.get(i).getAttributeList().get(j).getDatatype());
			}
			for(int k=0;k<classArrayList.get(i).getMethodList().size();k++)
			{
				System.out.println("function:");
				System.out.println(classArrayList.get(i).getMethodList().get(k).getName());
				System.out.println(classArrayList.get(i).getMethodList().get(k).getAccessLevel());
				System.out.println(classArrayList.get(i).getMethodList().get(k).getisVirtual());
				System.out.println(classArrayList.get(i).getMethodList().get(k).getReturnType());
				for(int m=0;m<classArrayList.get(i).getMethodList().get(k).getParameterList().size();m++)
				{
					System.out.print(classArrayList.get(i).getMethodList().get(k).getParameterList().get(m));					
					System.out.print(",");					
				}
				System.out.println();			
			}
		}
		*/
	}
	
	/*將int *a這種情況改為int* a*/
	private void changePointPosition()
	{
		Parser tokener = new Parser();
		
		for(int i=0;i<tokenArrayList.size();i++)
		{
			while(tokener.isDataType(tokenArrayList.get(i).getName().replace('*', ' ').trim()) && tokenArrayList.get(i+1).getName().startsWith("*"))
			{
				String tmp = tokenArrayList.get(i).getName()+"*";
				tokenArrayList.get(i).setName(tmp);
				tmp = tokenArrayList.get(i+1).getName().substring(1);
				
				if(tmp.length()==0)
				{
					tokenArrayList.remove(i+1);
				}
				else
				{
					tokenArrayList.get(i+1).setName(tmp);					
				}				
			}
		}
	}

	/*將tokens中class的資訊抽取出來*/
	private void parseTokens()
	{			
		for(int i=0;i<tokenArrayList.size();i++)
		{
			if(tokenArrayList.get(i).getName().equals("class"))//遇到關鍵字class則開始parse class的資訊
			{
				i = parseClass(++i);
			}
		}
	}
	
	/*parse Class的資訊*/
	private int parseClass(int i)
	{

		int flag=0; //標記目前在程式碼的第幾層，遇{則+1，}則-1
		String accesslevel="2"; //0→public;1→protected;2→private,因為都不寫會被預設為private,故初始為2
		Class c = new Class();
		Parser tokener = new Parser();
		c.setName(tokenArrayList.get(i).getName());
		i++;
		if(tokenArrayList.get(i).getName().equals(":")) //如果有繼承，則紀錄繼承的資訊
		{
			i++;
			
			while(!(tokenArrayList.get(i).getName().equals("private")||tokenArrayList.get(i).getName().equals("public")||tokenArrayList.get(i).getName().equals("protected")))
			{
				i++;
				
				if(tokenArrayList.get(i).getName().equals("{"))
				{
					i=i-2;
					break;
				}
			}
			
			c.setInheritanceLevel(tokenArrayList.get(i).getName());
			i++;
			c.setParentName(tokenArrayList.get(i).getName());
			i++;
		}
		
		do
		{
			if(tokenArrayList.get(i).getName().equals("{"))
			{
				flag++;
			}
			else if(tokenArrayList.get(i).getName().equals("}"))
			{
				flag--;
			}
			else if(tokenArrayList.get(i).getName().equals("public") && flag == 1)
			{
				accesslevel = "0";
				i++;
			}
			else if(tokenArrayList.get(i).getName().equals("protected") && flag == 1)
			{
				accesslevel = "1";
				i++;
			}
			else if(tokenArrayList.get(i).getName().equals("private") && flag == 1)
			{
				accesslevel = "2";
				i++;
			}
			else if((tokener.isDataType(tokenArrayList.get(i).getName().replace('*', ' ').trim()) || tokener.isDataType(tokenArrayList.get(i).getName().replace('&', ' ').trim())) && flag == 1)//遇到Method或是attribute
			{
				if(tokenArrayList.get(i+2).getName().equals("("))//Method
				{
					i = parseMethod(i,c);
					c.getMethodList().get(c.getMethodList().size()-1).setAccessLevel(accesslevel);
				}
				else //attribute
				{
					i = parseAttribute(i,c);
					c.getAttributeList().get(c.getAttributeList().size()-1).setAccessLevel(accesslevel);
				}
			}

			i++;
		}
		while(flag>0 && i<tokenArrayList.size());
		
		classArrayList.add(c);
		return i;
	}
	
	/*parse Method的資訊*/
	private int parseMethod(int i,Class c)
	{
		Parser tokener = new Parser();
		Method m = new Method();
		
		if(tokenArrayList.get(i-1).getName().equals("virtual"))//紀錄其是否為虛擬函式
		{
			m.setisVirtual(true);
		}
		
		m.setReturnType(tokenArrayList.get(i).getName());
		i++;
		m.setName(tokenArrayList.get(i).getName());
		i+=2;
		
		while(!tokenArrayList.get(i).getName().equals(")"))//紀錄其參數
		{
			if(tokener.isDataType(tokenArrayList.get(i).getName().replace('*', ' ').trim())||tokener.isDataType(tokenArrayList.get(i).getName().replace('&', ' ').trim()))
			{
				m.addParameter(tokenArrayList.get(i).getName());
			}
			i++;
		}
		
		c.addMethod(m);
		return i;
	}
	
	/*parse Attribute的資訊*/
	private int parseAttribute(int i,Class c)
	{
		Attribute a = new Attribute();
		a.setDatatype(tokenArrayList.get(i).getName());
		i++;
		a.setName(tokenArrayList.get(i).getName());
		i++;
		c.addAttribute(a);
		return i;
	}
	
	public ArrayList<Class> getClassArrayList()
	{
		return classArrayList;
	}
}