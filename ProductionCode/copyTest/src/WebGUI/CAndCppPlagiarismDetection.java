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
	
	 public String[][] execute()
	 {
		ArrayList<ArrayList<String>> totalSimilarityTable;
		CalculateSimilarly calculateSimilarly = new CalculateSimilarly();	//CalculateSimilarly
		LoadFile loadFile = new LoadFile();	//LoadFile
		ArrayList<ArrayList<String>> fileList = new ArrayList<ArrayList<String>>();	
		
		try 
		{
			//回傳zip檔內所有file的名字、file內容、file路徑
			fileList = loadFile.LoadArrayOfFile(settingInformation.getPath());
//			for(int i=0;i<fileList.size();i++)System.out.println(fileList.get(i).get(2));
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		if(settingInformation.isStructureAnalyzer())
		{
			System.out.println("StructureAnalyze start: ");
			calculateSimilarly.degreeOfPlagiarismUsingStructureAnalyze(6, fileList);	//使用Structure Analyze
			calculateSimilarly.degreeOfPlagiarismUsingOOAnalyze(6, fileList); //使用OO Analyze
			calculateSimilarly.calculateStructureSimilarly();	//整合DCS Tree與OO相似度
		}
		
		if(settingInformation.isTextAnalyzer())
		{
			System.out.println("TextAnalyze start: ");
			calculateSimilarly.degreeOfPlagiarismUsingTextAnalyze(6, fileList);	//使用Text Analyze計算相似度	
		}
		
		if(settingInformation.isVariableAnalyzer())
		{
			System.out.println("VariableAnalyze start: ");
			calculateSimilarly.degreeOfPlagiarismUsingVariableAnalyze2(6, fileList);	//使用Variable Analyze		
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