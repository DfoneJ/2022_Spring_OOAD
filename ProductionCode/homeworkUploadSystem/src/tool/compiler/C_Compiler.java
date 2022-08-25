package tool.compiler;

import java.io.File;
import java.io.IOException;

import ExamDB.DbProxy;

public class C_Compiler implements Compiler {

	private String[] outputFile = null;
	private String inputPath = "";
	private String fileName = "";
	private String outputPath = "";

	
	public C_Compiler(String[] outputFile,String inputPath,String fileName,String outputPath){
		this.outputFile = outputFile;
		this.inputPath = inputPath;
		this.fileName = fileName;
		this.outputPath = outputPath;
	}
	

	@Override
	public Process compile(Runtime rt) throws IOException {
		// TODO Auto-generated method stub
		//如果檔案有標記@，代表上傳為多個檔案
		if(fileName.lastIndexOf("@") > 1) {
			String path, dbName="", StdID="", HWID="";
			int Findex, Lindex;
			
			//將路徑、資料、學號、作業編號進行切割
			Findex = inputPath.lastIndexOf("WEB-INF") + 8;
			path = (String) inputPath.subSequence(0, Findex);	
			
			Findex = fileName.indexOf("@");
			Lindex = fileName.lastIndexOf("@");
			dbName = (String) fileName.subSequence(Findex+1, Lindex);
			
			Findex = Lindex;
			Lindex = fileName.indexOf("_");
			StdID = (String) fileName.subSequence(Findex+1, Lindex);
			
			Findex = Lindex;
			Lindex = fileName.indexOf("_", Lindex+1);
			HWID = (String) fileName.subSequence(Findex+1, Lindex);		

			//將相關資訊組合
			String ProgramSetPath = path + "ProgramSet" + File.separator + dbName + File.separator +
					                HWID + File.separator + StdID + File.separator;
			
			System.out.println("-------上傳程式數量-------");
			System.out.println(ProgramSetPath);
			//根據目前上傳的資料夾數目照出最後一次上傳的紀錄
			File f = new File(ProgramSetPath);
			String numberOfupload = Integer.toString(f.listFiles().length);
			System.out.println("file number : " + numberOfupload );
			System.out.println("\n---------編譯開始---------");
			//將記錄拼回path中
			ProgramSetPath = ProgramSetPath + numberOfupload + File.separator;
			
			
			String[] cmd = new String[3];
			cmd[0] = "/bin/sh";
			cmd[1] = "-c";
			//利用*.c編譯所有的.c file
			cmd[2] = "gcc " + ProgramSetPath +"*.c" + " -lm -o " + outputPath + outputFile[0];	
			
			System.out.println("\nExecing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
			
			return rt.exec(cmd);
			
		}else {
			String[] cmd = new String[3];
			cmd[0] = "/bin/sh";
			cmd[1] = "-c";
			cmd[2] = "gcc " + inputPath + fileName + " -lm -o " + outputPath + outputFile[0];
					
			System.out.println("\nExecing " + cmd[0] + " " + cmd[1] + " " + cmd[2]);
			
			return rt.exec(cmd);
		}

	}
}
