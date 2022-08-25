package Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

import LoadFile.*;
import SimilarlyAnalyze.CalculateSimilarly;

import ClassAnalyze.*;



public class CodeAnalyze {

	public static void main(String[] args) throws IOException 
	{
		int topNum;
		CalculateSimilarly calculateSimilarly = new CalculateSimilarly();	//CalculateSimilarly建構子
		LoadFile loadFile = new LoadFile();	//LoadFile建構子
		ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();	//file的array list

		
		// ricehuang 2011-04-08
		//fileList = loadFile.LoadArrayOfFile("D:\\workspace\\001\\028");	//設定路徑
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\博士論文\\C++Parsing\\source\\sample\\sample");	//設定路徑
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\博士論文\\C++Parsing\\source\\sample\\sample");	//設定路徑
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\博士論文\\C++Parsing\\source\\sample_hybrid_single_comparison\\variable");	//設定路徑
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\博士論文\\C++Parsing\\Experimet\\comparsion\\struct");	//比較結構
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\博士論文\\C++Parsing\\Experimet\\comparsion\\text");	//比較文字
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\博士論文\\C++Parsing\\Experimet\\comparsion\\variable");	    //比較變數
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\博士論文\\C++Parsing\\Experimet\\trick\\test");	    //比較Trick
		
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\Encapsulation");//比較封裝		
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\E+I");//比較封裝+繼承
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\E+I+P");//比較封裝+繼承+多型
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\E+I+P+O");//比較封裝+繼承+多型+多載
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\noclass");//比較非oo程式
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\comparsion\\s");//比較結構
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\comparsion\\v");//比較變數
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\comparsion\\t");//比較文字
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\trick\\trick");//比較trick
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\sample(c++)");
		fileList = loadFile.LoadArrayOfFile("F:\\Documents and Settings\\HongZhan\\My Documents\\我已接收的檔案\\004");
	System.out.println("H:\\workspace\\CPD_Original\\sample(c to c++)");
		calculateSimilarly.degreeOfPlagiarismUsingTextAnalyze(6, fileList);	//使用Text Analyze計算相似度
		calculateSimilarly.degreeOfPlagiarismUsingStructureAnalyze(6, fileList);	//使用Structure Analyze
		calculateSimilarly.degreeOfPlagiarismUsingVariableAnalyze2(6, fileList);	//使用Variable Analyze
		calculateSimilarly.degreeOfPlagiarismUsingOOAnalyze(6, fileList); //使用OO Analyze
		calculateSimilarly.degreeOfTotalAnalyzePlagiarism(6, fileList);	//計算平均相似度	
	}
}
