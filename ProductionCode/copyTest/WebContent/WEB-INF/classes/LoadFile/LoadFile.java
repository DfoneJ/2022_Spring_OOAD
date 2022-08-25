package LoadFile;

import java.io.*;
import java.util.*;

public class LoadFile {

	private Vector compareFileContainer;

	public ArrayList<ArrayList<String>> LoadArrayOfFile(String path) throws IOException
	{
		int ch;
		ArrayList<ArrayList<String>> fileArray = new ArrayList<ArrayList<String>>();//存放file的array list
		File file = new File(path);//設定file的路徑
		File[] files = file.listFiles();//file內所有的檔案
		
		String tmpFileString = "";//用來暫存檔案的字串

		for(int i = 0; i < files.length; i++) 
		{                       
			if(file.listFiles()[i].isFile()&&filterFile(file.listFiles()[i].getName()))//是否為檔案 
			{ 
				ArrayList<String> object = new ArrayList<String>();
				File tmpFile = new File(files[i].getPath());
				FileReader input = new FileReader(tmpFile);

				// ricehuang


				//把file內容轉成String
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
		// 列出檔案                 
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
