package WebGUI;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class testcon 
{
	public static void main(String[] args) throws IOException 
	{
		/*
		cmd /C start javac G:\sample(java)\temp\Category\Category.java -d G:\sample(java)\temp\Category\
		cmd /C start javac G:\sample(java)\temp\FileUtil\FileUtil.java -d G:\sample(java)\temp\FileUtil\
		cmd /C start javac G:\sample(java)\temp\FTP\FTP.java -d G:\sample(java)\temp\FTP\
		cmd /C start javac G:\sample(java)\temp\ThumbMaker\ThumbMaker.java -d G:\sample(java)\temp\ThumbMaker\
		 
		Process proc = null;
		try{
		proc = Runtime.getRuntime().exec("cmd /C start javac G:/sample(java)/temp/Category/Category.java -d G:/sample(java)/temp/Category/");
		proc.waitFor();
		proc.destroy();
		}
		catch(Exception ioe){
		ioe.printStackTrace();
		}
		*/       
        System.out.println(System.getProperty("SystemRoot"));
        System.out.println(System.getProperty("user.dir"));
        System.out.println(System.getenv("SystemRoot"));
        System.out.println(System.getenv("windir"));
        
	//	File settingRecord  = new File ("G:\\CPDSetting.txt");
	    FileWriter fwriter;
		try 
		{
			fwriter = new FileWriter("G:\\CPDSetting.txt");
			fwriter.write(true+"\r\n");
			fwriter.write(System.getenv("windir"));
			fwriter.write(System.getenv("windir"));
			fwriter.write(System.getenv("windir"));
			fwriter.write(System.getenv("windir"));
			fwriter.write(System.getenv("windir"));
			fwriter.write(System.getenv("windir"));
			fwriter.close();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
}
