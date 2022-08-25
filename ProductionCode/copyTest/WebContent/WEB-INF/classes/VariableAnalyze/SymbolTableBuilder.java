package VariableAnalyze;

import java.util.Vector;

/*
 �إ��ܼƪ�,�ǤJlineVector(�ɮרC����r��զ�)��layerVector(�ɮרC����r�궥�h
 �̫�,�NlineVector��layerVector�e��SymbolParser����i����R�öǦ^�ܼƪ�.
 */
public class SymbolTableBuilder {
    private double[] result = new double[2];
    private int algorithm = 0; //winnowing algorithm
    private Vector lineVector;
    private Vector layerVector;

    /*
         �غc�l:
         lineVector:�ɮרC����r��
         layerVector:�ɮרC����r�궥�h
         algorithm:hash �t��k
     */
    public SymbolTableBuilder(Vector lineVector, Vector layerVector,
                              int algorithm) {
        this.lineVector = lineVector;
        this.layerVector = layerVector;
        setAlgorithm(algorithm);
    }

    /*
         �]�whash�t��k:��TRD,TRDAD,MD5,SHA
     */
    public void setAlgorithm(int algorithm) {
        this.algorithm = algorithm;
    }

    /*
         �����ܼƤ��R�ëإߤ��R��
     */
    public Vector excute() {
        SymbolParser symParser = new SymbolParser(this.lineVector,
                                                  this.layerVector);
        return symParser.getSymbolList();
    }
}
