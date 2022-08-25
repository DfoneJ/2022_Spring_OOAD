package WebGUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SettingInformation 
{
	private String SettingTxt = "D:/CPDSetting.txt";	//�s��]�w��txt�ɦW��
    private String path = "";	//�������|
    private String language = "C/C++";	//������H�y��
    private boolean detectedFile = false;	//�ɮצC���O�_�ܤ֦�����ɮ�
    private boolean structureAnalyzer = true;	//�O�_�ϥε��c���R
    private boolean variableAnalyzer = true;	//�O�_�ϥ��ܼƤ��R
    private boolean textAnalyzer = true;	//�O�_�ϥΤ�r���R
    private double thresholdOfText = 0.0;	//��r���R�ۦ��ת����e��
    private double thresholdOfStructure = 0.0;	//���c���R�ۦ��ת����e��
    private double thresholdOfVariable = 0.0;	//�ܼƤ��R�ۦ��ת����e��
    private double thresholdOfTotal = 0.0;	//�`�ۦ��ת����e��
    private int weightOfText = 1;	//��r���R�ۦ��ת��v��
    private int weightOfStructure = 1;	//���c���R�ۦ��ת��v��
    private int weightOfVariable = 1;	//�ܼƤ��R�ۦ��ת��v��
    
    public SettingInformation()
    {
    	//��local�ݬO�_�����e�����]�w��txt�ɡA�p���h�̷�txt�ɭק�]�w�A�_�h�̷Ӫ�l�]�w�إ�txt��
    	if(new File(SettingTxt).exists())
    	{
    		updateSettingInformation();
    	}
    	else
    	{
    		createSettingRecord();
    	}
    }
    
	public String getPath()
	{
		return path;
	}

	public String getLanguage()
	{
		return language;
	}

	public boolean haveDetectedFile()
	{
		return detectedFile;
	}
	
	public boolean isStructureAnalyzer()
	{
		return structureAnalyzer;
	}

	public boolean isVariableAnalyzer()
	{
		return variableAnalyzer;
	}

	public boolean isTextAnalyzer()
	{
		return textAnalyzer;
	}

	public double getThresholdOfText()
	{
		return thresholdOfText;
	}

	public double getThresholdOfStructure()
	{
		return thresholdOfStructure;
	}

	public double getThresholdOfVariable()
	{
		return thresholdOfVariable;
	}

	public double getThresholdOfTotal()
	{
		return thresholdOfTotal;
	}
	
	public int getWeightOfText()
	{
		return weightOfText;
	}

	public int getWeightOfStructure()
	{
		return weightOfStructure;
	}

	public int getWeightOfVariable()
	{
		return weightOfVariable;
	}

	public void setPath(String path)
	{
		this.path = path;
		updateSettingRecord();
	}
	
	public void setLanguage(String language)
	{
		this.language = language;
		updateSettingRecord();
	}
	
	public void setDetectedFile(boolean detectedFile)
	{
		this.detectedFile = detectedFile;
		updateSettingRecord();
	}
	
	public void setStructureAnalyzer(boolean structureAnalyzer)
	{
		this.structureAnalyzer = structureAnalyzer;
		updateSettingRecord();
	}
	
	public void setVariableAnalyzer(boolean variableAnalyzer)
	{
		this.variableAnalyzer = variableAnalyzer;
		updateSettingRecord();
	}
	
	public void setTextAnalyzer(boolean textAnalyzer)
	{
		this.textAnalyzer = textAnalyzer;
		updateSettingRecord();
	}

	public void setThresholdOfText(double thresholdOfText)
	{
		this.thresholdOfText = thresholdOfText;
		updateSettingRecord();
	}
	
	public void setThresholdOfStructure(double thresholdOfStructure)
	{
		this.thresholdOfStructure = thresholdOfStructure;
		updateSettingRecord();
	}
	
	public void setThresholdOfVariable(double thresholdOfVariable)
	{
		this.thresholdOfVariable = thresholdOfVariable;
		updateSettingRecord();
	}
	
	public void setThresholdOfTotal(double thresholdOfTotal)
	{
		this.thresholdOfTotal = thresholdOfTotal;
		updateSettingRecord();
	}
	
	public void setWeightOfText(int weightOfText)
	{
		this.weightOfText = weightOfText;
		updateSettingRecord();
	}
	
	public void setWeightOfStructure(int weightOfStructure)
	{
		this.weightOfStructure = weightOfStructure;
		updateSettingRecord();
	}
	
	public void setWeightOfVariable(int weightOfVariable)
	{
		this.weightOfVariable = weightOfVariable;
		updateSettingRecord();
	}
	
	public void setSettingTxt(String s)
	{
		SettingTxt = s;
	}
	
	/*��s�����]�w��txt��*/
	private void updateSettingRecord()
	{
		new File(SettingTxt).delete();
		createSettingRecord();
	}
	
	/*��sSettingInformation*/
	private void updateSettingInformation()
	{
		try 
		{
			FileReader settingRecord = new FileReader(SettingTxt);
			BufferedReader settingRecordReader = new BufferedReader(settingRecord);
		    path = settingRecordReader.readLine();
		    language = settingRecordReader.readLine();
		    structureAnalyzer = Boolean.parseBoolean(settingRecordReader.readLine());
		    variableAnalyzer = Boolean.parseBoolean(settingRecordReader.readLine());
		    textAnalyzer = Boolean.parseBoolean(settingRecordReader.readLine());
		    thresholdOfText = Double.parseDouble(settingRecordReader.readLine());
		    thresholdOfStructure = Double.parseDouble(settingRecordReader.readLine());
		    thresholdOfVariable = Double.parseDouble(settingRecordReader.readLine());
		    thresholdOfTotal = Double.parseDouble(settingRecordReader.readLine());
		    weightOfText = Integer.parseInt(settingRecordReader.readLine());
		    weightOfStructure = Integer.parseInt(settingRecordReader.readLine());
		    weightOfVariable = Integer.parseInt(settingRecordReader.readLine());
		    settingRecordReader.close();
		    settingRecord.close();
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
	}
	
	/*�إ�SettingRecord*/
	public void createSettingRecord()
	{
		try 
		{
			FileWriter fwriter = new FileWriter(SettingTxt);
			fwriter.write(path+"\r\n");
			fwriter.write(language+"\r\n");
			fwriter.write(structureAnalyzer+"\r\n");
			fwriter.write(variableAnalyzer+"\r\n");
			fwriter.write(textAnalyzer+"\r\n");
			fwriter.write(thresholdOfText+"\r\n");
			fwriter.write(thresholdOfStructure+"\r\n");
			fwriter.write(thresholdOfVariable+"\r\n");
			fwriter.write(thresholdOfTotal+"\r\n");
			fwriter.write(weightOfText+"\r\n");
			fwriter.write(weightOfStructure+"\r\n");
			fwriter.write(weightOfVariable+"\r\n");
			fwriter.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}