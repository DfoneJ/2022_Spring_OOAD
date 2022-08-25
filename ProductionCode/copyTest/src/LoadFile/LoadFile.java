package LoadFile;

import java.io.*;
import java.util.*;

public class LoadFile {

	private Vector compareFileContainer;

	public ArrayList<ArrayList<String>> LoadArrayOfFile(String path) throws IOException
	{
		int ch;
		ArrayList<ArrayList<String>> fileArray = new ArrayList<ArrayList<String>>();
		File file = new File(path);
		File[] files = file.listFiles();
		
		String tmpFileString = "";

		for(int i = 0; i < files.length; i++) 
		{                       
			if(file.listFiles()[i].isFile()&&filterFile(file.listFiles()[i].getName()))
			{ 
				ArrayList<String> object = new ArrayList<String>();
				File tmpFile = new File(files[i].getPath());
				FileReader input = new FileReader(tmpFile);

				// ricehuang


				while((ch = input.read()) != -1)
			    {
					tmpFileString = tmpFileString + (char)ch;
			    }
				object.add(tmpFile.getName());
				object.add(tmpFileString);
				// ricehuang
				object.add(files[i].getPath());
				// ricehuang
				fileArray.add(object);
				tmpFileString = "";
// ricehuang
			//	filer = new Filer(files[i].getPath());
            		//filer.excute();
            		//	compareFileContainer.add(filer);	
// ricehuang						
			}           
		}                  
		                
		/*for( int i = 0; i < fileArray.size(); i++)
		{                   
			System.out.println(fileArray.get(i).get(1));  
			System.out.println();
		}*/
		
		return fileArray;
	}
	
	// ricehuang	
	public Vector GetFiler()	
	{
		return this.compareFileContainer;
	}
	// ricehuang
	
	//henryhuang
	private boolean filterFile(String name)
	{
		ArrayList<String> extensionFileName = new ArrayList<String>();
		extensionFileName.add("c");
		extensionFileName.add("cpp");
		extensionFileName.add("py");
		name = name.substring(name.lastIndexOf(".")+1).toLowerCase();
		
		for(int i=0;i<extensionFileName.size();i++)
		{
			if(extensionFileName.get(i).equals(name))
			{
				return true;
			}
		}
		
		return false;
	}
	//henryhuang
}
