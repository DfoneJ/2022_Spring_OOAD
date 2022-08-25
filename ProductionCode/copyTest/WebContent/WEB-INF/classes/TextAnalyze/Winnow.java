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
		parser.setReservedWordType(tokenArrayList);
		//why it runs ten times? by hongzhan
		for(int x = 0; x < 10; x++)
		{
			parser.setLine(tokenArrayList);
			parser.setLevel(tokenArrayList);
		}
		parser.setFunction(tokenArrayList);
		parser.setVariable(tokenArrayList);
		parser.changeVariableNameInfunction(tokenArrayList);
		//parser.printProgram(tokenArrayList);
		////////////////////////////////////////
		
		parser.parserCodeToToken(File2, tokenArrayList2);
		parser.setReservedWordType(tokenArrayList2);
		for(int x = 0; x < 10; x++)
		{
			parser.setLine(tokenArrayList2);
			parser.setLevel(tokenArrayList2);
		}
		parser.setFunction(tokenArrayList2);
		parser.setVariable(tokenArrayList2);
		parser.changeVariableNameInfunction(tokenArrayList2);
		//parser.printProgram(tokenArrayList2);
		////////////////////////////////////////////
		
		//parser.parserCodeToToken(File1,tokenArrayList);
		//parser.parserCodeToToken(File2,tokenArrayList2);
		//�Nfile1��file2��tokenArrayList�����Ҧ�tokens�U�ۦꦨ�@��string by hongzhan
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
	//�����ť� ��Ҧ��^��r�ܤp�g
	public String removeSpace(String str)
	{
		int index = 0;
		String tmp = "", tmp2 = "";
		//StringTokenizer�w�]�|�ϥΪťզr���Ӥ����r��A�ťզr���]�A�F�ť�����U�B����δ���r�� by hongzhan
		StringTokenizer tokenizer = new StringTokenizer(str);
		//�M���ťզr�� by hongzhan
		while(tokenizer.hasMoreTokens())
			tmp = tmp + tokenizer.nextToken();
		//�Ntmp�����e�A�@�Ӧr���@�Ӧr�����ƻs��tmp2�W by hongzhan
		while(index < tmp.length())
		{
			//if(Character.isLetter(tmp.charAt(index)) /*&&  ((tmp.charAt(index) <= 90 && tmp.charAt(index) >= 65) || (tmp.charAt(index) <= 122 && tmp.charAt(index) >= 97))*/)
				tmp2 = tmp2+tmp.charAt(index);
			index++;
		}
		//System.out.println(tmp2);
		//�Ntmp2�����e�����ର�p�g�^�� by hongzhan
		return tmp2.toLowerCase();
	}
	//�N�r���g�Lhash table�নkey
	public void toHashTable(String str , ArrayList<Integer> hashTable, int gram, int base)
	{
	     int i = 0;
	     int hashValue = 0;
	     while( i < str.length()-gram)
	     {
	    	 for(int j = 0 ; j < gram ; ++j)
	    		 hashValue = hashValue + str.charAt(i+j);//�N�r���নascii�X�ۥ[ by hongzhan
	    	 hashValue = hashValue%26;
	    	 hashTable.add(hashValue);
	         hashValue = 0;
	         i++;
	     }
	}
	//�Nkey��iwindow��
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
	//�g��window�নfinger print
	public void setFingerPrint(ArrayList<Integer> hashTable, ArrayList<ArrayList<Integer>> fingerPrint, int kGram)
	{
		int min = 99999999;
		int position = 99999999;
		int flag = 0;
		
		for(int i = 0 ; i <= hashTable.size()-kGram ; i++)
		{
			min = 99999999;
			position = 99999999;
			//�q��i��hashvalue��i+kGram��hashvalue����M�̤p��hashvalue by hongzhan
			for(int j = i ; j < i+kGram ; j++)
			{
				if(hashTable.get(j) <=  min)
				{
					min = hashTable.get(j);
					position = j;    
					
				}
			}
			//�w�Q���L��hashvalue�h���L�A���A���ƨ� by hongzhan
			for(int k = 0 ; k < fingerPrint.size() ; k++)
				if(!fingerPrint.isEmpty() && !fingerPrint.get(k).isEmpty())
	        		 if( fingerPrint.get(k).size() == 2 && fingerPrint.get(k).get(1) == position)
	        			 flag = 1;
			//�p�G�S�����ơA�N��ȤΦ�m�s�Jtmp�����JfingerPrint by hongzhan
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
			      if( fingerPrint1.get(i).get(0) == fingerPrint2.get(j).get(0))//�P�_���value�O�_���ۦP by hongzhan
			           if((fingerPrint1.get(i+1).get(0) == fingerPrint2.get(j+1).get(0)) && (fingerPrint1.get(i+1).get(1)-fingerPrint1.get(i).get(1) == fingerPrint2.get(j+1).get(1)-fingerPrint2.get(j).get(1)))//�P�_��ӬۦPvalue���U�@��value�O�_�]�ۦP�A�p���A�h�ݨ��value������m���t�Z�O�_�ۦP�A�p�G�]�O�~�N��u��match by hongzhan
			           {
			        	   match.add(1);
			               k++;
			               flag = 1;
			               break;
			           }
			  //�p�GfingerPrint1.get(i)�bfingerPrint2���S�����match���A�h�bmatch��list���[�J0 by hongzhan
			  if(flag == 0)
			  {
				  match.add(0);
			  }
			  flag = 0;
	     }
	     //�p��match���ƶq���h�� by hongzhan
	     for( i = 0 ; i < match.size() ; i++)
	          if(match.get(i) == 1)
	              count++;
	     //�^��match���ƶq�P�`�ƪ���� by hongzhan
	     return count/(double)match.size();
	}
	//�^�Ǭۦ���
	public double similarity(double s1 , double s2)
	{
		//System.out.println("similarity = " + (s1+s2)/2*100);
		return (s1+s2)/2*100;
	}
}
