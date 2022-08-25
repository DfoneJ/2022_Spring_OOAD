package JPD.winnowing;

/*
 CampareFingerPrint物件提供指紋值比對,輸入兩枚指紋,在執行(excute method),即可的到相似度
 */
public class CompareFingerPrint {
    private double similarity = -1.0; //相似度
    private FingerPrint fingerPrint1; //第一枚指紋
    private FingerPrint fingerPrint2; //第一枚指紋

    public CompareFingerPrint(FingerPrint fp1, FingerPrint fp2) {
        this.fingerPrint1 = fp1;
        this.fingerPrint2 = fp2;
    }

    /*
         執行指紋比對並取得相似度
     */
    public double excute() {
        boolean[] isMatch1 = new boolean[this.fingerPrint1.size()];
        boolean[] isMatch2 = new boolean[this.fingerPrint2.size()];
        campare(this.fingerPrint1, this.fingerPrint2, isMatch1);
        campare(this.fingerPrint2, this.fingerPrint1, isMatch2);
        similarity = (countPercentage(isMatch1) + countPercentage(isMatch2)) /
                     2;
        return similarity;
    }

    /*
         比對兩枚指紋:一次比對兩個指紋值,相同的話在比對兩個指紋值相減數值相同的話一次比對出兩個相同的指紋值
         isMatch陣列在紀錄指紋是相同,相同為true.後面countPercentage method根據isMatch陣列計算相似度
     */
    private void campare(FingerPrint fp1, FingerPrint fp2, boolean[] isMatch) {
        int fp1Size = fp1.size() - 1; //第一枚要比對指紋總指紋值數量,-1是為了比對連續兩枚,避免下面for溢位
        int fp2Size = fp2.size() - 1; //第二枚要比對指紋總指紋值數量,-1是為了比對連續兩枚,避免下面for溢位
        int k = 0;

        if (fp1Size == 0 && fp2Size == 0) {
            if (fp1.elementAt(0).getHashValue() ==
                fp2.elementAt(0).getHashValue()) {
                isMatch[0] = true;
            } else {
                isMatch[0] = false;
            }
        } else {
            for (int i = 0; i < fp1Size; i++) {
                for (int j = k; j < fp2Size; j++) {
                    if ((fp1.elementAt(i).getHashValue() ==
                         fp2.elementAt(j).getHashValue()) &&
                        (fp1.elementAt(i + 1).getHashValue() ==
                         fp2.elementAt(j + 1).getHashValue())) {
                        if ((fp1.elementAt(i + 1).getIndex() -
                             fp1.elementAt(i).getIndex()) ==
                            (fp2.elementAt(j + 1).getIndex() -
                             fp2.elementAt(j).getIndex())) {
                            k = i + 1;
                            isMatch[i] = true;
                            isMatch[i + 1] = true;
                            break;
                        }
                    }
                }
            }
        }
    }

    /*
         取的比對後的相似度,若未比對傳回-1
     */
    public double GetSimilarity() {
        return similarity;
    }

    /*
         計算相似度
     */
    private double countPercentage(boolean[] isMatch) {
        double denominator, numerator;
        double counter = 0;
        denominator = isMatch.length;
        for (int i = 0; i < denominator; i++) {
            if (isMatch[i] == true) {
                counter++;
            }
        }
        numerator = counter;
        return (numerator / denominator);
    }
}
