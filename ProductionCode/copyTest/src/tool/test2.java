package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class test2 {

	 public static void main(String[] args) throws Exception {
		 
		 String _7zPath = "C:/Program Files/7-Zip/7z.exe";
		 String targetPath = "";
		 String fileName = "";
		 try {
		 String osName = System.getProperty ("os.name");
         String[] cmd = new String[3];
         cmd[0] = "cmd";
         cmd[1] = "/C";
         //cmd[2] = "\""+_7zPath+"\" e \""+fileName+"\" -o"+targetPath+"";
         cmd[2] = "\""+_7zPath+"\" -o\""+targetPath+"\" e "+fileName+"";
          
         Runtime rt = Runtime.getRuntime();

         System.out.println ("Execing " + cmd[0] + " " + cmd[1]+ " " + cmd[2]);

         Process proc = rt.exec(cmd);                                     
         // any error???

         OutputStreamWriter osr = new OutputStreamWriter (proc.getOutputStream());
         osr.write("S\n"); //如果有問是否需取代  (Y)es / (N)o / (A)lways / (S)kip all / A(u)to rename all / (Q)uit? 
         osr.flush();
         
         int exitVal = proc.waitFor ();

         proc.destroy();
     } 
     catch (Exception e) {
         e.printStackTrace ();
     }
		 
		 
		 
	 }
}
