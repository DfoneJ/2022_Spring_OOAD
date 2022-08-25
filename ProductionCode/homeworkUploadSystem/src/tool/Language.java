package tool;

import tool.compiler.CSCompiler;
import tool.compiler.C_Compiler;
import tool.compiler.Compiler;
import tool.compiler.JavaCompiler;
import tool.compiler.PathData;
import tool.compiler.PythonCompiler;
import tool.testResult.CSTestResult;
import tool.testResult.CTestResult;
import tool.testResult.JavaTestResult;
import tool.testResult.PythonTestResult;
import tool.testResult.CompilationConfig;
import tool.testResult.TestResult;

public class Language {
	private String language = "";
	public Language(String language){
		this.language = language;
	}
	
	public Compiler BuildCompiler(PathData path) {
		if(language.equals("C")) {
			return new C_Compiler(
					path.getOutputFile(),
					path.getInputPath(),
					path.getFileName(),
					path.getOutputPath());
		}else if(language.equals("Python")) {
			return new PythonCompiler(
					path.getInputPath(),
					path.getFileName(),
					path.getOutputPath());
		}else if(language.equals("Java")){
			return new JavaCompiler(
					path.getPackageName(),
					path.getInputPath(),
					path.getFileName(),
					path.getOutputPath(),
					path.getOldfileName());
		}else if(language.equals("C#")) {
			return new CSCompiler(
					path.getOutputFile(),
					path.getInputPath(),
					path.getFileName(),
					path.getOutputPath());
		}else {
			return null;
		}
		
	}
	//only c and python has container,container name most use db
	public TestResult setBuildConfig(CompilationConfig result) {
		if(language.equals("c")) {
			return new CTestResult(
					result.getStudentID(),
					result.getOutputFile(),
					result.getInputData(),
					result.getOutputPath(),
					result.getDb());
		}else if(language.equals("py")) {		
			return new PythonTestResult(
					result.getStudentID(),
					result.getOutputFile(),
					result.getInputData(),
					result.getOutputPath(),
					result.getDb());
		}else if(language.equals("java")){
			return new JavaTestResult(
					result.getOutputFile(),
					result.getPackageName(),
					result.getInputData(),
					result.getOutputPath());
		}else if(language.equals("cs")) {
			return new CSTestResult(
					result.getOutputFile(),
					result.getInputData(),
					result.getOutputPath());
		}else {
			return null;
		}
	}
}
