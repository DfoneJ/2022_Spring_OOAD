package VariableAnalyze;

import java.util.Vector;

//import winnowing.WinnowingAlgorithm;
//import java.util; 

/**
 將兩個建立好的變數表做比對求得相似度,相似度比對分成兩種
 1.統計資訊比對:變數在各種條件數量分佈比對.
 2.結構比對:轉成階層符號後由winnowing演算法比對.
 */
public class CompareSymbol {
    private double statsRatio; // the calculated result of comparing statistics of two files.
    private double formsRatio; //the calculated result of comparing formations of two files.
    private Vector symbolTable1; // the first symbol table to be compared.
    private Vector symbolTable2; // the second symbol table to be compared.
    private int kgrmSize; // the algorithm used for winnowing forms.
    private int interval;
    private int windowSize;
    private String algorithm;

    /*
         建構子:參數依序為第一個比對變數表,第二個比對變數表,hash演算法種類,kgram大小,字元間距
     */
    public CompareSymbol(Vector symbolTable1, Vector symbolTable2,
                        String algorithm, int kgrmSize, int interval , int windowSize) {
        this.symbolTable1 = symbolTable1;
        this.symbolTable2 = symbolTable2;
        this.algorithm = algorithm;
        this.kgrmSize = kgrmSize;
        this.interval = interval;
        this.windowSize = windowSize;
        
    //    System.out.println("CompareSymbol");
        
        excute();
    }

    /*
         執行比對功能
     */
    private void excute() {
        SymbolNode sn1, sn2;
        Vector tempTable1; // the first symbol table to be compared.
        Vector tempTable2; // the second symbol table to be compared.
        int sizeDecrease = 0;
        if(symbolTable1.size() >= symbolTable2.size()){
            tempTable1 = symbolTable1;
            tempTable2 = symbolTable2;
        }
        else{
            tempTable1 = symbolTable2;
            tempTable2 = symbolTable1;
        }

        for (int y = 0; y < tempTable1.size(); y++) {
            sn1 = (SymbolNode) tempTable1.get(y);
            for (int z = 0; z < tempTable2.size(); z++) {
                sn2 = (SymbolNode) tempTable2.get(z);
                bindStatsRatio(z, sn1, sn2); //統計資訊比對
                bindFormsRatio(z, sn1, sn2); //結構相似度比對
            }
          //  if(compared) sizeDecrease++;
            statsRatio += sn1.getStatsSim();
            formsRatio += sn1.getFormsSim();
        }
        if (symbolTable1.size() == 0) {
            statsRatio = -1;
            formsRatio = -1;
        } else {
            statsRatio = statsRatio / tempTable1.size();
            formsRatio = formsRatio / tempTable1.size();
        }
    }

    /*
         統計資訊比對相似度
     */
    public double getStatsRatio() {
        return statsRatio;
    }

    /*
         結構比對相似度
     */
    public double getFormsRatio() {
        return formsRatio;
    }

    public boolean CheckSymbolNode(SymbolNode symNode){
        boolean compared = false;
        int[] snStats;
        snStats = symNode.getStats();
        for (int i = 0; i < 8; i++) {

            if(i != 6 && snStats[i] != 0){
              compared = true;
            }
        }
        return compared;
    }
    /*
         比對統計資訊,求得相似度,之前除數皆為8,因為0出現機會高,導致相似度較高,反應事實較差
     */
    private void bindStatsRatio(int loc, SymbolNode symNode1,
                                SymbolNode symNode2) {
      //  double equalCount1 = 0, equalCount2 = 0 , sRatio = 0 , sRatio1 = 0 ,sRatio2 = 0;
        double equalCount = 0, sRatio = 0 ;
        int[] snStats1, snStats2;
        int divisor = 0;
       // System.out.println("bindStatsRatio");
       // System.out.println(divisor);
        if(symNode1.getDataType() != symNode2.getDataType()){
            sRatio = 0;
        }
        else{
            snStats1 = symNode1.getStats();
            snStats2 = symNode2.getStats();
            for (int i = 0; i < 8; i++) { //index 6->運算式(y = v + 5) , 7->被參考(y = v)
                if(snStats1[i] != 0 || snStats2[i] != 0){
                    divisor++;
                    if (snStats1[i] == snStats2[i]) {
                        equalCount++;
                    }
                }
            }
            //sRatio = equalCount / 8;

            sRatio = equalCount / divisor;
        }
        symNode1.checkSimNode('s', loc, sRatio);
    }

    /*
         比對結構文字,求得相似度
     */
    private void bindFormsRatio(int loc, SymbolNode symNode1,
                                SymbolNode symNode2) {
        double sim = 0;
        Vector forms1 = symNode1.getForms();
        Vector forms2 = symNode2.getForms();
        
        if(symNode1.getDataType() != symNode2.getDataType()){
            return;
        }
        //這邊寫死winnowing的參數.
      //  WinnowingAlgorithm winnowingAlgorithm = new WinnowingAlgorithm(this.
        //        algorithm, this.kgrmSize, this.interval ,this.windowSize);
        //Winnow winnow = new Winnow();
        String form1, form2;
        for (int i = 0; i < forms1.size(); i++) {
            form1 = (String) forms1.get(i);
           // System.out.println(form1);
            for (int j = 0; j < forms2.size(); j++) {
                form2 = (String) forms2.get(j);	
                //winnowingAlgorithm.steInput(form1, form2);
                // sim = winnowingAlgorithm.excute();
		//  winnow.textAnalyzeForStructure(form1, form2, 4);                
                sim = 0;
                if(form1.equals(form2)) sim = 1;
                symNode1.checkSimNode('f', loc, sim);
            }
        }
    }
}
