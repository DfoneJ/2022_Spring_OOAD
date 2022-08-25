package JPD.winnowing;

import java.security.MessageDigest;
import java.util.Vector;

/*------------------------------------------------------------------------------
 FingerPrintVa1ue:
 index      - the original position of hashvalue
 hashValue  - use hash method(TRD , TRDAD , SHA , MD5) to transfer string to hash value
 指紋是從n個雜湊值中找最小值,並紀錄其位置,位置不得重複.
 ------------------------------------------------------------------------------*/

class FingerPrintVa1ue {
    private int index;                                 //位置
    private int hashValue;                             //雜湊值

    public FingerPrintVa1ue() {
    }

    public FingerPrintVa1ue(int index, int hashValue) {
        this.index = index;
        this.hashValue = hashValue;
    }

    public void setHashValue(int hashValue) {
        this.hashValue = hashValue;
    }

    public int getHashValue() {
        return this.hashValue;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
/*------------------------------------------------------------------------------
 FingerPrint:
 Input  -  String(a-z,A-Z)
 Output -  FingerPrintVa1ue
 目的在將一串文字轉換成雜湊值,並從中建立指紋
 ------------------------------------------------------------------------------*/
public class FingerPrint {
    private int hashValue[];         //文字串轉換過程會先建立雜湊值,在從雜湊值找指紋
    private Vector fingerprint;      //指紋
    private MessageDigest msgDigest; //Java物件,可供計算雜湊值
    private String algorithm;        //可表示雜湊演算法
    private int ksize;               //每一串雜湊列範圍(K-Gram)
    private int interval;            //兩個K-Gram之間的間距字元數
     private int windowSize;
    public FingerPrint() {}

    /*建構值:
    參數依序為欲處理字串,欲使用之演算法,K-Gram字元數,兩個K-Gram之間的間距字元數
    */
    public FingerPrint(String data, String algorithm, int ksize, int interval , int windowSize) {
        int len;
        int hashLen;
        this.algorithm = algorithm.toUpperCase();

        this.interval = interval;

        len = data.length();

        if (len - ksize < 0) { //當K-gram size比文字串大時,將K-gram size及文字間距都改成為1
            this.interval = 1;
            this.ksize = 1;
            this.windowSize = 1;
        } else {
            this.ksize = ksize;
            this.windowSize = windowSize;
        }
        hashLen = getHashValueSize(len, this.ksize, this.interval);

        try {
            if (!algorithm.equals("TRD") && !algorithm.equals("TRDAD") ) {
                msgDigest = MessageDigest.getInstance(algorithm);
            }
            fingerprint = new Vector();
            hashValue = new int[hashLen];
            stringToHashValue(data); //將原始字串轉成hash value
            hashvalueToFigerPrint(); //將hash value轉成指紋
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    利用字串長度.kgram size及字元間距,求得hash table長度
    */
    private int getHashValueSize(int strlen, int kgram, int interval) {
        int size = (strlen - kgram) / interval + 1;
        return size;
    }

    /*
    此函數可將一般字串轉換建立雜湊表
    */
    private void stringToHashValue(String str) {
        int len;
        int key = 0;
        int count = 0;
        len = str.length() - ksize + 1;
       // String s = "";
        if (this.algorithm.equals("TRD")) { //TRD演算法取K-gram第一個字母%26之餘數
            for (int i = 0; i < len; i += this.interval) {
               // s += str.substring(i,i+ksize) + " ";
                hashValue[count] = str.charAt(i) % 126;
               // System.out.println(hashValue[count] );
                count++;
            }
          /*  s = "";
            for (int i = 0; i < hashValue.length-5; i++){
                s += "(";
                for(int j=i ; j<i+4 ; j++){
                    s +=  hashValue[j] + " , ";
                }
                s += ") ";
            }
            System.out.println(s);
            System.out.println("========");*/
        } else if (this.algorithm.equals("TRDAD")) { //TRDAD演算法取K-gram所有字母相加%26之餘數
            for (int i = 0; i < len; i += this.interval) {
                for (int j = 0; j < ksize; j++) {
                    hashValue[count] += str.charAt(i + j);
                }
                hashValue[count] %= 126;
                count++;
            }
        } else { //MD5 , SHA
            try {
                String temp;
                for (int i = 0; i < len; i += this.interval) {
                    temp = str.substring(i, i + ksize + 1);
                    msgDigest.reset();
                    msgDigest.update(temp.toString().getBytes());
                    byte[] toDigest = msgDigest.digest();
                    for (int j = 0; j < toDigest.length; j++) {
                        hashValue[count] += toDigest[j] *
                                (Math.pow(Math.pow(2.0, 8.0),
                                          toDigest.length - 1 - j));
                    }
                    count++;
                }
            } catch (Exception e) {
            }

        }
    }

    /*
    將hash value轉換成指紋,在每ㄧ組hash value內找最小值並記錄其位置,位置不得重複.結果便是指紋
    */
    private void hashvalueToFigerPrint() {
        int minValue, index, lastMinValue, lastIndex;
        int size = hashValue.length - (ksize - 1);
        if (hashValue.length - this.ksize < 0) {
           // this.ksize = 1;
            this.windowSize = 1;
        }
        size = hashValue.length - this.windowSize + 1;
        lastMinValue = -1;
        lastIndex = -1;
       // String s = "";
        for (int i = 0; i < size; i++) {
            minValue = 65536;
            index = 0;
            for (int j = 0; j < this.windowSize; j++) { //這一組hash value內比對找最小值
                if (hashValue[i + j] < minValue) {
                    index = i + j;
                    minValue = hashValue[index];
                }
            }
            if (index != lastIndex) { //找到最小值比對其位置是否重複,沒有重複即成為指紋
                fingerprint.add(new FingerPrintVa1ue(index, minValue));
                lastIndex = index;
              // s += "["+ minValue + " , " +index+ "]  ";
            }
        }
       //  System.out.println(s);
    }

    /*
    以index找出指紋
    */
    public FingerPrintVa1ue elementAt(int index) {
        FingerPrintVa1ue fp = null;
        if (fingerprint.size() > index) {
            fp = (FingerPrintVa1ue) fingerprint.elementAt(index);
        }
        return fp;
    }

    /*
    目前指紋數量
    */
    public int size() {
        return fingerprint.size();
    }
}
//------------------------------------------------------------------------------
