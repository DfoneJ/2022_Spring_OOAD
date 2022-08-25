package tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompressTool {
	String _7zPath = "/user/bin/7z";

	public CompressTool(){
	}
	
	public boolean decompressFile(String fileName,String targetPath) {     
		try
		{
			Runtime rt = Runtime.getRuntime();
			System.getProperty("os.name");
			String[] cmd = new String[3];
			String[] outputFile = fileName.split("\\.");
			cmd[0] = "7za";
			cmd[1] = "x";
			cmd[2] = fileName + " -o"+targetPath;
			System.out.println("Execing " + cmd[0] + " " + cmd[1] + " " + cmd[2]); 
			Process proc = rt.exec(cmd[0] + " " + cmd[1] + " " + cmd[2]);
			try
			{
				OutputStreamWriter osr = new OutputStreamWriter (proc.getOutputStream());
				osr.write("S\n"); //如果有問是否需取代  (Y)es / (N)o / (A)lways / (S)kip all / A(u)to rename all / (Q)uit? 
	            osr.flush();
			}
			catch(Exception e){}
			//Process proc = rt.exec("7za x /home/lucas/eclipse-workspace/temp/newtest.7z -o/home/lucas/eclipse-workspace/temp/");
			//7za x /home/lucas/eclipse-workspace/temp/newtest.7z -o/home/lucas/eclipse-workspace/temp 
			// any error???
			int exitVal=proc.waitFor();
			//int exitVal = proc.waitFor();
			if (exitVal == 0) 
			{
				return true;
			}
			proc.destroy();
		}
        catch (Exception e) {
            e.printStackTrace ();
        }
        return false;
    }
}
