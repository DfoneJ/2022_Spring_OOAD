package VariableAnalyze;

import java.util.ArrayList;

import StructureAnalyze.Token;

public class VariableAnalyzer {
	/*
	 * 設定統計相似度
	 */
	public void setStatisticalSimilarity(ArrayList<Token> tokenArrayList)
	{
		//System.out.println(tokenArrayList.size());
		
		for( int i = 0 ; i < tokenArrayList.size() ; i++ )
		{

		//System.out.println("t:"+tokenArrayList.get(i).getParameterList());
		//System.out.println("p:"+tokenArrayList.get(i).getVariableList());
			if(!tokenArrayList.get(i).getParameterList().isEmpty() || !tokenArrayList.get(i).getVariableList().isEmpty())
			{
				for(int j = 0 ;j < tokenArrayList.get(i).getParameterList().size(); j++)
				{
					int k = 0;
					int variableIndex = tokenArrayList.get(i).getParameterList().get(j);
					do{			
						if(tokenArrayList.get(variableIndex).getName().equals(tokenArrayList.get(k).getName())
							&&	variableIndex != k)
						{
						
							if(j+1 < tokenArrayList.size() && (tokenArrayList.get(k+1).getName().equals("=") || tokenArrayList.get(k+1).getName().equals("+=")
									|| tokenArrayList.get(k+1).getName().equals("-=")  || tokenArrayList.get(k+1).getName().equals("*=")
									|| tokenArrayList.get(k+1).getName().equals("/=")))
									{
						//				System.out.println(tokenArrayList.get(variableIndex));
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("assign" , tokenArrayList.get(k).getLevel(), k);
									}
									else if(k+1 < tokenArrayList.size() && tokenArrayList.get(k+1).getName().equals("++"))
									{
									
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("inc" , tokenArrayList.get(k).getLevel(), k);
									}
									else if(k+1 < tokenArrayList.size() && tokenArrayList.get(k+1).getName().equals("--"))
									{
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("inc" , tokenArrayList.get(k).getLevel(), k);
									}
									else if(k+1 < tokenArrayList.size() && tokenArrayList.get(k+1).getName().equals("--"))
									{
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("dec" , tokenArrayList.get(k).getLevel(), k);
									}
						}
						k++;
					}while(k < tokenArrayList.size());
				}
				
				for(int j = 0 ;j < tokenArrayList.get(i).getVariableList().size(); j++)
				{
					int k = 0;
					int variableIndex = tokenArrayList.get(i).getVariableList().get(j);
					
					do{
					//	System.out.println("var:" + tokenArrayList.get(variableIndex).getName());
					//	System.out.println("var2:" + tokenArrayList.get(k).getName());
						if(tokenArrayList.get(variableIndex).getName().equals(tokenArrayList.get(k).getName())
							&&	variableIndex != k)
						{
							if(j+1 < tokenArrayList.size() && (tokenArrayList.get(k+1).getName().equals("=") || tokenArrayList.get(k+1).getName().equals("+=")
									|| tokenArrayList.get(k+1).getName().equals("-=")  || tokenArrayList.get(k+1).getName().equals("*=")
									|| tokenArrayList.get(k+1).getName().equals("/=")))
									{
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("assign" , tokenArrayList.get(k).getLevel(), k);
									}
									else if(k+1 < tokenArrayList.size() && tokenArrayList.get(k+1).getName().equals("++"))
									{
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("inc" , tokenArrayList.get(k).getLevel(), k);
									}
									else if(k+1 < tokenArrayList.size() && tokenArrayList.get(k+1).getName().equals("--"))
									{
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("inc" , tokenArrayList.get(k).getLevel(), k);
									}
									else if(k+1 < tokenArrayList.size() && tokenArrayList.get(k+1).getName().equals("--"))
									{
										tokenArrayList.get(variableIndex).setStatisticalSimilarity("dec" , tokenArrayList.get(k).getLevel(),k);
									}
						}
						k++;
					}while(k < tokenArrayList.size());
				}
			}
		}
	}
	/*
	 * 設定結構相似度
	 */
	public void setFormationSimilarity(ArrayList<Token> tokenArrayList)
	{
		for(int index = 0; index < tokenArrayList.size(); index++)	
		{
			if(!tokenArrayList.get(index).getParameterList().isEmpty() || !tokenArrayList.get(index).getVariableList().isEmpty())	//有ParameterList或VariableList
			{
				for(int i = 0 ;i < tokenArrayList.get(index).getVariableList().size(); i++)	//把List裡的變數都跑過
				{
					int deadLine = 0;
					//System.out.println("variable:" + tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getName());
					for(int j = 0 ; j < tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getstatisticalSimilarity().size() ; j++)
					{
						int variableIndex = Integer.parseInt(tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getstatisticalSimilarity().get(j).get(2));
						//int deadLine = tokenArrayList.get(index).getVariableList().get(i);
						int line = tokenArrayList.get(variableIndex).getLine();
						int level = 0;
						int currentLevel = 10;
						int flag = 0;
						String tmp = "";
						do{	//計算自己的結構
							if(level == 1)
								flag = 1;
							while(tokenArrayList.get(variableIndex).getLine() == line)
							{
								variableIndex--;
							}
							level = tokenArrayList.get(variableIndex+1).getLevel();
							//System.out.println(level);
							line = tokenArrayList.get(variableIndex).getLine();
							//System.out.print(" ");
				
							if(currentLevel > level )
								currentLevel = level;
							//System.out.print(tokenArrayList.get(variableIndex+1).getName());
							if(tokenArrayList.get(variableIndex+1).getName().equals("for") || tokenArrayList.get(variableIndex+1).getName().equals("while") || tokenArrayList.get(variableIndex+1).getName().equals("do"))
							{
								tmp = tmp + "w(";
							}
							else if(tokenArrayList.get(variableIndex+1).getName().equals("if"))
							{
								tmp = tmp + "i(";
							}
							else if(tokenArrayList.get(variableIndex+1).getName().equals("else if"))
							{
								tmp = tmp + "ii(";
							}
							else if(tokenArrayList.get(variableIndex+1).getName().equals("else"))
							{
								tmp = tmp + "e(";
							}
						}while(level >= 1 && flag == 0 && currentLevel >= level/*&& deadLine < variableIndex*/);
						tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).setFormationSimilarity(tmp);
					//	System.out.println("tmp" + tmp);
						deadLine = Integer.parseInt(tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getstatisticalSimilarity().get(j).get(2));
					}

				}
			}
		}		
	}

	public double calculateStatisticalSimilarity(ArrayList<Token> tokenArrayList, ArrayList<Token> tokenArrayList2)
	{
		int match = 0;
		int match2 = 0;
		ArrayList<Integer> similarityList = new ArrayList<Integer>();
		ArrayList<Integer> similarityList2 = new ArrayList<Integer>();
		double sum = 0;
		double sum2 = 0;
		
		for(int i = 0; i < tokenArrayList.size()-1; i++)
		{			
			if(!tokenArrayList.get(i).getstatisticalSimilarity().isEmpty())
			{
				
				for( int j = 0; j < tokenArrayList.get(i).getstatisticalSimilarity().size(); j++)
				{
					for(int k = 0; k < tokenArrayList2.size(); k++)
					{
						if(!tokenArrayList2.get(k).getstatisticalSimilarity().isEmpty())
						{
							//System.out.println( tokenArrayList2.get(k).getstatisticalSimilarity().size());
							for( int l = 0; l < tokenArrayList2.get(k).getstatisticalSimilarity().size(); l++)
							{
							//	System.out.println(tokenArrayList.get(i).getstatisticalSimilarity());
								//System.out.println(tokenArrayList2.get(k).getstatisticalSimilarity().get(l));
								if(this.calculateSimilarity2(tokenArrayList,tokenArrayList2, i, j, k, l) == 1)
								{				
									match = 1;
								}
								else if(match != 1)
								{
									match = 0;
								}
							}
						}
					}
					if(match == 1)
						similarityList.add(1);	
					else
						similarityList.add(0);			
				}
			}
		}
		for(int i = 0; i < similarityList.size(); i++)
		{	//System.out.println(similarityList.get(i));
			sum = sum + similarityList.get(i);
		}
		/*
		 * formation
		 */
		for(int i = 0; i < tokenArrayList.size()-1; i++)
		{
			if(!tokenArrayList.get(i).getFormationSimilarity().isEmpty())
			{
				for( int j = 0; j < tokenArrayList.get(i).getFormationSimilarity().size(); j++)
				{
					for(int k = 0; k < tokenArrayList2.size(); k++)
					{
						if(!tokenArrayList2.get(k).getFormationSimilarity().isEmpty())
						{
							for( int l = 0; l < tokenArrayList2.get(k).getFormationSimilarity().size(); l++)
							{
								//System.out.println(tokenArrayList.get(i).getFormationSimilarity().get(j));
								//System.out.println(tokenArrayList2.get(k).getFormationSimilarity().get(l));
								if(this.calculateSimilarity(tokenArrayList,tokenArrayList2, i, j, k, l) == 1)
								{
									match2 = 1;
								}
								else if(match2 != 1)
									match2 = 0;
							}
						}
					}
					if(match2 == 1)
						similarityList2.add(1);	
					else
						similarityList2.add(0);			
				}
			}
		}
		for(int i = 0; i < similarityList2.size(); i++)
		{	//System.out.println(similarityList2.get(i));
			sum2 = sum2 + similarityList2.get(i);
		}
		
		if(similarityList.size() == 0 && similarityList2.size() == 0)
			return 0;
		else if(similarityList.size() == 0)
			return sum2/(double)similarityList2.size();
		else if(similarityList2.size() == 0)
			return sum2/(double)similarityList.size();
		else
			return (sum/(double)similarityList.size()+sum2/(double)similarityList2.size())/2;
	}
	/*
	 * 計算結構相似度
	 */
	public double calculateSimilarity(ArrayList<Token> tokenArrayList, ArrayList<Token> tokenArrayList2, int index,  int k, int index2,  int l)
	{
		//double similarity = 0;
		int match = 0;
		if(tokenArrayList.get(index).getFormationSimilarity().get(k).equals(tokenArrayList2.get(index2).getFormationSimilarity().get(l)))
			match++;
			
				
				
		return match;
	}
	
	public int calculateSimilarity2(ArrayList<Token> tokenArrayList, ArrayList<Token> tokenArrayList2, int index,  int k, int index2,  int l)
	{
		//double similarity = 0;
		int match = 0;
	
		if(tokenArrayList.get(index).getstatisticalSimilarity().get(k).get(0).equals(tokenArrayList2.get(index2).getstatisticalSimilarity().get(l).get(0))
		   && tokenArrayList.get(index).getstatisticalSimilarity().get(k).get(1).equals(tokenArrayList2.get(index2).getstatisticalSimilarity().get(l).get(1)))
			match++;
		
		return match;
	}
}
