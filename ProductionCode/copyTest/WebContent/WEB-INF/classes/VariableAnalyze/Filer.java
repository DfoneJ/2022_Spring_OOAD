package VariableAnalyze;

import java.io.*;
import java.util.Vector;
//import winnowing.StringFilter;

/*
Filer:一個Filer Object負責一個檔案
建構子參數傳入檔名後,執行excute method工作如下:
1.掃描檔案:(scanFile)
   去除註解行數,每一個method放入至method容器內,並將所有method容器放入至method list.
   method list後面將提供結構分析之用.
   另外,也將每一行分別放入lineVector容器,並紀錄其階層數於layerVector容器,供後面建立符號表示用.
   Finally,同時也將所有行數並串一個文字串(fileContext),作為結構分析之用.
2.分析method list:(parseMethodList)
   分析每一個method,執行後parser物件會分析method並建立DCS Tree,將提供結構分析之用.
3.建立符號表:(buildSymbolTable)
   建立符號表將供變數分析時使用.
*/
public class Filer {
    private String filename;    //Filer欲處理檔案的名稱
  //  private Vector methodList;  //放入File內所有method的list
  //  private Parser parser;      //parser物件可提供parse method list內的所有method並建立DCS Tree,一個method可建立一顆DCS Tree
    //private Vector DCSForest;   //檔案的所有method(DCS Tree)組合成一個森林(DCSForest)
 //   private String fileContext; //去除註解後的檔案所有文字串成fileContext
    private Vector lineVector;  //去除註解後的檔案所有文字行放置lineVector,供建立符號表示用.
    private Vector layerVector; //去除註解後的檔案所有文字行階層紀錄放置layerVector,供建立符號表示用.
    private Vector symbolTable; //符號表

    /*建構子
    input:檔案名稱
    */
    public Filer(String filename) {
        this.filename = filename;
       /* methodList = new Vector();
        parser = new Parser();
        DCSForest = new Vector();
        fileContext = "";*/
        lineVector = new Vector();
        layerVector = new Vector();
    }

    /*
    掃描檔案,分析method list and 建立符號表
    */
    public void excute() {
        scanFile();
   //     parseMethodList();
        buildSymbolTable();
    }

    /*
    1.掃描檔案並分析每一個方法,將每一個方法放置個別容器內(methodList),將作為結構分析
    2.合併檔案文字內容,將做為內容分析之用
    3.建立文字行及階層向量,作為變數分析之用
    */
    private void scanFile() {
        try {
            BufferedReader br;
            int count = 0;
            String line = "";
            String methodName = "";
            String temp = "";
            Vector method = new Vector();
          //  StringFilter stringFilter = new StringFilter();
            int len;
            int layer = 0;

         //   fileContext = "";
            br = new BufferedReader(new FileReader(new File(this.filename)));
            line = br.readLine();
            while (line != null) {

                line = line.trim();
                if (line.startsWith("//") || line.equals("")) { //遇到小註解忽略,往下讀一行
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
                } else {
                    if (count >= 2) {                              //當分析有兩個左大括號"{"時
                        if (line.startsWith("}")) {                //當分析有右大括號"}"時,count減1
                            count--;
                            if (count > 1) {
                                method.add(line);
                            } else {
                                //methodList.add(method);             //當此條件成立時,表示method block結束,將整個method加到method list容器,並產生另一個method容器放下一個method
                                method = new Vector();
                            }
                        } else {
                            if (line.endsWith("{")) {
                                count++;
                            }
                            method.add(line);                        //放method內每一行程式碼至method容器
                        }
                    } else {
                        if (line.startsWith("{")) {
                            count++;
                            if (count == 2) {                        //第一個"{"為class的第二個才是method的左大括號
                                methodName = temp;
                                method.add(methodName);
                            }
                        } else {
                            temp = line;
                            if (line.endsWith("}")) {
                                count--;
                            }
                        }
                    }

                    if (line.endsWith("{")) {                           //遇到左大括號表示階層向下
                        layer++;
                    } else if (line.startsWith("}")) {                //遇到左大括號表示階層向上
                        layer--;
                    }

				//	System.out.println(line);
				//	System.out.println(layer);
                    lineVector.add(line);
                    layerVector.add(new Integer(layer));

                    line = br.readLine();
                  //  fileContext += stringFilter.trim(line);           //所有行字串相加於fileContext文字串
                }
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /*
    取回處理後檔案內容
    */
    /*
    public String GetFileContext() {
        return this.fileContext;
    }*/

    /*
    分析每一個method,執行後parser物件會分析method並建立DCS Tree
    */
   /*
    private void parseMethodList() {
        int len = methodList.size();
        Vector method;
        for (int i = 0; i < len; i++) {
            method = (Vector) methodList.elementAt(i);
            parser.setMethod(method);
            parser.run();
            DCSForest.add(parser.getTree()); //將分析得到的DCS Tree放入DCSForest
        }
    }*/

    /*
    取得DCSForest
    */
    /*
    public Vector getDCSForect() {
        return this.DCSForest;
    }*/

    /*
    建立符號表
    */
    private void buildSymbolTable() {
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder( lineVector, layerVector, 0);
        this.symbolTable = symbolTableBuilder.excute();
    }

    /*
    取得符號表
    */
    public Vector getSymbolTable() {
        return this.symbolTable;
    }
}
