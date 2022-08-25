package StructureAnalyze;

import java.util.ArrayList;

public class Token {
	
	private String name = "";	//token名稱
	private String type = "";	//這個token屬於什麼
	private String dataType = "";	//這個token的型態
	private int level;	//token在第幾層
	private int line;	//token在第幾行
	private int parentIndex = 0; //父類別名稱Token的位置
	private boolean functionFlag = false; //?? by hongzhan
	private ArrayList<ArrayList<String>> statisticalSimilarity = new ArrayList<ArrayList<String>>();	//統計性相似度
	private ArrayList<String> formationSimilarity = new ArrayList<String>();	//結構性相似度
	private ArrayList<Integer> functionList = new ArrayList<Integer>();		//class內包含的函數
	private ArrayList<Integer> parameterList = new ArrayList<Integer>();	//function內包含的參數
	private ArrayList<Integer> variableList = new ArrayList<Integer>();		//function內包含的變數
	
	//set parameter
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
    
    public void setDataType(String dataType)
    {
    	this.dataType = dataType;
    }
	
	public void setLevel(int level)
    {
    	this.level = level;
    }
    
    public void setLine(int line)
    {
    	this.line = line;
    }
    
    public void setFunctionFlag(boolean functionFlag)
    {
    	this.functionFlag = true;
    }
	//get parameter
	public String getName()
    {
    	return this.name;
    }
	
	public String getType()
    {
    	return this.type;
    }
	
	public String getDataType()
    {
    	return this.dataType;
    }
	
	public int getLevel()
    {
    	return this.level;
    }
    
    public int getLine()
    {
    	return this.line;
    }
    
    public boolean getFunctionFlag()
    {
    	return this.functionFlag;
    }
    
    public void setStatisticalSimilarity( String statistical , int level, int index)
	{
    	ArrayList<String> tmp = new ArrayList<String>();
    	tmp.add(statistical);
    	tmp.add(Integer.toString(level));
    	tmp.add(Integer.toString(index));
		statisticalSimilarity.add(tmp);
	}
	
	public ArrayList<ArrayList<String>> getstatisticalSimilarity()
	{
		return this.statisticalSimilarity;
	}
	
	public void setFormationSimilarity(String formation)
	{
		formationSimilarity.add(formation);
	}
	
	public ArrayList<String> getFormationSimilarity()
	{
		return this.formationSimilarity;
	}

	public void setParameterList(int index)
	{
		parameterList.add(index);
	}
	
	public ArrayList<Integer> getParameterList()
	{
		return this.parameterList;
	}

	public void setVariableList(int index)
	{
		variableList.add(index);
	}
	
	public ArrayList<Integer> getVariableList()
	{
		return this.variableList;
	}
	
	public void setFunctionList(int index)
	{
		functionList.add(index);
	}
	
	public ArrayList<Integer> getFunctionList()
	{
		return this.functionList;
	}
	
	public void setParentIndex(int parentIndex)
	{
		this.parentIndex = parentIndex;
	}
	
	public int getParentIndex()
	{
		return this.parentIndex;
	}
}
