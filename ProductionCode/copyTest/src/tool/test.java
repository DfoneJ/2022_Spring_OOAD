package tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test {

	 public static void main(String[] args) throws Exception {
	        ProcessBuilder builder = new ProcessBuilder(
	        "C:\\Program Files\\7-Zip\\7z.exe", "-oD:\\Program Files\\jplag\\temp\\out","e", "D:\\Program Files\\jplag\\temp\\023.zip");
	        builder.redirectErrorStream(true);
	        Process p = builder.start();
	        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	        String line;
	        while (true) {
	            line = r.readLine();
	            if (line == null) { break; }
	            System.out.println(line);
	        }
	    }

}
