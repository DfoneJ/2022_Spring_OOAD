package WebGUI;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JProgressBar;

import LoadFile.LoadFile;
import SimilarlyAnalyze.CalculateSimilarly;

public class CAndCppPlagiarismDetection extends PlagiarismDetection 
{

	public CAndCppPlagiarismDetection(SettingInformation settingInformation) 
	{
		super(settingInformation);
	}
	
	/*�ާ@CPD�A�è��^���G*/
	 public String[][] execute()
	 {
		ArrayList<ArrayList<String>> totalSimilarityTable;
		CalculateSimilarly calculateSimilarly = new CalculateSimilarly();	//CalculateSimilarly�غc�l
		LoadFile loadFile = new LoadFile();	//LoadFile�غc�l
		ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();	//file��array list
		
		try 
		{
			fileList = loadFile.LoadArrayOfFile(settingInformation.getPath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if(settingInformation.isStructureAnalyzer())
		{
			calculateSimilarly.degreeOfPlagiarismUsingStructureAnalyze(6, fileList);	//�ϥ�Structure Analyze
			calculateSimilarly.degreeOfPlagiarismUsingOOAnalyze(6, fileList); //�ϥ�OO Analyze
			calculateSimilarly.calculateStructureSimilarly();	//��XDCS Tree�POO�ۦ���
		}
		
		if(settingInformation.isTextAnalyzer())
		{
			calculateSimilarly.degreeOfPlagiarismUsingTextAnalyze(6, fileList);	//�ϥ�Text Analyze�p��ۦ���	
		}
		
		if(settingInformation.isVariableAnalyzer())
		{
			calculateSimilarly.degreeOfPlagiarismUsingVariableAnalyze2(6, fileList);	//�ϥ�Variable Analyze		
		}
		
		calculateSimilarly.DeleteByThresholdsAndcalculateAverageSimilarly(settingInformation);
		totalSimilarityTable = calculateSimilarly.sortTotalSimilarly(settingInformation);
		return ArrayList2Array(totalSimilarityTable,fileList);
	 }
	 
	 private String[][] ArrayList2Array(ArrayList<ArrayList<String>> totalSimilarityTable,ArrayList<ArrayList<String>> fileList)
	 {
		String[][] similarly = new String[totalSimilarityTable.size()][3];
		NumberFormat resultFormat = NumberFormat.getInstance();
		resultFormat.setMaximumFractionDigits(3);
		 
		 for(int i=0;i<totalSimilarityTable.size();i++)
		 {
			 similarly[i][0] = fileList.get(Integer.parseInt(totalSimilarityTable.get(i).get(0))).get(0);
			 similarly[i][1] = fileList.get(Integer.parseInt(totalSimilarityTable.get(i).get(1))).get(0);
			 similarly[i][2] = resultFormat.format(Double.parseDouble(totalSimilarityTable.get(i).get(2)));
		 }
		 
		 return similarly;
	 }
}