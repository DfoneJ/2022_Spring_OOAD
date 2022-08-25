package VariableAnalyze;

import java.util.Vector;

/*
 建立變數表,傳入lineVector(檔案每ㄧ行字串組成)及layerVector(檔案每ㄧ行字串階層
 最後,將lineVector及layerVector送到SymbolParser物件進行分析並傳回變數表.
 */
public class SymbolTableBuilder {
    private double[] result = new double[2];
    private int algorithm = 0; //winnowing algorithm
    private Vector lineVector;
    private Vector layerVector;

    /*
         建構子:
         lineVector:檔案每ㄧ行字串
         layerVector:檔案每ㄧ行字串階層
         algorithm:hash 演算法
     */
    public SymbolTableBuilder(Vector lineVector, Vector layerVector,
                              int algorithm) {
        this.lineVector = lineVector;
        this.layerVector = layerVector;
        setAlgorithm(algorithm);
    }

    /*
         設定hash演算法:有TRD,TRDAD,MD5,SHA
     */
    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    /*
         執行變數分析並建立分析表
     */
    public Vector excute() {
        SymbolParser symParser = new SymbolParser(this.lineVector,
                                                  this.layerVector);
        return symParser.getSymbolList();
    }
}
