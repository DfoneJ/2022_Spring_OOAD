package WebGUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JProgressBar;

import JPD.filemanager.FileList;
import JPD.filemanager.FileEditor;

public class JavaPlagiarismDetection extends PlagiarismDetection  
{
	JavaPlagiarismDetection(SettingInformation settingInformation) 
	{
		super(settingInformation);
	}
	
	 public String[][] execute()
	 {
		 String[][] similarly = null;
		 convertJavaToClass();
		 convertClassToJad();
		 similarly = sort(compare());
	     try 
	     {
			new File(new java.io.File(".").getCanonicalPath()+"\\jad.exe").delete();
	     } 
	     catch (IOException e) 
	     {
			// TODO Auto-generated catch block
			e.printStackTrace();
	     }

		 deleteTempFile(new File(settingInformation.getPath()+"/temp"));
		 return similarly;
	 }
	 
	 private void convertJavaToClass()
	 {
        FileEditor codeComplier = new FileEditor(settingInformation.getPath());
        codeComplier.convertJavaToClass();
	 }
	 
	 private void convertClassToJad()
	 {
        FileEditor codeComplier = new FileEditor(settingInformation.getPath());
        
    	try 
    	{
			if(!new File(new java.io.File(".").getCanonicalPath()+"\\jad.exe").exists())
			{
				URL onlineJad = new URL("http://www.cc.ntut.edu.tw/~t9598031/jad.exe");
				URLConnection onlineJadConnection = onlineJad.openConnection();
				onlineJadConnection.connect();
				InputStream sendJad = onlineJadConnection.getInputStream();
				FileOutputStream receiveJad = new FileOutputStream(new java.io.File(".").getCanonicalPath()+"\\jad.exe");
				byte[] tmp = new byte[1]; 	
				
				while( sendJad.read(tmp) != -1 )
				{
					receiveJad.write(tmp);
				}
				
				sendJad.close();
				receiveJad.close();    		
			}
		} 
    	catch (MalformedURLException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	catch (FileNotFoundException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	catch (IOException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        codeComplier.convertClassToJad();
	 }
	 
	 private ArrayList<ArrayList<String>> compare()
	 {
		 ArrayList<ArrayList<String>> similarly = new ArrayList<ArrayList<String>>();
	     Vector blackList,textSimarity,structureSimarity,symbolSimarity;
		 FileList fileList;
		 int interval = 1;
		 int kgramSize = 4;
		 int windowSize = 3;
		 String hashAlgorithm = "TRD";
		 boolean textSelected = settingInformation.isTextAnalyzer();
		 boolean variableSelected = settingInformation.isVariableAnalyzer();
		 boolean structureSelected = settingInformation.isStructureAnalyzer();
		 double txtSimLimit = settingInformation.getThresholdOfText()/100;
		 double symbolSimLimit = settingInformation.getThresholdOfVariable()/100;
		 double structureSimLimit = settingInformation.getThresholdOfStructure()/100;
		 
		 fileList = new FileList(settingInformation.getPath(),hashAlgorithm ,kgramSize, interval, windowSize ,txtSimLimit,structureSimLimit, symbolSimLimit,textSelected , structureSelected ,variableSelected );
		 
		 int size = fileList.length();
		 String filename;
		 int len;
		 double tmpSim;
		 int totalWeight;
		 int samePairLoc;
		
		 for (int i = 0; i < size; i++) 
		 {
			 filename = fileList.getFilename(i);
			 fileList.compare(i);
			 blackList = fileList.getBlackList();
			 textSimarity = fileList.getTextSimarity();
			 structureSimarity = fileList.getStructureSimarity();
			 symbolSimarity = fileList.getSymbolSimarity();
			 len = blackList.size();
			 if(len>0)
			 {
				 for(int k=0 ; k<len ; k++)
				 {
					 tmpSim = 0;
					 totalWeight = 0;
					 samePairLoc = findSamePairLocation(similarly,filename,(String)blackList.elementAt(k));
		  
					 if(settingInformation.isStructureAnalyzer())
					 {
						 tmpSim+=Double.parseDouble((String)structureSimarity.elementAt(k))*100*settingInformation.getWeightOfStructure();
						 totalWeight+=settingInformation.getWeightOfStructure();
					 }
		        	
					 if(settingInformation.isTextAnalyzer())
					 {
						 tmpSim+=Double.parseDouble((String)textSimarity.elementAt(k))*100*settingInformation.getWeightOfText();
						 totalWeight+=settingInformation.getWeightOfText(); 
					 }
		        	
					 if(settingInformation.isVariableAnalyzer())
					 {
						 tmpSim+=Double.parseDouble((String)symbolSimarity.elementAt(k))*100*settingInformation.getWeightOfVariable();
						 totalWeight+=settingInformation.getWeightOfVariable(); 
					 }
		        	
					 tmpSim/=totalWeight;
					 
					 if(samePairLoc<0)
					 {
						 ArrayList<String> tmp = new ArrayList<String>();
						 tmp.add(filename);
						 tmp.add((String)blackList.elementAt(k));
						 tmp.add(String.valueOf(tmpSim));
						 similarly.add(tmp);
					 }
					 else
					 {
						 similarly.get(samePairLoc).set(2,String.valueOf((Double.parseDouble(similarly.get(samePairLoc).get(2))+tmpSim)/2));
					 }
				 }
			 }
		 }
		 
		 return similarly;
	 	}
	 
	 private String[][] sort(ArrayList<ArrayList<String>> similarly)
	 {
		 double max;
		 int maxLoc = 0;
		 int simSize = similarly.size();
		 NumberFormat nf = NumberFormat.getInstance();
		 nf.setMaximumFractionDigits(3);
		 String[][] finalSimilarly = new String[similarly.size()][3];

		 for(int i=0;i<simSize;i++)
		 {
			 max = 0;
			 
			 for(int j=0;j<similarly.size();j++)
			 {
				 if(Double.parseDouble(similarly.get(j).get(2))>max)
				 {
					 max = Double.parseDouble(similarly.get(j).get(2));
					 maxLoc = j;
				 }
			 }
			 
			 if(Double.parseDouble(similarly.get(maxLoc).get(2))<settingInformation.getThresholdOfTotal())
			 {
				 break;
			 }
			 
			 finalSimilarly[i][0] = similarly.get(maxLoc).get(0);
			 finalSimilarly[i][1] = similarly.get(maxLoc).get(1);
			 finalSimilarly[i][2] = nf.format(Double.parseDouble(similarly.get(maxLoc).get(2)));
			 similarly.remove(maxLoc);	 
		 }

		 return finalSimilarly;
	 }
	
	 private void deleteTempFile(File file)
	 {
		 if(!file.delete())//先嘗試直接刪除,若文件夾非空。枚舉、遞歸刪除裏面內容
		 {
			 File subs[] = file.listFiles();
			 
			 for (int i = 0; i <= subs.length - 1; i++)
			 {
				 if (subs[i].isDirectory())
				 {
					 deleteTempFile(subs[i]);//遞歸刪除子文件夾內容
				 }
				 
				 subs[i].delete();//刪除子文件夾本身
			 }
			 
			 file.delete();//刪除此文件夾本身
		 }
	 }
	 
	 private int findSamePairLocation(ArrayList<ArrayList<String>> similarly,String file1,String file2)
	 {
		 for(int i=0;i<similarly.size();i++)
		 {
			 if(similarly.get(i).get(1).equals(file1))
			 {
				 if(similarly.get(i).get(0).equals(file2))
				 {
					 return i;
				 }
			 }
			 
		 }
		 
		 return -1;
	 }
}