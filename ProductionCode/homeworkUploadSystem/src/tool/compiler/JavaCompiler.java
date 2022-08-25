package tool.compiler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class JavaCompiler implements Compiler {
	private String[] packageName = null;
	private String inputPath = "";
	private String fileName = "";
	private String outputPath = "";
	private String oldfileName = "";


	public JavaCompiler(String[] packageName, String inputPath, String fileName, String outputPath,
			String oldfileName) {
		super();
		this.packageName = packageName;
		this.inputPath = inputPath;
		this.fileName = fileName;
		this.outputPath = outputPath;
		this.oldfileName = oldfileName;
	}


	@Override
	public Process compile(Runtime rt) throws IOException {
		// TODO Auto-generated method stub
		
		String[] cmd = new String[3];
		File newFile = new File(inputPath + fileName);
		File abstractFile = new File(inputPath + oldfileName);
		boolean done = newFile.renameTo(abstractFile);
		System.out.println(done);
		
		cmd[0] = "/bin/sh";
		cmd[1] = "-c";
		cmd[2] = "javac -d " + outputPath+ " "+inputPath + oldfileName;
		
		System.out.println("\nExecing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
		Process process = rt.exec(cmd);
		//取得package name
		@SuppressWarnings("resource")
		BufferedReader buf=new BufferedReader(new FileReader(abstractFile));
		while(buf.ready())
		{
			String line=buf.readLine();
			//System.out.println(line);
			if(line.indexOf("package")!=-1)
			{
				String s2=line.substring(line.indexOf("package"));
				//System.out.println(s2);
				StringTokenizer st = new StringTokenizer(s2);
				st.nextToken();
				String s3=st.nextToken();
				//System.out.println(s3);
				String s4[]=s3.split(";");
				System.out.println(s4[0]);
				packageName[0]=s4[0];
				break;
			}
		}
		return process;
	}
}