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
		CalculateSimilarly calculateSimilarly = new CalculateSimilarly();	//CalculateSimilarly�غc�l
		LoadFile loadFile = new LoadFile();	//LoadFile�غc�l
		ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();	//file��array list

		
		// ricehuang 2011-04-08
		//fileList = loadFile.LoadArrayOfFile("D:\\workspace\\001\\028");	//�]�w���|
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\�դh�פ�\\C++Parsing\\source\\sample\\sample");	//�]�w���|
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\�դh�פ�\\C++Parsing\\source\\sample\\sample");	//�]�w���|
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\�դh�פ�\\C++Parsing\\source\\sample_hybrid_single_comparison\\variable");	//�]�w���|
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\�դh�פ�\\C++Parsing\\Experimet\\comparsion\\struct");	//������c
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\�դh�פ�\\C++Parsing\\Experimet\\comparsion\\text");	//�����r
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\�դh�פ�\\C++Parsing\\Experimet\\comparsion\\variable");	    //����ܼ�
		//fileList = loadFile.LoadArrayOfFile("G:\\NTUT\\�դh�פ�\\C++Parsing\\Experimet\\trick\\test");	    //���Trick
		
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\Encapsulation");//����ʸ�		
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\E+I");//����ʸ�+�~��
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\E+I+P");//����ʸ�+�~��+�h��
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\E+I+P+O");//����ʸ�+�~��+�h��+�h��
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\classtest\\noclass");//����Doo�{��
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\comparsion\\s");//������c
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\comparsion\\v");//����ܼ�
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\comparsion\\t");//�����r
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\trick\\trick");//���trick
	//	fileList = loadFile.LoadArrayOfFile("H:\\workspace\\CPD_Original\\sample(c++)");
		fileList = loadFile.LoadArrayOfFile("F:\\Documents and Settings\\HongZhan\\My Documents\\�ڤw�������ɮ�\\004");
	System.out.println("H:\\workspace\\CPD_Original\\sample(c to c++)");
		calculateSimilarly.degreeOfPlagiarismUsingTextAnalyze(6, fileList);	//�ϥ�Text Analyze�p��ۦ���
		calculateSimilarly.degreeOfPlagiarismUsingStructureAnalyze(6, fileList);	//�ϥ�Structure Analyze
		calculateSimilarly.degreeOfPlagiarismUsingVariableAnalyze2(6, fileList);	//�ϥ�Variable Analyze
		calculateSimilarly.degreeOfPlagiarismUsingOOAnalyze(6, fileList); //�ϥ�OO Analyze
		calculateSimilarly.degreeOfTotalAnalyzePlagiarism(6, fileList);	//�p�⥭���ۦ���	
	}
}
