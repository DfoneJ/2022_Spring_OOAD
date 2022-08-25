package JPD.filemanager;

import java.util.Vector;
import JPD.structure.StructAlgorithm;
import JPD.symbol.CompareSymbol;
import JPD.winnowing.WinnowingAlgorithm;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import java.io.File;
import java.text.*;


/*
�N�Ҧ�������ɮץ[�JFileList,�ǥ�JAD�ഫ�P�@�榡��,�C�@�ɮץѨC�@Parser����i����R
�M��i���r.���c���ܼƤ��
*/
public class FileList {
    public static FilenameFilter filter(final String regex) {
        // Creation of anonymous inner class:
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        }; // End of anonymous inner class
    }

    private Vector filenameContainer = new Vector();
    private String directory;
    private Vector blackList = new Vector();
    private Vector compileErrorFileList = new Vector();
    private Vector textSimarity = new Vector();
    private Vector structureSimarity = new Vector();
    private Vector symbolSimarity = new Vector();
    private Vector simarityInfo = new Vector();
    private String algorithm;
    private int kgrmSize,interval , windowSize;
    private double textSimilarity , structureSimilarity , symbolSimilarity;
    private boolean textSelected ;
    private boolean structureSelected ;
    private boolean variableSelected ;

    public FileList() {
    }
    /*
    �غc�l:
    ��J��Ƨ�,��X�Ҧ����ɦW��java,�é�JfilenameContainer
    */
    public FileList(String directory, String algorithm, int kgrmSize, int interval , int windowSize, double textSimilarity , double structureSimilarity
                    , double symbolSimilarity , boolean textSelected , boolean structureSelected , boolean variableSelected) {
        this.directory = directory;
        this.algorithm = algorithm;
        this.kgrmSize = kgrmSize;
        this.interval = interval;
        this.windowSize = windowSize;
        this.textSimilarity = textSimilarity;
        this.structureSimilarity = structureSimilarity;
        this.symbolSimilarity = symbolSimilarity;
        this.textSelected = textSelected;
        this.structureSelected = structureSelected;
        this.variableSelected = variableSelected;
        int len = directory.length();
        if(!this.directory.substring(len-1 , len).equals("\\"))  this.directory += "\\";
        SearchJavaFile();
    }
    /*
    Add filename and return current position of container
    */
    public void add(String filename) {
        filenameContainer.add(new String(filename));
    }

    /*
    �N�Ҧ�java��compile��class,�A�Nclass�Q��jad�নjava��
    */
    private void SearchJavaFile(){
        File file = new File(this.directory);
        String[] fileFist;
        fileFist = file.list(filter(".+.java"));
        filenameContainer.clear();
        for (int i = 0; i < fileFist.length; i++) {
            filenameContainer.add(new String(fileFist[i]));
        }
    }

    /*
    Delete file by index,if index is larger than container size , then return -1.
    */
    public void delete(int index) {
        int size;
        size = filenameContainer.size();
        if (index >= size - 1) {
            return;
        }
        filenameContainer.remove(index);
    }

    /*
    Delete all files from container
    */
    public void deleteAll() {
        filenameContainer.removeAllElements();
    }

    /*
    ���o�Ҧ��ɮ��`��
    */
    public int length(){
        return filenameContainer.size();
    }

    public String getFilename(int index){
        String filename = "";
        int size = filenameContainer.size();
        if(index>=size) return filename;
        filename = (String) filenameContainer.elementAt(index);
        return filename.substring(0,filename.length()-5);
}


    /*
    ����ɮ׬ۦ���,��j��䤤�@�ӳ]�w�ȫK�C�J�¦W��
    */
    public void compare(int index){
        Vector compareFileContainer = new Vector(); //��諸java�ɮץi��class�ҥH�i�঳�n�X��filer�ݭn���,�γo��container�˰_��
        Vector tempFileContainer;
        int size = filenameContainer.size(); //���java�ƶq
        if (index >= size)
            return;
        Filer filer1, filer2; //��諸filer
        WinnowingAlgorithm winnowingAlgorithm;
        StructAlgorithm structAlgorithm;
        CompareSymbol symBinder;
        double result[] = new double[2]; //[0]:�έp�ۦ��� [1]:���h���c�ۦ���
        double textSim, structureSim, symbolSim; //��諸�T�Ӭۦ���
        String compareFileDir;
        String tempFileDir;
        String information;
        String tempInformation;

        compareFileDir = this.directory + "temp" + "\\" + getFilename(index) + "\\";
        File compareFile = new File(compareFileDir);
        File tempFile;
        String[] compareFileList;
        String[] tempFileList;
        int comapreFileLen;
        int tempFileLen;
        boolean addBlaclList;
        this.blackList.clear();
        this.compileErrorFileList.clear();
        this.textSimarity.clear();
        this.structureSimarity.clear();
        this.symbolSimarity.clear();

        compareFileList = compareFile.list(filter(".+.jad"));
        comapreFileLen = compareFileList.length;

        if (comapreFileLen == 0) {
            compileErrorFileList.add(getFilename(index));
        }

        for (int k = 0; k < comapreFileLen; k++) {
            filer1 = new Filer(compareFileDir + compareFileList[k]);
            filer1.excute();
            compareFileContainer.add(filer1);
        }

        NumberFormat nf = NumberFormat.getInstance();
        //nf.setMaximumIntegerDigits(1);
        nf.setMinimumFractionDigits(1);

        for (int i = 0; i < size; i++) {
            if (i == index)
                continue;
            tempFileDir = this.directory + "temp" + "\\" + getFilename(i) + "\\";
            tempFile = new File(tempFileDir);
            tempFileList = tempFile.list(filter(".+.jad"));
            tempFileLen = tempFileList.length;
            tempFileContainer = new Vector();
            for (int k = 0; k < tempFileLen; k++) {
                filer2 = new Filer(tempFileDir + tempFileList[k]);
                filer2.excute();
                tempFileContainer.add(filer2);
            }
            addBlaclList = false;
            information = "";

            for (int k = 0; k < comapreFileLen; k++) {

                filer1 = (Filer) compareFileContainer.elementAt(k);

                textSim = structureSim = symbolSim = 0;
                for (int j = 0; j < tempFileLen; j++) {
                    tempInformation = "";
                    filer2 = (Filer) tempFileContainer.elementAt(j);
                    if (this.textSelected) {
                        winnowingAlgorithm = new WinnowingAlgorithm(filer1.
                                GetFileContext(), filer2.GetFileContext(),
                                this.algorithm, this.kgrmSize, this.interval,
                                this.windowSize);
                        textSim = winnowingAlgorithm.excute();
                    }
                    if (this.structureSelected) {
                        structAlgorithm = new StructAlgorithm(filer1.getDCSForect(),
                                filer2.getDCSForect(), this.algorithm,
                                this.kgrmSize, this.interval, this.windowSize);
                        structureSim = structAlgorithm.excute();
                    }
                    if (this.variableSelected) {
                        symBinder = new CompareSymbol(filer1.getSymbolTable(),
                                                      filer2.getSymbolTable(),
                                                      this.algorithm, this.kgrmSize,
                                                      this.interval,
                                                      this.windowSize);
                        symbolSim = (symBinder.getStatsRatio() +
                                     symBinder.getFormsRatio()) / 2; //statstical similarity ,formational similarity
                        if (symbolSim < 0)
                            symbolSim = 0;
                    }

                    if (this.textSelected) {
                        addBlaclList = false;
                        if (textSim < this.textSimilarity)
                            continue;
                        else {
                            addBlaclList = true;
                            tempInformation += "T=" + String.valueOf(nf.format(textSim));
                        }
                    }
                    if (this.structureSelected) {
                        addBlaclList = false;
                        if (structureSim < this.structureSimilarity)
                            continue;
                        else {
                            addBlaclList = true;
                            if (!tempInformation.equals(""))
                                tempInformation += " , S=" +
                                        String.valueOf(nf.format(structureSim));
                            else
                                tempInformation += "S=" +
                                        String.valueOf(nf.format(structureSim));
                        }
                    }

                    if (this.variableSelected) {
                        addBlaclList = false;
                        if (symbolSim < this.symbolSimilarity)
                            continue;
                        else {
                            addBlaclList = true;
                            if (!tempInformation.equals(""))
                                tempInformation += " , V=" +
                                        String.valueOf(nf.format(symbolSim));
                            else
                                tempInformation += "V=" +
                                        String.valueOf(nf.format(symbolSim));
                        }
                    }

                    if (addBlaclList) {
                        this.textSimarity.add(String.valueOf(nf.format(textSim)));
                        this.structureSimarity.add(String.valueOf(nf.format(
                                structureSim)));
                        this.symbolSimarity.add(String.valueOf(nf.format(symbolSim)));
                       // this.blackList.add(getFilename(i));
                       tempInformation =  "         Class:"+compareFileList[k].substring(0,compareFileList[k].length()-4) + "-" +
                                      tempFileList[j].substring(0,tempFileList[j].length()-4) + " : (" + tempInformation +")";
                        information += tempInformation + "\r\n";
                    }
                 }
              }
              if (!information.equals(""))
              {
                  this.blackList.add(getFilename(i));
                  this.simarityInfo.add(information);
              }
        }
    }
    public Vector getSimarityInformation(){
        return this.simarityInfo;
    }
    public Vector getBlackList(){
        return this.blackList;
    }

    public Vector getEorrorFileList(){
        return this.compileErrorFileList;
    }

    public Vector getTextSimarity(){
        return this.textSimarity;
    }

    public Vector getStructureSimarity(){
        return this.structureSimarity;
    }

    public Vector getSymbolSimarity(){
        return this.symbolSimarity;
    }
}
