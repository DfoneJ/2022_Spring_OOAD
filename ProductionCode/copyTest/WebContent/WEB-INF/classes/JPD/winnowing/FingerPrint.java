package JPD.winnowing;

import java.security.MessageDigest;
import java.util.Vector;

/*------------------------------------------------------------------------------
 FingerPrintVa1ue:
 index      - the original position of hashvalue
 hashValue  - use hash method(TRD , TRDAD , SHA , MD5) to transfer string to hash value
 �����O�qn������Ȥ���̤p��,�ì������m,��m���o����.
 ------------------------------------------------------------------------------*/

class FingerPrintVa1ue {
    private int index;                                 //��m
    private int hashValue;                             //�����

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
 �ت��b�N�@���r�ഫ�������,�ñq���إ߫���
 ------------------------------------------------------------------------------*/
public class FingerPrint {
    private int hashValue[];         //��r���ഫ�L�{�|���إ������,�b�q����ȧ����
    private Vector fingerprint;      //����
    private MessageDigest msgDigest; //Java����,�i�ѭp�������
    private String algorithm;        //�i�������t��k
    private int ksize;               //�C�@������C�d��(K-Gram)
    private int interval;            //���K-Gram���������Z�r����
     private int windowSize;
    public FingerPrint() {}

    /*�غc��:
    �Ѽƨ̧Ǭ����B�z�r��,���ϥΤ��t��k,K-Gram�r����,���K-Gram���������Z�r����
    */
    public FingerPrint(String data, String algorithm, int ksize, int interval , int windowSize) {
        int len;
        int hashLen;
        this.algorithm = algorithm.toUpperCase();

        this.interval = interval;

        len = data.length();

        if (len - ksize < 0) { //��K-gram size���r��j��,�NK-gram size�Τ�r���Z���令��1
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
            stringToHashValue(data); //�N��l�r���নhash value
            hashvalueToFigerPrint(); //�Nhash value�ন����
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    �Q�Φr�����.kgram size�Φr�����Z,�D�ohash table����
    */
    private int getHashValueSize(int strlen, int kgram, int interval) {
        int size = (strlen - kgram) / interval + 1;
        return size;
    }

    /*
    ����ƥi�N�@��r���ഫ�إ������
    */
    private void stringToHashValue(String str) {
        int len;
        int key = 0;
        int count = 0;
        len = str.length() - ksize + 1;
       // String s = "";
        if (this.algorithm.equals("TRD")) { //TRD�t��k��K-gram�Ĥ@�Ӧr��%26���l��
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
        } else if (this.algorithm.equals("TRDAD")) { //TRDAD�t��k��K-gram�Ҧ��r���ۥ[%26���l��
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
    �Nhash value�ഫ������,�b�C����hash value����̤p�ȨðO�����m,��m���o����.���G�K�O����
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
            for (int j = 0; j < this.windowSize; j++) { //�o�@��hash value������̤p��
                if (hashValue[i + j] < minValue) {
                    index = i + j;
                    minValue = hashValue[index];
                }
            }
            if (index != lastIndex) { //���̤p�Ȥ����m�O�_����,�S�����ƧY��������
                fingerprint.add(new FingerPrintVa1ue(index, minValue));
                lastIndex = index;
              // s += "["+ minValue + " , " +index+ "]  ";
            }
        }
       //  System.out.println(s);
    }

    /*
    �Hindex��X����
    */
    public FingerPrintVa1ue elementAt(int index) {
        FingerPrintVa1ue fp = null;
        if (fingerprint.size() > index) {
            fp = (FingerPrintVa1ue) fingerprint.elementAt(index);
        }
        return fp;
    }

    /*
    �ثe�����ƶq
    */
    public int size() {
        return fingerprint.size();
    }
}
//------------------------------------------------------------------------------
