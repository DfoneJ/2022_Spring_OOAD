package TextAnalyze;

import java.lang.String;
import java.util.*;
import Parser.Parser;
import StructureAnalyze.Token;
import Parser.*;
import StructureAnalyze.*;
import VariableAnalyze.*;



public class Winnow {
	/*
	 * text analyze
	 */
	public double textAnalyze(String File1, String File2, int kGram)
	{
		Winnow winnow = new Winnow();
		Parser parser = new Parser();
		String tmp = "";
		String tmp2 = "";
		ArrayList<Token> tokenArrayList = new ArrayList<Token>();
		ArrayList<Token> tokenArrayList2 = new ArrayList<Token>();
		ArrayList<Integer> hashTable = new ArrayList<Integer>();
		ArrayList<Integer> hashTable2 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> window = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> window2 = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> fingerPrint = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> fingerPrint2 = new ArrayList<ArrayList<Integer>>();
		
		parser.parserCodeToToken(File1,tokenArrayList);
		parser.parserCodeToToken(File2,tokenArrayList2);
		for(int i = 0; i < tokenArrayList.size(); i++)
			tmp = tmp + tokenArrayList.get(i).getName();
		for(int i = 0; i < tokenArrayList2.size(); i++)
			tmp2 = tmp2 + tokenArrayList2.get(i).getName();
		File1 = winnow.removeSpace(tmp);
		File2 = winnow.removeSpace(tmp2);
		winnow.toHashTable(File1, hashTable, 5, 2);
		winnow.toHashTable(File2, hashTable2, 5, 2);
		winnow.setFingerPrint(hashTable, fingerPrint, kGram);
		winnow.setFingerPrint(hashTable2, fingerPrint2, kGram);
		double s1 = winnow.fingerPrintScan(fingerPrint, fingerPrint2);
		double s2 = winnow.fingerPrintScan(fingerPrint2, fingerPrint);
		//winnow.similarity(s1, s2);
		
		return winnow.similarity(s1, s2);
	}
	/*
	 * text analyze by change variable name
	 */
	public double textAnalyzeByChangeVariableName(String File1, String File2, int kGram)
	{
		Winnow winnow = new Winnow();
		Parser parser = new Parser();
		String tmp = "";
		String tmp2 = "";
		ArrayList<Token> tokenArrayList = new ArrayList<Token>();
		ArrayList<Token> tokenArrayList2 = new ArrayList<Token>();
		ArrayList<Integer> hashTable = new ArrayList<Integer>();
		ArrayList<Integer> hashTable2 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> window = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> window2 = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> fingerPrint = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> fingerPrint2 = new ArrayList<ArrayList<Integer>>();
		/////////////////////////////////////////
		VariableAnalyzer variableAnalyze = new VariableAnalyzer();
		
		parser.parserCodeToToken(File1, tokenArrayList);
//		System.out.println("1");
//		for(int i=0;i<tokenArrayList.size();i++)
//		{
//			System.out.println(tokenArrayList.get(i).getName()+"     "+tokenArrayList.get(i).getType());
//		}
		parser.setReservedWordType(tokenArrayList);
//		System.out.println("2");
//		for(int i=0;i<tokenArrayList.size();i++)
//		{
//			System.out.println(tokenArrayList.get(i).getName()+"     "+tokenArrayList.get(i).getType());
//		}
		//why it runs ten times? by hongzhan
//		for(int x = 0; x < 10; x++)
//		{
//			parser.setLine(tokenArrayList);
//			parser.setLevel(tokenArrayList);
//		}
//		System.out.println("3");
		parser.setFunction(tokenArrayList);
//		System.out.println("4");
		parser.setVariable(tokenArrayList);
//		System.out.println("5");
		parser.changeVariableNameInfunction(tokenArrayList);
//		System.out.println("6");
//		for(int i=0;i<tokenArrayList.size();i++)
//		{
//			System.out.println(tokenArrayList.get(i).getName()+"     "+tokenArrayList.get(i).getType());
//		}
		//parser.printProgram(tokenArrayList);
		////////////////////////////////////////
		
		parser.parserCodeToToken(File2, tokenArrayList2);
//		System.out.println("2-1");
//		for(int i=0;i<tokenArrayList2.size();i++)
//		{
//			System.out.println(tokenArrayList2.get(i).getName()+"     "+tokenArrayList2.get(i).getType()+"    "+i);
//		}
		parser.setReservedWordType(tokenArrayList2);
//		System.out.println("2-2");
//		for(int i=0;i<tokenArrayList2.size();i++)
//		{
//			System.out.println(tokenArrayList2.get(i).getName()+"     "+tokenArrayList2.get(i).getType());
//		}
//		for(int x = 0; x < 10; x++)
//		{
//			parser.setLine(tokenArrayList2);
//			System.out.println("2-2-1");
//			parser.setLevel(tokenArrayList2);
//			System.out.println("2-2-2");
//		}
//		System.out.println("2-3");
		parser.setFunction(tokenArrayList2);
//		System.out.println("2-4");
		parser.setVariable(tokenArrayList2);
//		System.out.println("2-5");
		parser.changeVariableNameInfunction(tokenArrayList2);
//		System.out.println("2-6");
		//parser.printProgram(tokenArrayList2);
		////////////////////////////////////////////
		
		//parser.parserCodeToToken(File1,tokenArrayList);
		//parser.parserCodeToToken(File2,tokenArrayList2);
		//???file1???file2???tokenArrayList????????????tokens??????????????????string by hongzhan
		for(int i = 0; i < tokenArrayList.size(); i++)
			tmp = tmp + tokenArrayList.get(i).getName();
		for(int i = 0; i < tokenArrayList2.size(); i++)
			tmp2 = tmp2 + tokenArrayList2.get(i).getName();
//		System.out.println(tmp+"\n\n\n");
		File1 = winnow.removeSpace(tmp);
		File2 = winnow.removeSpace(tmp2);
//		System.out.println(File1);
		winnow.toHashTable(File1, hashTable, 5, 2);
		winnow.toHashTable(File2, hashTable2, 5, 2);
		winnow.setFingerPrint(hashTable, fingerPrint, kGram);
		winnow.setFingerPrint(hashTable2, fingerPrint2, kGram);
		double s1 = winnow.fingerPrintScan(fingerPrint, fingerPrint2);
		double s2 = winnow.fingerPrintScan(fingerPrint2, fingerPrint);
		//winnow.similarity(s1, s2);
		
		return winnow.similarity(s1, s2);
	}
	/*
	 * text analyze for structure analyze
	 */
	public double textAnalyzeForStructure(String File1, String File2, int kGram)
	{
		Winnow winnow = new Winnow();
		ArrayList<Integer> hashTable = new ArrayList<Integer>();
		ArrayList<Integer> hashTable2 = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> window = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> window2 = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> fingerPrint = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> fingerPrint2 = new ArrayList<ArrayList<Integer>>();
		
		winnow.toHashTable(File1, hashTable, 7, 2);
		winnow.toHashTable(File2, hashTable2, 7, 2);
		winnow.setFingerPrint(hashTable, fingerPrint, kGram);
		winnow.setFingerPrint(hashTable2, fingerPrint2, kGram);
		double s1 = winnow.fingerPrintScan(fingerPrint, fingerPrint2);
		double s2 = winnow.fingerPrintScan(fingerPrint2, fingerPrint);
		//winnow.similarity(s1, s2);
		
		return winnow.similarity(s1, s2);
	}
	//???????????? ???????????????????????????
	public String removeSpace(String str)
	{
		int index = 0;
		String tmp = "", tmp2 = "";
		//StringTokenizer????????????????????????????????????????????????????????????????????????????????????????????????????????? by hongzhan
		StringTokenizer tokenizer = new StringTokenizer(str);
		//?????????????????? by hongzhan
		while(tokenizer.hasMoreTokens())
			tmp = tmp + tokenizer.nextToken();
		//???tmp????????????????????????????????????????????????tmp2??? by hongzhan
		while(index < tmp.length())
		{
			//if(Character.isLetter(tmp.charAt(index)) /*&&  ((tmp.charAt(index) <= 90 && tmp.charAt(index) >= 65) || (tmp.charAt(index) <= 122 && tmp.charAt(index) >= 97))*/)
				tmp2 = tmp2+tmp.charAt(index);
			index++;
		}
		//System.out.println(tmp2);
		//???tmp2????????????????????????????????? by hongzhan
		return tmp2.toLowerCase();
	}
	//???????????????hash table??????key
	public void toHashTable(String str , ArrayList<Integer> hashTable, int gram, int base)
	{
	     int i = 0;
	     int hashValue = 0;
	     while( i < str.length()-gram)
	     {
	    	 for(int j = 0 ; j < gram ; ++j) 
	    		 hashValue = hashValue + str.charAt(i+j);//???????????????ascii????????? by hongzhan
	    	 hashValue = hashValue%26;
	    	 hashTable.add(hashValue);
	         hashValue = 0;
	         i++;
	     }
	}
	//???key??????window???
	public void setWindow(ArrayList<Integer> hashTable, ArrayList<ArrayList<Integer>> window, int windowSize)
	{
	     int i , j;
	     
	     for( i = 0 ; i < hashTable.size()-windowSize+1 ; i++ )
	     {
	    	 ArrayList<Integer> tmp = new ArrayList<Integer>();
	    	 
	         for( j = 0 ; j < windowSize ; j++ )
	        	 tmp.add(hashTable.get(i+j));
	         
	         window.add(tmp);
	     }
	}
	//??????window??????finger print
	public void setFingerPrint(ArrayList<Integer> hashTable, ArrayList<ArrayList<Integer>> fingerPrint, int kGram)
	{
		int min = 99999999;
		int position = 99999999;
		int flag = 0;
		
		for(int i = 0 ; i <= hashTable.size()-kGram ; i++)
		{
			min = 99999999;
			position = 99999999;
			//??????i???hashvalue???i+kGram???hashvalue??????????????????hashvalue by hongzhan
			for(int j = i ; j < i+kGram ; j++)
			{
				if(hashTable.get(j) <=  min)
				{
					min = hashTable.get(j);
					position = j;    
					
				}
			}
			//???????????????hashvalue??????????????????????????? by hongzhan
			for(int k = 0 ; k < fingerPrint.size() ; k++)
				if(!fingerPrint.isEmpty() && !fingerPrint.get(k).isEmpty())
	        		 if( fingerPrint.get(k).size() == 2 && fingerPrint.get(k).get(1) == position)
	        			 flag = 1;
			//?????????????????????????????????????????????tmp?????????fingerPrint by hongzhan
			if( flag == 0)
			{
				ArrayList<Integer> tmp = new ArrayList<Integer>();
				tmp.add(min);
				tmp.add(position);
				fingerPrint.add(tmp);
			}
	        flag = 0;
		}
	}
	
