package JPD.structure;

import java.util.Vector;

import JPD.winnowing.WinnowingAlgorithm;

public class StructAlgorithm {
    private Vector DCSForect1;
    private Vector DCSForect2;
    private int kgrmSize;
    private int interval;
    private int windowSize;
    private String algorithm;

    private double similarity = -1.0;
    public StructAlgorithm(Vector forect1, Vector forect2, String algorithm,
                           int kgrmSize, int interval,int windowSize) {
        this.DCSForect1 = forect1;
        this.DCSForect2 = forect2;
        this.algorithm = algorithm;
        this.kgrmSize = kgrmSize;
        this.windowSize = windowSize;
        this.interval = interval;
    }

    public double excute() {
        DCSTree tree1, tree2;
        WinnowingAlgorithm winnowingAlgorithm = new WinnowingAlgorithm(this.
                algorithm, this.kgrmSize, this.interval ,this.windowSize);
        double tempSimilarity = 0.0d;
        double structureSimilarity = 0.0d;
        double result;
        int forest1Size = this.DCSForect1.size();
        int forest2Size = this.DCSForect2.size();
        for (int i = 0; i < forest1Size; i++) {
            result = 0.0d;
            tree1 = (DCSTree)this.DCSForect1.get(i);
            for (int j = 0; j < forest2Size; j++) {
                tree2 = (DCSTree)this.DCSForect2.get(j);
                winnowingAlgorithm.steInput(tree1.getExpression(),
                                            tree2.getExpression());
                tempSimilarity = winnowingAlgorithm.excute();

                if (tempSimilarity > result) {
                    result = tempSimilarity;
                }
            }
            structureSimilarity += result;
        }

        structureSimilarity /= forest1Size;

        similarity = structureSimilarity;
        return similarity;
    }

    public double getSimilarity() {
        return similarity;
    }
}
