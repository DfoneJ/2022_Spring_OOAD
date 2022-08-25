package StructureAnalyze;

import java.lang.Character;
import java.util.ArrayList;


public class AlgebraicExpression {
	
	
	private String fP = "";
	private String DCSTreeExpression = "";
	private ArrayList<String> fPArrayList = new ArrayList<String>();
	private ArrayList<String> DCSTreeExpressionArrayList = new ArrayList<String>();
	
	/*
	 * 砞﹚计ボΑ
	 */
	public void setAlgebraicExpression( ArrayList<Token> tokenArrayList)
	{
	    int index = 0, k = 0, line = -1;
	    fP = "";
	    DCSTreeExpression = "";
	    
	    while( index < tokenArrayList.size())
	    {
	        if(tokenArrayList.get(index).getName().equals("if"))
	        {
	        	//System.out.print("if" + "( " + tokenArrayList.get(index).getLevel() + "," + tokenArrayList.get(index).getLine() + " )");
	            line = tokenArrayList.get(index).getLine();
            	//setExpression(tokenArrayList, expressionArrayList, k, "F");
	            k++; 
	            fP = fP + "f";
	            DCSTreeExpression = DCSTreeExpression + "F";
	        }
	        else if(tokenArrayList.get(index).getName().equals("else if") ||
	        		tokenArrayList.get(index).getName().equals("else"))
	        {
	        	String tmp = "";
	        	//System.out.print("if" + "( " + tokenArrayList.get(index).getLevel() + "," + tokenArrayList.get(index).getLine() + " )");
	        	line = tokenArrayList.get(index).getLine();
            	//setExpression(tokenArrayList, expressionArrayList, k, "F");
	            k++; 
	            tmp = fP.substring(0, fP.length()-1) + "+f";
	            fP = tmp;
	            DCSTreeExpression = DCSTreeExpression + "+F";
	        }
	        else if(tokenArrayList.get(index).getName().equals("for"))
	        {
	        	//System.out.print("w" + "( " + tokenArrayList.get(index).getLevel() + "," + tokenArrayList.get(index).getLine() + " )");
	        	line = tokenArrayList.get(index).getLine();
            	//setExpression(tokenArrayList, expressionArrayList, k, "W");
	            k++; 
	            fP = fP + "w";
	            DCSTreeExpression = DCSTreeExpression + "W";
	        }
	        else if(tokenArrayList.get(index).getName().equals("{"))
	        {
	        	line = tokenArrayList.get(index).getLine();
            	//setExpression(tokenArrayList, expressionArrayList, k, "(");
	            k++; 
	            fP = fP + "(";
	            DCSTreeExpression = DCSTreeExpression + "(";
	        }
	        else if(tokenArrayList.get(index).getName().equals("}"))
	        {
	        	line = tokenArrayList.get(index).getLine();
            	//setExpression(tokenArrayList, expressionArrayList, k, ")");
	            k++; 
	            if(fP.charAt(fP.length()-1) == '/')
	        	{
	            	String tmp = "";
	            	String DCStmp = "";
	            	if( index+2 < tokenArrayList.size())
	            	{
	            		tmp = fP.substring(0, fP.length()-1) + ")/";
	        			fP = tmp;
	        			DCStmp = DCSTreeExpression.substring(0, DCSTreeExpression.length()-1) + ")c";
		        		DCSTreeExpression = DCStmp;
	            	}
	            	else
	            	{
		        		tmp = fP.substring(0, fP.length()-1) + ")";
		        		fP = tmp;
		        		DCStmp = DCSTreeExpression.substring(0, DCSTreeExpression.length()-1) + ")";
		        		DCSTreeExpression = DCStmp;
	            	}
	        	}	
	            else
	            {
	            	fP = fP + ")";
	            	DCSTreeExpression = DCSTreeExpression + ")";
	            }
	        }
	        else
	        {
	            if(tokenArrayList.get(index).getLine() != line)
	            {
	            	//System.out.print("b" + "( " + tokenArrayList.get(index).getLevel() + "," + tokenArrayList.get(index).getLine() + " )");
	            	line = tokenArrayList.get(index).getLine();
	            	//setExpression(tokenArrayList, expressionArrayList, k, "B");
	                k++;
	                fP = fP + "b/";
	                DCSTreeExpression = DCSTreeExpression + "Bc";
	            } 
	        } 
	        index++;
	    }
	    
	    DCSTreeExpressionArrayList.add(fP);
	    fPArrayList.add(DCSTreeExpression);
		  
	    //System.out.println(fPArrayList.size());
	}
	/*
	 * 计ボΑ
	 */
	
	public void printExpression()
	{
	//	System.out.println("F(p)计ボΑ");
	//	System.out.println(this.fP);
	//	System.out.println("DCS攫挡篶");
	  //  System.out.println(this.DCSTreeExpression);
	}	
	/*
	 * 琌疭才腹
	 */
	public boolean ispunct(char ch)
	{
		Character[] ary = {'(' , ')' , '{' , '}' , '[' , ']' , ',' , '.' , '"' , ';' , '=' , '>' , '<' , '+' , '-' , '*' , '/' , '#'};
		
		for( int i = 0 ; i < ary.length ; i++)
			if(ary[i].charValue() == ch)
				return true;
		
		return false;
	}
	/*
	 * 眔计ボΑ
	 */
	public ArrayList<String> getDCSTreeExpressionArrayList()
	{
		return DCSTreeExpressionArrayList;
	}
	/*
	 * 眔ㄧ计ボΑ
	 */
	public ArrayList<String> getFPArrayList()
	{
		return fPArrayList;
	}
	/*
	 * 砞﹚计ㄧ计
	 */
	public void setExpression(ArrayList<String> expressionArrayList)
	{
		/*Algebraic expression = new Algebraic();
        expressionArrayList.add(expression);
        expressionArrayList.get(index).setLevel(tokenArrayList.get(index).getLevel());
    	expressionArrayList.get(index).setLine(tokenArrayList.get(index).getLine());
    	expressionArrayList.get(index).setAlgebraic(algebraic.toLowerCase());
    	expressionArrayList.get(index).setDCSTreealgebraic(algebraic);*/
		expressionArrayList.add(this.fP);
	}
	
}
