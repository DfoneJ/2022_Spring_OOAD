package VariableAnalyze;

import java.io.*;
import java.util.Vector;
//import winnowing.StringFilter;

/*
Filer:�@��Filer Object�t�d�@���ɮ�
�غc�l�ѼƶǤJ�ɦW��,����excute method�u�@�p�U:
1.���y�ɮ�:(scanFile)
   �h�����Ѧ��,�C�@��method��J��method�e����,�ñN�Ҧ�method�e����J��method list.
   method list�᭱�N���ѵ��c���R����.
   �t�~,�]�N�C�@����O��JlineVector�e��,�ì����䶥�h�Ʃ�layerVector�e��,�ѫ᭱�إ߲Ÿ���ܥ�.
   Finally,�P�ɤ]�N�Ҧ���ƨæ�@�Ӥ�r��(fileContext),�@�����c���R����.
2.���Rmethod list:(parseMethodList)
   ���R�C�@��method,�����parser����|���Rmethod�ëإ�DCS Tree,�N���ѵ��c���R����.
3.�إ߲Ÿ���:(buildSymbolTable)
   �إ߲Ÿ���N���ܼƤ��R�ɨϥ�.
*/
public class Filer {
    private String filename;    //Filer���B�z�ɮת��W��
  //  private Vector methodList;  //��JFile���Ҧ�method��list
  //  private Parser parser;      //parser����i����parse method list�����Ҧ�method�ëإ�DCS Tree,�@��method�i�إߤ@��DCS Tree
    //private Vector DCSForest;   //�ɮת��Ҧ�method(DCS Tree)�զX���@�Ӵ˪L(DCSForest)
 //   private String fileContext; //�h�����ѫ᪺�ɮשҦ���r�ꦨfileContext
    private Vector lineVector;  //�h�����ѫ᪺�ɮשҦ���r���mlineVector,�ѫإ߲Ÿ���ܥ�.
    private Vector layerVector; //�h�����ѫ᪺�ɮשҦ���r�涥�h������mlayerVector,�ѫإ߲Ÿ���ܥ�.
    private Vector symbolTable; //�Ÿ���

    /*�غc�l
    input:�ɮצW��
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
    ���y�ɮ�,���Rmethod list and �إ߲Ÿ���
    */
    public void excute() {
        scanFile();
   //     parseMethodList();
        buildSymbolTable();
    }

    /*
    1.���y�ɮרä��R�C�@�Ӥ�k,�N�C�@�Ӥ�k��m�ӧO�e����(methodList),�N�@�����c���R
    2.�X���ɮפ�r���e,�N�������e���R����
    3.�إߤ�r��ζ��h�V�q,�@���ܼƤ��R����
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
                if (line.startsWith("//") || line.equals("")) { //�J��p���ѩ���,���UŪ�@��
                    line = br.readLine();
                } else if (line.startsWith("/*")) {             //��j���ѩ����᭱��r��,����J��(*/)�r�ꬰ��
                    do {
                        line = br.readLine();
                        if (line == null) {
                            break;
                        }
                        len = line.length();
                        if (len >= 2) {
                            line = line.substring(len - 2, len).trim(); //�b�j���Ѥ�����u���@�Ӥ�rline.substring(0, 2)�|�X�{bug,�ҥH�Haa���N
                        } else {
                            line = "aa";
                        }
                    } while (!line.endsWith("*/"));                  //���e������ line != "*/"�]����,�n��equals
                } else {
                    if (count >= 2) {                              //����R����ӥ��j�A��"{"��
                        if (line.startsWith("}")) {                //����R���k�j�A��"}"��,count��1
                            count--;
                            if (count > 1) {
                                method.add(line);
                            } else {
                                //methodList.add(method);             //�����󦨥߮�,���method block����,�N���method�[��method list�e��,�ò��ͥt�@��method�e����U�@��method
                                method = new Vector();
                            }
                        } else {
                            if (line.endsWith("{")) {
                                count++;
                            }
                            method.add(line);                        //��method���C�@��{���X��method�e��
                        }
                    } else {
                        if (line.startsWith("{")) {
                            count++;
                            if (count == 2) {                        //�Ĥ@��"{"��class���ĤG�Ӥ~�Omethod�����j�A��
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

                    if (line.endsWith("{")) {                           //�J�쥪�j�A����ܶ��h�V�U
                        layer++;
                    } else if (line.startsWith("}")) {                //�J�쥪�j�A����ܶ��h�V�W
                        layer--;
                    }

				//	System.out.println(line);
				//	System.out.println(layer);
                    lineVector.add(line);
                    layerVector.add(new Integer(layer));

                    line = br.readLine();
                  //  fileContext += stringFilter.trim(line);           //�Ҧ���r��ۥ[��fileContext��r��
                }
            }
            br.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    /*
    ���^�B�z���ɮפ��e
    */
    /*
    public String GetFileContext() {
        return this.fileContext;
    }*/

    /*
    ���R�C�@��method,�����parser����|���Rmethod�ëإ�DCS Tree
    */
   /*
    private void parseMethodList() {
        int len = methodList.size();
        Vector method;
        for (int i = 0; i < len; i++) {
            method = (Vector) methodList.elementAt(i);
            parser.setMethod(method);
            parser.run();
            DCSForest.add(parser.getTree()); //�N���R�o�쪺DCS Tree��JDCSForest
        }
    }*/

    /*
    ���oDCSForest
    */
    /*
    public Vector getDCSForect() {
        return this.DCSForest;
    }*/

    /*
    �إ߲Ÿ���
    */
    private void buildSymbolTable() {
        SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder( lineVector, layerVector, 0);
        this.symbolTable = symbolTableBuilder.excute();
    }

    /*
    ���o�Ÿ���
    */
    public Vector getSymbolTable() {
        return this.symbolTable;
    }
}
