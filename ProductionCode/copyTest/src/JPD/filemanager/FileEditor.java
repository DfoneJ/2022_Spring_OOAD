package JPD.filemanager;
import java.io.*;
import java.util.regex.Pattern;
import java.util.Vector;
import java.lang.Object.*;
public class FileEditor{

    public static FilenameFilter filter(final String regex) {
        // Creation of anonymous inner class:
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        }; // End of anonymous inner class
    }
    private String directory;
    private Vector javaFilenameContainer = new Vector();
    private FileUtil fileUtil;
    /*
    輸入為要比對的java file目錄
    */
    public FileEditor(String directory) {
        int len = directory.length();
        this.directory = directory;
        if(!this.directory.substring(len-1 , len).equals("\\"))  this.directory += "\\";
        File file = new File(directory);
        String[] fileFist;
        fileFist = file.list(filter(".+.java"));
        for (int i = 0; i < fileFist.length; i++) {
            javaFilenameContainer.add(new String(fileFist[i]));
        }
        buildDirectory(this.directory+ "temp");
        this.fileUtil = new FileUtil();
    }

    /*
    將java file 轉成 class file
    */
    public void convertJavaToClass(){
        for(int i=0 ; i<javaFilenameContainer.size() ; i++){
            processJavaToClass((String)javaFilenameContainer.elementAt(i));
        }

    }


    private void processJavaToClass(String filename) {
        String fn = filename.substring(0 , filename.length()-5);
        String javaPath = this.directory  + filename;
        String classPath = this.directory +"temp\\" + fn + "\\" ;
        String javaPath2 = classPath  + filename;
        buildDirectory(classPath);
        String newFilename = getNewFileName(javaPath);

        if(!newFilename.equals("")) javaPath2 = classPath  + newFilename + ".java";
        this.fileUtil.copy(javaPath , javaPath2 , true);
        Process proc = null;
        try{
        		proc = Runtime.getRuntime().exec("cmd /C start javac " + javaPath2 + " -d " + classPath);
                proc.waitFor();
                proc.destroy();
          }
        catch(Exception ioe){
            ioe.printStackTrace();
        }
    }
    /*
    將class file 用 JAD 轉成 java,以統一檔案格式
    */
    public void convertClassToJad(){
        String javaFileName;
        File file;
        String[] fileFist;
        String jadPath;
        for(int i=0 ; i<javaFilenameContainer.size() ; i++){
            javaFileName =(String)javaFilenameContainer.elementAt(i);
            javaFileName = javaFileName.substring(0 , javaFileName.length()-5);
            jadPath = this.directory + "temp"+ "\\" + javaFileName+"\\";
            file = new File(jadPath);
            fileFist = file.list(filter(".+.class"));
            for (int j = 0; j < fileFist.length; j++) {
                processClassToJad(jadPath ,fileFist[j]);
            }
        }
    }

    private void processClassToJad(String path ,String filename) {
        String classFile = path + filename;
        Process proc = null;
        try{
                proc = Runtime.getRuntime().exec("cmd /C start jad.exe -o -b -noctor -space -d " + path + " " + classFile);
                proc.waitFor();
                proc.destroy();
            }
        catch(Exception ioe){
            ioe.printStackTrace();
        }
    }
    /*
    將暫存檔全部delete
    */
    private void deleteTempFile(){
        File dir = new File(this.directory+"temp");
        File temp;
        String list[] = dir.list();
        if(list != null)
        {
            for(int i = 0 ; i<list.length ; i++){
                temp = new File(list[0]);
                if(temp.isFile()){
                    temp.delete();
                }
            }
        }
    }

    public Vector GetErrorFileList(){
        Vector errorFileList = new Vector();
        String[] javaFileFist;
        String[] classFileFist;
        String javaFilename;
        String classFilename;
        boolean result;
        File javaFile = new File(this.directory);
        File classFile = new File(this.directory+"temp\\");
        javaFileFist = javaFile.list(filter(".+.java"));
        classFileFist = classFile.list(filter(".+.class"));
        for (int i = 0; i < javaFileFist.length; i++) {
            javaFilename = javaFileFist[i].substring(0,javaFileFist[i].length()-5);
            result = false;
            for(int j = 0; j < classFileFist.length; j++){
                classFilename =  classFileFist[j].substring(0,classFileFist[j].length()-6);
                if(javaFilename.equals(classFilename)){
                    result = true;
                    break;
                }
            }
            if(!result) errorFileList.add(new String(javaFilename));
        }
        return errorFileList;
    }

    /*
    建立temp資料夾準備放jad檔
    */
    private boolean buildDirectory(String directory){
        File dir = new File(directory);
        return dir.mkdir();
    }

    private String getNewFileName(String sourceFile){
        String filename = "";
        boolean rtnFindClassName = false;
        try {
            String line = "";
            int len;
            String[] lineSplit; //array for storing the String tokens.
            BufferedReader br;
            boolean findMain = false;
            br = new BufferedReader(new FileReader(new File(sourceFile)));
            line = br.readLine();
            int count=0;
            String str;
            while (line != null) {
                line = line.trim();
                if (line.startsWith("//")) { //遇到小註解忽略,往下讀一行
                    line = br.readLine();
                } else if (line.startsWith("/*")) {             //到大註解忽略後面行字串,直到遇到(*/)字串為止
                    do {
                        line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        len = line.length();
                        if (len >= 2) {
                            line = line.substring(len - 2, len).trim(); //在大註解內ㄧ行只有一個文字line.substring(0, 2)會出現bug,所以以aa替代
                        } else {
                            line = "aa";
                        }
                    } while (!line.endsWith("*/"));                  //之前直接用 line != "*/"也不對,要用equals

                }
                lineSplit = line.split(" "); //以空白切割字串
                len = lineSplit.length;
            //    if(!findMain){
                     if(len>=2){
                         count = 0;
                         for (int i = 0; i < len; i++) {
                             str = lineSplit[i];
                             if(str.equals("")) continue;
                             if(count == 0){
                                 if(str.equals("class")) count++;
                             }
                             else if(count == 1){
                                 int pos = str.indexOf("{");
                                 if(pos > -1) str = str.substring(0,pos);
                                 filename = str;
                              //   findMain = true;
                                 break;
                             }
                         }
                     }
             //    }
                 //else{
                     if(len>=4){
                         count = 0;
                         for (int i = 0; i < len; i++) {
                             str = lineSplit[i];
                             if(str.equals("")) continue;
                             if(count == 0){
                                 if(str.equals("public")) count++;
                             }
                             else if(count == 1){
                                 if(str.equals("static")) count++;
                             }
                             else if(count == 2){
                                 if(str.equals("void")) count++;
                             }
                             else if(count == 3){
                                  if(str.length()==4){
                                      if(str.equals("main")){
                                          rtnFindClassName = true;
                                          break;
                                      }
                                  }
                                  else if(str.length()>4){
                                      str = str.substring(0,5);
                                      if(str.equals("main(")){
                                          rtnFindClassName = true;
                                          break;
                                      }
                                 }
                             }
                         }
                     }
              //  }
                if(rtnFindClassName) break;
                if(line == null) break;
                line = br.readLine();
            }
            br.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        if(!rtnFindClassName) filename = "";
        return filename;
    }
}
