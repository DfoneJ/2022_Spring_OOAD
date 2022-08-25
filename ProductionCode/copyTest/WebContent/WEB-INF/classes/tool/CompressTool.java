package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompressTool {
	String _7zPath = "C:/Program Files/7-Zip/7z.exe";

	public CompressTool(){
	}
	
	public boolean decompressFile(String fileName,String targetPath) {         
        try {           
            String osName = System.getProperty ("os.name");
            String[] cmd = new String[3];
            cmd[0] = "cmd";
            cmd[1] = "/C";
            cmd[2] = "\""+_7zPath+"\" e \""+fileName+"\" -o"+targetPath+"";
             
            Runtime rt = Runtime.getRuntime();

            System.out.println ("Execing " + cmd[0] + " " + cmd[1]+ " " + cmd[2]);

            Process proc = rt.exec(cmd);                                     
            // any error???

            OutputStreamWriter osr = new OutputStreamWriter (proc.getOutputStream());
            osr.write("S\n"); //如果有問是否需取代  (Y)es / (N)o / (A)lways / (S)kip all / A(u)to rename all / (Q)uit? 
            osr.flush();
            /*InputStreamReader isr = new InputStreamReader (proc.getInputStream());
            BufferedReader br = new BufferedReader (isr);
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null)
            	sb.append(line+"\n"); 
            System.out.println(sb.toString());*/
            
            int exitVal = proc.waitFor ();
            if(exitVal==0){
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
