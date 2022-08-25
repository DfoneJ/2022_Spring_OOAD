package Parser;

import java.util.ArrayList;
import StructureAnalyze.Token;

public class Parser {
	
	public void parserCodeToToken(String str, ArrayList<Token> tokenArrayList)
	{
		int i = -1 , flag = 0, index = 0, commentFlag = 0;
		int quotes = 0;	//�������޸�
	    String tmp = "";
	    
	    while(index < str.length())
	    {

	    	if(commentFlag == 5)	//�p�ڬO//�}�Y�������J��'\n'����
	    	{
	    		while(index < str.length() && str.charAt(index) != '\n')
	    			index++;
	    		commentFlag = 0;
	    	}
	    	else if(commentFlag == 6)	//�P�_/*�}�Y����������
	    	{
	    		while(index < str.length())
	    		{
	    			if(str.charAt(index) == '*')
	    			{
	    				if( index+1 < str.length() && str.charAt(index+1) == '/')
	    				{
	    					index++;
	    					break;
	    				}
	    			}
	    			index++;
	    		}
	    		commentFlag = 0;
	    	}
	        else if(str.charAt(index) == '/')	//�P�_����
	        {
	        	if(str.charAt(index+1) == '/')
	        	{
	        		index++;
	        		commentFlag = 5;
	        	}
	        	if(str.charAt(index+1) == '*')
	        	{
	        		index++;
	        		commentFlag = 6;
	        	}
	        }
	        else if(str.charAt(index) == '"')   //�P�_""
	        {
	        	String tmpStr = "";
	        	Token token = new Token();
	        	tmpStr = tmpStr + str.charAt(index);
	        	quotes++;
	        	while(index+1 < str.length() && quotes != 0)
	    		{
	        		index++;
	        		tmpStr = tmpStr + str.charAt(index);
	    			if(str.charAt(index) == '"')
	    					break;
	    		}
	        	tokenArrayList.add(token);
	        	i++;
		//		System.out.println("tmpStr:"+tmpStr);
	        	tokenArrayList.get(i).setName(tmpStr);
                tokenArrayList.get(i).setType("Letter");
	        }
	    	else if(Character.isLetter(str.charAt(index)) || str.charAt(index) == '_' || str.charAt(index) == '.')	//�P�_��r
	        {
	        	Token token = new Token();
	            tmp = tmp + str.charAt(index);
	        	if( flag != 1 )
	            { 
	                flag = 1;	// flag = 1 ��ܬO�򱵦b��r�᭱���Ʀr 
	                i++;
	            	tokenArrayList.add(token);
	            }
	            tokenArrayList.get(i).setName(tmp);
	            tokenArrayList.get(i).setType("Letter");
	        }
	    	else if(str.charAt(index) == '*' && Character.isLetter(str.charAt(index+1)))
	    	{
	    		Token token = new Token();
	            tmp = tmp + str.charAt(index);
	        	if( flag != 1 )
	            { 
	                flag = 1;	// flag = 1 ��ܬO�򱵦b��r�᭱���Ʀr 
	                i++;
	            	tokenArrayList.add(token);
	            }
	            tokenArrayList.get(i).setName(tmp);
	            tokenArrayList.get(i).setType("Letter");
	    	}
	    	else if( str.charAt(index) == '[' )
	    	{
	            tmp = tmp + str.charAt(index);
	            tokenArrayList.get(i).setName(tmp);
	            tokenArrayList.get(i).setType("Letter");
	            flag = 0;
	            tmp = "";
	    	}
	        else if(Character.isDigit(str.charAt(index)))	//�P�_�Ʀr
	        {
	            if(flag == 1)
	            { 
	            	tmp = tmp + str.charAt(index);
	            	tokenArrayList.get(i).setName(tmp);
	            }
	            else if(flag == 2) 
	            {
	            	tmp = tmp + str.charAt(index);
	            	tokenArrayList.get(i).setName(tmp);
	            	tokenArrayList.get(i).setType("Digit");
	            } 
	            else
	            { 
	                flag = 2;	// flag = 2 ��ܬO�򱵦b�Ʀr�᭱���Ʀr 
	                i++; 
	                Token token = new Token();
	                tokenArrayList.add(token);
	                tmp = tmp + str.charAt(index);
	                tokenArrayList.get(i).setName(tmp);
	                tokenArrayList.get(i).setType("Digit");
	                //tmp = "";
	            } 
	        }
	        else if(Character.isWhitespace(str.charAt(index))) //�P�_�ť�
	        {
	            if(flag != 3)
	            { 
	                flag = 3;	// flag = 3 ��ܫe�@�ӬO�ť� 
	                tmp = "";
	            } 
	            else
	            { 
	                flag = 0;
	                tmp = "";
	            } 
	        }
	        else if(ispunct(str.charAt(index)))	//�P�_�Ÿ�
	        {
	            flag = 4;	// flag = 4�N��Ÿ�
	            tmp = "";
	            i++; 
	            Token token = new Token();
                tokenArrayList.add(token);
                tmp = tmp + str.charAt(index);
                tokenArrayList.get(i).setName(tmp);
                tokenArrayList.get(i).setType("Punctuation");
	            tmp = "";
	        }
	        
	        index++;
	    }
	}
	/*
	 * �]�w�O�d�r 
	 */
	public void setReservedWordType(ArrayList<Token> tokenArrayList)
	{
	    int i, j; 
	    String includeStr = "#include";	    
	    i = tokenArrayList.size();

	    for( j = 0 ; j < i ; j++)
	    {
	    	if(tokenArrayList.get(j).getName().equals("else"))	//�Nelse if�X�֦��@��token
	        {
	           if(tokenArrayList.get(j+1).getName().equals("if"))
	           { 
	        	   tokenArrayList.get(j).setName("else if"); 
	        	   tokenArrayList.get(j).setType("ReservedWord");
	        	   tokenArrayList.remove(j+1);
	        	   i--;
	           }
	        }
	    	else if(tokenArrayList.get(j).getName().equals("#"))	//�Ninclude�X���@��token
	        {
	    		if(tokenArrayList.get(j+1).getName().equals("include"))
	    		{
	    			j++;
	    			tokenArrayList.remove(j);
	    			i--;

	    			while(!(tokenArrayList.get(j).getName().equals(">")/*add by hongzhan*/||tokenArrayList.get(j).getName().contains("\"")/*add by hongzhan*/))
	    			{
	    				includeStr += tokenArrayList.get(j).getName();
		    			tokenArrayList.remove(j);				
	    				i--;
		        	}

	    			includeStr += tokenArrayList.get(j).getName();
	    			tokenArrayList.get(j-1).setName(includeStr); 
	    			tokenArrayList.get(j-1).setType("include");
	    			tokenArrayList.remove(j);
	    			i--;
				    j--;
				    includeStr = "#include";
	         	}
	    		else if(tokenArrayList.get(j+1).getName().equals("define"))	//�Ndefine�X���@��token�A
	    		{ 
	    			String tmp = "";
	    			tmp = "#define" + " " +  tokenArrayList.get(j+2).getName() + " " + tokenArrayList.get(j+3).getName();
	    			tokenArrayList.get(j).setName(tmp); 
	    			tokenArrayList.get(j).setType("define");
	    			tokenArrayList.remove(j+1);
	    			tokenArrayList.remove(j+1);
	    			tokenArrayList.remove(j+1);
	        		i = i - 3;
	        	//	j = j + 3;//? by hongzhan
	    		}//�ȭ�#define PI 3.14159���اΦ��A#difine max(x,y) ((x)>(y)?(x):(y))��#define _UNICODE �|�� by hongzhan
	        }
	         else if(tokenArrayList.get(j).getName().equals("+"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("+"))
		            { 
	        		 	tokenArrayList.get(j).setName("++"); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	        	 else if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName("+="); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("-"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("-"))
		            { 
	        		 	tokenArrayList.get(j).setName("--"); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	        	 else if(tokenArrayList.get(j+1).getName().equals(">"))
		            { 
		            	tokenArrayList.get(j).setName("->"); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	        	 else if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName("-="); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("/"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName("/="); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("*"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName("*="); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("="))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName("=="); //"*=" to "==" by hongzhan
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals(">"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName(">="); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	        	 else if(tokenArrayList.get(j+1).getName().equals(">"))
		            { 
		            	tokenArrayList.get(j).setName(">>"); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("<"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName("<="); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	        	 else if(tokenArrayList.get(j+1).getName().equals("<"))
		            { 
		            	tokenArrayList.get(j).setName("<<"); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("!"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("="))
		            { 
		            	tokenArrayList.get(j).setName("!="); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("&"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("&"))
		            { 
		            	tokenArrayList.get(j).setName("&&"); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         else if(tokenArrayList.get(j).getName().equals("|"))
	         {
	        	 if(tokenArrayList.get(j+1).getName().equals("|"))
		            { 
		            	tokenArrayList.get(j).setName("||"); 
		            	tokenArrayList.get(j).setType("Punctuation");
		            	tokenArrayList.remove(j+1);
		                i--;
		            }
	         }
	         
	    } 
	    
	    for( j = 0 ; j < tokenArrayList.size() ; j++)	//�N�U���]��ReservedWord
	    { 
	         if(isReservedWord(tokenArrayList.get(j).getName())) 
	        	 tokenArrayList.get(j).setType("ReservedWord");
	         else if(isDataType(tokenArrayList.get(j).getName()))
	        	 tokenArrayList.get(j).setType("DataType");
	    }
	}
	
	/*
	 * �]�wtoken�����
	 */
	public void setLine(ArrayList<Token> tokenArrayList)
	{
		int index = 0;
		int line = 0;	//�]�wline�ܼ�
		int parenthesis = 0;	//���p�A�����ƶq
		int endFlag = 0;
		
		while(index < tokenArrayList.size())
	    {
			tokenArrayList.get(index).setLine(line);
			
			/*
			 * for�����p�A�����t�~�B�z
			 */
			if(tokenArrayList.get(index).getName().equals("for") ||
			   tokenArrayList.get(index).getName().equals("if")  ||
			   tokenArrayList.get(index).getName().equals("else if") ||
			   tokenArrayList.get(index).getName().equals("while"))
			{
				tokenArrayList.get(index).setLine(line);
				//�קK�����򴫦檺�����d�V
				//for,if,else if,while��()������r�Һ�P�@�� by hongzhan
				do{
					index++;
					if(tokenArrayList.get(index).getName().equals("("))
						parenthesis++;
					else if(tokenArrayList.get(index).getName().equals(")"))
						parenthesis--;
					tokenArrayList.get(index).setLine(line);					
				}while(parenthesis != 0);
				//������while();�����p�A�D�o�ر��p�h�U�@��token�Q�����U�@��
				if(tokenArrayList.get(index+1).getName().equals(";"))
				{
					tokenArrayList.get(++index).setLine(line);
					endFlag = 1;
				}
				else
					line++;
				if(tokenArrayList.get(index+1).getName().equals("{"))
				{
					index++;
					tokenArrayList.get(index).setLine(line);
				}
				//�ΨӸɨ����S���j�A�����{��
				//�����S���j�A�����{���e��[�W�j�A���A����ӵ{���榡�Τ@ by hongzhan
				else if(!tokenArrayList.get(index+1).getName().equals("{") && !tokenArrayList.get(index+1).getName().equals(";") && endFlag == 0)
				{
					index++;
					Token leftBracket = new Token();
					leftBracket.setName("{");
					leftBracket.setType("Punctuation");
					tokenArrayList.add(index, leftBracket);
					tokenArrayList.get(index).setLine(line++);
					//bracket++;
					do{
						index++;
						tokenArrayList.get(index).setLine(line);
					}while(!tokenArrayList.get(index).getName().equals(";"));
					//this.setLine(tokenArrayList, index, bracket);
					
					index++;
					Token rightBracket = new Token();
					rightBracket.setName("}");
					rightBracket.setType("Punctuation");
					tokenArrayList.add(index, rightBracket);
					tokenArrayList.get(index).setLine(++line);
					//bracket--;
				}
				line++;
				endFlag = 0;
			}
			/*
			 * �]��else�M�W�C���c���P�ҥH�ݭn�W�ߥX��
			 */
			//if(){},else{}���c�W�t�F() by hongzhan
			else if(tokenArrayList.get(index).getName().equals("else"))
			{
				line++;
				if(tokenArrayList.get(index+1).getName().equals("{"))
				{
					index++;
					tokenArrayList.get(index).setLine(line);
				}
				//�ΨӸɨ����S���j�A�����{��
				//�����S���j�A�����{���e��[�W�j�A���A����ӵ{���榡�Τ@ by hongzhan
				else if(!tokenArrayList.get(index+1).getName().equals("{"))
				{
					index++;
					Token leftBracket = new Token();
					leftBracket.setName("{");
					leftBracket.setType("Punctuation");
					tokenArrayList.add(index, leftBracket);
					tokenArrayList.get(index).setLine(line++);
					
					do{
						index++;
						tokenArrayList.get(index).setLine(line);
					}while(!tokenArrayList.get(index).getName().equals(";"));
					
					index++;
					Token rightBracket = new Token();
					rightBracket.setName("}");
					rightBracket.setType("Punctuation");
					tokenArrayList.add(index, rightBracket);
					tokenArrayList.get(index).setLine(++line);
				}
				line++;
			}
			/*
			 * �򥻡G�J�������ƥ[�@
			 */
			/*
			 * �J��}��Ƥ]�[�@�A����{{1,2},{2,3}} {}; do{}while();�o�T�ر��p�h������
			 * do{}while();�зǮ榡��
			 * do
			 * {
			 * }while();
			 * by hongzhan
			 */
			else if(tokenArrayList.get(index).getName().equals(";") ||
					//(tokenArrayList.get(index).getName().equals("{") && tokenArrayList.get(index-1).getName().equals(")"))||
					(index+1 < tokenArrayList.size() && tokenArrayList.get(index).getName().equals("}") 
					 && !tokenArrayList.get(index+1).getName().equals(",")) 
					 && !tokenArrayList.get(index+1).getName().equals("while")
					 && !tokenArrayList.get(index+1).getName().equals(";"))
				line++;
			else if(tokenArrayList.get(index).getName().equals("{"))
			{
				line++;
				tokenArrayList.get(index).setLine(line++);
			}
			/*
			 * include�Mdefine
			 */
			//����getType�A�ҥH��token�w�bsetReservedWordType method����X����#include<stdio>���˦� by hongzhan
			else if(tokenArrayList.get(index).getType().equals("include") ||
					tokenArrayList.get(index).getType().equals("define"))
					line++;
			
			index++;
	    }
	}
	/*
	 * �]�wtoken���h��
	 */
	//�J��{�[�@�h�A�J��}��@�h by hongzhan
	public void setLevel(ArrayList<Token> tokenArrayList)
	{
		int index = 0;
		int level = 0;
		
		while( index < tokenArrayList.size()) 
	    {
			 if(tokenArrayList.get(index).getName().equals("{"))
			 {
				 tokenArrayList.get(index).setLevel(level);
				 level++;
			 } 
			 else if(tokenArrayList.get(index).getName().equals("}"))
			 {
				 level--;
				 tokenArrayList.get(index).setLevel(level);
			 }
			 else if(tokenArrayList.get(index).getName().equals("if") ||
					 tokenArrayList.get(index).getName().equals("else if") ||
					 tokenArrayList.get(index).getName().equals("for") )
			 {
				 while(tokenArrayList.get(index).getLine() == tokenArrayList.get(index+1).getLine())
				 {
					 tokenArrayList.get(index).setLevel(level);
					 index++;
				 }
				 tokenArrayList.get(index).setLevel(level);
			 }
			 else if(tokenArrayList.get(index).getName().equals("else"))
			 {
				 tokenArrayList.get(index).setLevel(level);
			 }
			 else
				 tokenArrayList.get(index).setLevel(level);
			 
			 index++;
	    }
	}
	/*
	 * �]�w��ƩM�ǻ��Ѽ�
	 */
	public void setFunction(ArrayList<Token> tokenArrayList)
	{	
		for( int index = 0; index < tokenArrayList.size(); index++)
		{
			//�]�w���
			if(isDataType(tokenArrayList.get(index).getName()) /*&& tokenArrayList.get(index).getLevel() == 0*/)
			{
				String dataType = tokenArrayList.get(index).getName();	//�Ȧs���A��String
				int parenthesis = 0;	//�A���X��
				int functionIndex = 0;	
				int flag = 0;
				
				if(tokenArrayList.get(++index).getType().equals("Letter") && tokenArrayList.get(index+1).getName().equals("("))	//�T�w�Ofunction
				{
					tokenArrayList.get(index).setType("function");
					tokenArrayList.get(index).setDataType(dataType);
					flag = 1;
				}
				functionIndex = index;
				
				if(flag == 1)	//���p�Ofunction�h�}�l�O���ǻ��Ѽ�
				{
					//�ΰj��]�ǻ��Ѽ�
					do{	
						index++;
						if(tokenArrayList.get(index).getName().equals("("))
							parenthesis++;
						else if(tokenArrayList.get(index).getName().equals(")"))
							parenthesis--;			
						else if(isDataType(tokenArrayList.get(index).getName()))
						{
							while(!tokenArrayList.get(++index).getType().equals("Letter"));
							tokenArrayList.get(index).setType("parameter");
							tokenArrayList.get(functionIndex).setParameterList(index);
						}
					}while(parenthesis != 0 && index+1 < tokenArrayList.size());
				}
				flag = 0;
			}
			else if(tokenArrayList.get(index).getName().equals("class"))
			{
				tokenArrayList.get(++index).setType("class");
				tokenArrayList.get(++index).setDataType("class");//�o��++�����쪺�Oclass name�᭱���A��? by hongzhan
			}
		}
	}
	/*
	 * �]�w��Ƥ��ܼ�
	 */
	public void setVariable(ArrayList<Token> tokenArrayList)
	{
		for( int index = 0; index < tokenArrayList.size(); index++)
		{
			//�����ܼƤ��|�Q������A�]�b�̤W�h  by hongzhan
			if(isDataType(tokenArrayList.get(index).getName()) && tokenArrayList.get(index).getLevel() != 0)	//�T�w���O�ܼ�
			{
				String dataType = "";
				int vectorFlag = 0;
				//�S�O�w��vector(ex.vector<int> ivector(10);)���w�����B�z by hongzhan
				if(tokenArrayList.get(index).getName().equals("vector"))
				{
					index = index+2;
					dataType = tokenArrayList.get(index).getName();
					index = index+1;
					vectorFlag = 1;
				}
				else
					dataType = tokenArrayList.get(index).getName();	//�Ȧs���A��String
				int line = tokenArrayList.get(index).getLine();
				while(true)
				{	
					if(tokenArrayList.get(++index).getType().equals("Letter") && vectorFlag == 1)
					{
						tokenArrayList.get(index).setDataType(dataType);
						tokenArrayList.get(index).setType("variable");
					}
					else if( tokenArrayList.get(index).getType().equals("Letter") 
						&& ( tokenArrayList.get(index-1).getName().equals(",") || tokenArrayList.get(index-1).getType().equals("DataType")))
					{
						tokenArrayList.get(index).setDataType(dataType);
						tokenArrayList.get(index).setType("variable");
					}
					else if(tokenArrayList.get(index).getName().equals(";"))
						break;
					//����h���X by hongzhan
					if(line != tokenArrayList.get(index).getLine())
						break;
				}
			}
		}
	}
	/*
	 * ��Ѽƥ[��function��
	 */
	public void addVariableInfunction(ArrayList<Token> tokenArrayList)
	{
		for(int index = 0 ; index < tokenArrayList.size() ; index++)
		{
			if(tokenArrayList.get(index).getType().equals("function"))	//����function���Ѽ�
			{
				int functionIndex = index;
				index++;
				while(index < tokenArrayList.size() && !tokenArrayList.get(index).getType().equals("function") && !tokenArrayList.get(index).getType().equals("class"))
				{
					if(tokenArrayList.get(index).getType().equals("variable"))
					{
						tokenArrayList.get(index).setParentIndex(functionIndex);
						tokenArrayList.get(functionIndex).setVariableList(index++);
					}
					else
						index++;
				}
			}
			else if(tokenArrayList.get(index).getType().equals("class"))	//����class����T
			{
				int classIndex = index;
				index += 3;
				//int functionIndex = 0;
				while(tokenArrayList.get(classIndex).getLevel() != tokenArrayList.get(index).getLevel())
				{
					if(tokenArrayList.get(index).getType().equals("function"))
					{
						//System.out.println(tokenArrayList.get(index).getName());
						tokenArrayList.get(index).setVariableList(classIndex);
						tokenArrayList.get(classIndex).setFunctionList(index++);
						
					}
					else if(tokenArrayList.get(index).getType().equals("variable"))
					{
						tokenArrayList.get(classIndex).setVariableList(index++);
					}
					else
						index++;
				}
			}
		}
	}
	/*
	 * ����ܼƦW�ټҲ�
	 */
	public void changeVariableNameInfunction(ArrayList<Token> tokenArrayList)
	{
		int index = 0;
		for(int i = 0; i < tokenArrayList.size()-1; i++)
		{
			if(tokenArrayList.get(i).getType().equals("variable") || tokenArrayList.get(i).getType().equals("parameter"))
			{
				for(int j = i + 1; j < tokenArrayList.size(); j++)
				{
					if(tokenArrayList.get(i).getName().equals(tokenArrayList.get(j).getName()))
					{
						tokenArrayList.get(j).setName("changeName"+index);
					}
				}
				tokenArrayList.get(i).setName("changeName"+index);
				index++;
			}
		}
	}
	/*
	 * �L�X�{��
	 */
	public void printProgram(ArrayList<Token> tokenArrayList)
	{
		for( int index = 0 ; index < tokenArrayList.size() ; index++ )
		{
	    	int lin = tokenArrayList.get(index).getLine();
	    	for(int i = 0 ; i < tokenArrayList.get(index).getLevel() ; i++)
	    		System.out.print("\t");
	    	while(index < tokenArrayList.size() && lin == tokenArrayList.get(index).getLine()){
	    		System.out.print( tokenArrayList.get(index).getName() + " " );
	    		index++;
			}
	    	index--;
	    	System.out.println();
	    }
		/*
		 * print funtion list
		 */
		/*for(int index = 0 ; index < tokenArrayList.size() ; index++)
		{
			if(tokenArrayList.get(index).getType().equals("function"))
			{
				if(tokenArrayList.get(index).getParentIndex() == 0)
				{
					System.out.println();
					System.out.println( "function:" 
										+ tokenArrayList.get(index).getName() + " "
										+ tokenArrayList.get(index).getDataType());
				}
				for(int i = 0 ; i < tokenArrayList.get(index).getParameterList().size() ; i++)
					if(tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getType().equals("parameter"))
					{
						System.out.println( "parameter:" 
											+ tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getName() + " "
											+ tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getDataType());
						for(int j = 0; j < tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getstatisticalSimilarity().size(); j++)
							System.out.println( "parameter:" + tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getstatisticalSimilarity().get(j));
						for(int j = 0; j < tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getFormationSimilarity().size(); j++)
							System.out.println( "FormationSimilarity:" + tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getFormationSimilarity().get(j));
					}					
				for(int i = 0 ; i < tokenArrayList.get(index).getVariableList().size() ; i++)
				{
					System.out.println( "variable:" 
										+ tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getName() + " "
										+ tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getDataType());
					for(int j = 0; j < tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getstatisticalSimilarity().size(); j++)
						System.out.println( "statisticalSimilarity:" + tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getstatisticalSimilarity().get(j));
					for(int j = 0; j < tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getFormationSimilarity().size(); j++)
						System.out.println( "FormationSimilarity:" + tokenArrayList.get(tokenArrayList.get(index).getVariableList().get(i)).getFormationSimilarity().get(j));
				}					
			}
			else if(tokenArrayList.get(index).getType().equals("class"))
			{
				System.out.println();
				System.out.println( "class:" + tokenArrayList.get(index).getName());
				for(int i = 0 ; i < tokenArrayList.get(index).getParameterList().size() ; i++)
				{
					if(tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getType().equals("parameter"))
						System.out.println( "parameter:" 
											+ tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getName() + " "
											+ tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getDataType());
					else
						System.out.println( "variable:" 
											+ tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getName() + " "
											+ tokenArrayList.get(tokenArrayList.get(index).getParameterList().get(i)).getDataType());
				}
				for(int i = 0 ; i < tokenArrayList.get(index).getFunctionList().size() ; i++)
				{
					System.out.println( "function:" 
										+ tokenArrayList.get(tokenArrayList.get(index).getFunctionList().get(i)).getName() + " "
										+ tokenArrayList.get(tokenArrayList.get(index).getFunctionList().get(i)).getDataType());
				}
			}
		}*/
	}
	
	public boolean ispunct(char ch)
	{
		Character[] ary = {'(' , ')' , '{' , '}' , '[' , ']' , ',' , '.' , '"' , ';' , '=' , '>' , '<' , '+' , '-' , '*' , '/' , '#', ':'};
		
		for( int i = 0 ; i < ary.length ; i++)
			if(ary[i].charValue() == ch)
				return true;
		
		return false;
	}
	
	public boolean isDataType(String str)
	{
		String[] StrAry = { "int", "long", "double", "short", "float", "byte", "char", "String", "bool", "void", "vector", "arg"};
		
		for( int i = 0 ; i < StrAry.length ; i++)
			if(StrAry[i].equals(str))
				return true;
		
		return false;
	}
	
	public boolean isReservedWord(String str)
	{
		String[] StrAry = { "for", "while", "do", "if", "eles if", "else" };
		
		for( int i = 0 ; i < StrAry.length ; i++)
			if(StrAry[i].equals(str))
				return true;
		
		return false;
	}
}
