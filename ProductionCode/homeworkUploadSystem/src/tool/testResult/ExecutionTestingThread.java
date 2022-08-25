package tool.testResult;

import java.io.IOException;
import java.lang.reflect.Field;

public class ExecutionTestingThread extends Thread{
		Process process;
	    public boolean lock = true;

		public ExecutionTestingThread(Process process) {
			this.process = process;
		}
		
		 
		public void run() {
	    	try {    
	    		
				process.waitFor();
				lock = false;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Thread.currentThread().interrupt();
				
				
			}
				
		}  
		
		public Process getProcess() {
			return process;
		}
}
