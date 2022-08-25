package JPD.winnowing;

/*
 CampareFingerPrint���󴣨ѫ����Ȥ��,��J��T����,�b����(excute method),�Y�i����ۦ���
 */
public class CompareFingerPrint {
    private double similarity = -1.0; //�ۦ���
    private FingerPrint fingerPrint1; //�Ĥ@�T����
    private FingerPrint fingerPrint2; //�Ĥ@�T����

    public CompareFingerPrint(FingerPrint fp1, FingerPrint fp2) {
        this.fingerPrint1 = fp1;
        this.fingerPrint2 = fp2;
    }

    /*
         ����������è��o�ۦ���
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
         ����T����:�@������ӫ�����,�ۦP���ܦb����ӫ����Ȭ۴�ƭȬۦP���ܤ@�����X��ӬۦP��������
         isMatch�}�C�b���������O�ۦP,�ۦP��true.�᭱countPercentage method�ھ�isMatch�}�C�p��ۦ���
     */
    private void campare(FingerPrint fp1, FingerPrint fp2, boolean[] isMatch) {
        int fp1Size = fp1.size() - 1; //�Ĥ@�T�n�������`�����ȼƶq,-1�O���F���s���T,�קK�U��for����
        int fp2Size = fp2.size() - 1; //�ĤG�T�n�������`�����ȼƶq,-1�O���F���s���T,�קK�U��for����
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
         �������᪺�ۦ���,�Y�����Ǧ^-1
     */
    public double GetSimilarity() {
        return similarity;
    }

    /*
         �p��ۦ���
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