	public double fingerPrintScan(ArrayList<ArrayList<Integer>> fingerPrint1, ArrayList<ArrayList<Integer>> fingerPrint2)
	{
		 ArrayList<Integer> match = new ArrayList<Integer>();
	     int i , j , k = 0, flag = 0;
	     double count = 0.0 ;
	    
	     for( i = 0 ; i < fingerPrint1.size()-1 ; i++)
	     {
			  for( j = k ; j < fingerPrint2.size()-1 ; j++)
			      if( fingerPrint1.get(i).get(0) == fingerPrint2.get(j).get(0))//????????????value??????????????? by hongzhan
			           if((fingerPrint1.get(i+1).get(0) == fingerPrint2.get(j+1).get(0)) && (fingerPrint1.get(i+1).get(1)-fingerPrint1.get(i).get(1) == fingerPrint2.get(j+1).get(1)-fingerPrint2.get(j).get(1)))//??????????????????value????????????value???????????????????????????????????????value???????????????????????????????????????????????????????????????match by hongzhan
			           {
			        	   match.add(1);
			               k++;
			               flag = 1;
			               break;
			           }
			//??????fingerPrint1.get(i)???fingerPrint2???????????????match????????????match???list?????????0 by hongzhan
			  if(flag == 0)
			  {
				  match.add(0);
			  }
			  flag = 0;
	     }
	     //??????match?????????????????? by hongzhan
	     for( i = 0 ; i < match.size() ; i++)
	          if(match.get(i) == 1)
	              count++;
	     //??????match??????????????????????????? by hongzhan
	     return count/(double)match.size();
	}
	//???????????????
	public double similarity(double s1 , double s2)
	{
		//System.out.println("similarity = " + (s1+s2)/2*100);
		return (s1+s2)/2*100;
	}
}
