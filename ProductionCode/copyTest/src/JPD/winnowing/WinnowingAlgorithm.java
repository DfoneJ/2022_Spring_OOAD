package JPD.winnowing;

import java.util.Vector;

/* WinnowingAlgorithm
   Input : string
   Output: FingerPrint
 */
public class WinnowingAlgorithm {
   // private Vector fingerPrintBox = new Vector();
    private StringFilter stringFilter = new StringFilter();
    private double similarity = -1.0;
    private FingerPrint fingerPrint1;
    private FingerPrint fingerPrint2;
    private int kgrmSize;
    private int interval;
    private int windowSize;
    private String algorithm;

    public WinnowingAlgorithm() {
    }

    public WinnowingAlgorithm(String algorithm, int kgrmSize, int interval , int windowSize) {
        this.algorithm = algorithm;
        this.kgrmSize = kgrmSize;
        this.interval = interval;
        this.windowSize = windowSize;
    }

    public WinnowingAlgorithm(String data1, String data2, String algorithm,
                              int kgrmSize, int interval , int windowSize) {
        fingerPrint1 = new FingerPrint(stringFilter.trim(data1), algorithm,
                                       kgrmSize, interval , windowSize);
        fingerPrint2 = new FingerPrint(stringFilter.trim(data2), algorithm,
                                       kgrmSize, interval,windowSize );
    }

    /*
         input:比對的資料及演算法
     */
    public void steInput(String data1, String data2) {
        fingerPrint1 = new FingerPrint(stringFilter.trim(data1), this.algorithm,
                                       this.kgrmSize, this.interval ,this.windowSize);
        fingerPrint2 = new FingerPrint(stringFilter.trim(data2), this.algorithm,
                                       this.kgrmSize, this.interval ,this.windowSize);
    }

    /*
         移除之前指紋容器的所有指紋物件
     */
  /*  private void removeFingerPrintBox() {
        if (fingerPrintBox.size() > 0) {
            fingerPrintBox.clear();
        }
    }*/

    public double excute() {
        // CampareFingerPrint cfp = new CampareFingerPrint((FingerPrint)fingerPrintBox.elementAt(0),(FingerPrint)fingerPrintBox.elementAt(1));
        CompareFingerPrint cfp = new CompareFingerPrint(fingerPrint1,
                fingerPrint2);
        similarity = cfp.excute();
        return similarity;
    }

    public double getSimilarity() {
        return similarity;
    }

    public void print() {
        int size;
        FingerPrint fingerPrint = fingerPrint1;
        FingerPrintVa1ue fpValue;
        String str;
        size = fingerPrint.size();
        for (int i = 0; i < size; i++) {
            fpValue = fingerPrint.elementAt(i);
            str = "FP:[" + String.valueOf(fpValue.getIndex()) + "," +
                  String.valueOf(fpValue.getHashValue()) + "]";
            System.out.println(str);
        }
    }

    public static void main(String args[]) {
        // WinnowingAlgorithm winnowingAlgorithm = new WinnowingAlgorithm();
        //  winnowingAlgorithm.print();

    }
}
