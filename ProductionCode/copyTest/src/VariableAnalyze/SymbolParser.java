package VariableAnalyze;

import java.util.Vector;
import java.util.regex.Pattern;

/*
 建立變數表,對輸入lineVector(檔案每ㄧ行字串組成)及layerVector(檔案每ㄧ行字串階層)
 進行分析並建立symbolList(變數表)
 */
public class SymbolParser {
    private Vector symbolList = new Vector(); //list of all symbols
    private Vector strVector; //stores the lines of the program to be parsed
    private Vector layerVector; //a corresponding vector to strVector that indicates code layers.

    /**
     * Default constructor for SymbolParser.
     *
     * @param strVector the source code stored in Vector with each element
     * being a line of code.
     * @param layerVector the corresponding layer (in context of the entire code)
     * of the line.
     */
    public SymbolParser(Vector strVector, Vector layerVector) {
        this.strVector = strVector;
        this.layerVector = layerVector;
        excute();
    }

    /**
     * Executes in order the set of methods needed to parse the file.
     */
    private void excute() {
        parseSymbol();
    }

    /**
     * Parses the pre-processed source code Vector to produce a symbol table.
     *
     * @return the symbol table for this file.
     */
    private Vector parseSymbol() {
        String testLine = "", keyType = "", varName = ""; //line to be tested, data type, variable name, respectively.
        boolean isLocal = false; //boolean check for global variable
        String[] lineSplit; //array for storing the String tokens.
        int type = -1; //data type identifier
        int cLevel = 0, nLevel = 0; //current line level, next line level, respectively.
        String commonForm = "";

        for (int lineNum = 0; lineNum < strVector.size() - 1; lineNum++) { //processing entire code by line.
            int nextL = lineNum + 1; //下ㄧ行數編號
            cLevel = getLayer(lineNum); //此行階層
            nLevel = getLayer(nextL); //下ㄧ行階層
            testLine = (String) strVector.get(lineNum);
            testLine = testLine.replace(';', ' '); //remove the semicolons.
            testLine = testLine.replace('(', ' '); //remove the left parentheses.
            testLine = testLine.replace(')', ' '); //remove the right parentheses.
            lineSplit = testLine.split(" "); //以空白切割字串
            if (lineSplit.length == 0) { //parsing lines with only 1 block after split.
                lineSplit = new String[1];
                lineSplit[0] = testLine;
            }
            SymbolNode newNode = null; //initialize a node for temp storage.

	//System.out.println(lineNum +" : "+ lineSplit[0]);		
            type = getDataType(lineSplit[0]);
            if (type >= 1 && type <= 10) { //1-9為目前定義的標準符號,check if a datatype is in front of the line, signifying a declaration.
                varName = lineSplit[1];
                isLocal = isLocal(lineNum);
                newNode = new SymbolNode(varName, type, isLocal); //將變數資訊(變數名稱,型態,是否為區域變數)丟給SymbolNode
                symbolList.add(newNode);
                newNode.setStats('a', commonForm);
				//System.out.println("11111111111");	
            } else if ((lineSplit[0].equals("private") ||
                        lineSplit[0].equals("public")) //變數宣告型態前有可能有下列幾種private,public,protected,static,final , 新增protected判斷
                       || lineSplit[0].equals("protected") ||
                       lineSplit[0].equals("static") ||
                       lineSplit[0].equals("final")) {
                if (nLevel <= cLevel) {
                    for (int i = 0; i < lineSplit.length; i++) {
                        type = getDataType(lineSplit[i]);
                        if (type >= 1 && type <= 9) {
                            varName = lineSplit[i + 1]; //變數名稱
                            isLocal = isLocal(lineNum); //檢查是不是區域變數
                            newNode = new SymbolNode(varName, type, isLocal); //產生新的變數節點
                            symbolList.add(newNode);
                            newNode.setStats('a', commonForm);
				//System.out.println("22222222222");			
                        }
                    }
                }
            } else if (Pattern.matches(".*\\+\\+.*", lineSplit[0])) { //when ++ is concatenated with a variable name.
                int[] indices = findOprndIndex(lineSplit[0], '+');
                varName = lineSplit[0].substring(indices[0], indices[1]);
                newNode = searchSymbol(varName);
                if(newNode != null)  // exception
                {
                    newNode.setStats('n', commonForm);
                }
            } else if (Pattern.matches(".*\\-\\-.*", lineSplit[0])) { //when -- is concatenated with a variable name.
                int[] indices = findOprndIndex(lineSplit[0], '-');
                varName = lineSplit[0].substring(indices[0], indices[1]);
                newNode = searchSymbol(varName);
                if(newNode != null) // exception
                {
                    newNode.setStats('d', commonForm);
                }
            } else if (lineSplit[0].equals("for")) { //for loops
                commonForm = commonForm.concat("f");
            } else if (lineSplit[0].equals("do")) { //do-while loops
                commonForm = commonForm.concat("w");
            }
            else if (lineSplit[0].equals("if") || lineSplit[0].equals("else") ||
                     lineSplit[0].equals("case") ||
                     lineSplit[0].equals("default")) { //if-structure
                commonForm = commonForm.concat("i");
            } else {
                //checks for assignment operation that begins with a variable name.
                newNode = searchSymbol(lineSplit[0]);
                if (newNode != null &&/*add by hongzhan*/lineSplit.length>1/*add by hongzhan*/) {
                    if (lineSplit[1].equals("+=") || lineSplit[1].equals("-=") ||
                        lineSplit[1].equals("*=") || lineSplit[1].equals("/=") ||
                        lineSplit[1].equals("%=")) {
                        newNode.setStats('e', commonForm);
                    } else if (lineSplit[1].equals("=")) {
                        String finalExpr = "";
                        for (int index = 2; index < lineSplit.length; index++) {
                            finalExpr = finalExpr.concat(lineSplit[index]);
                        }
					
                        if (isExpression(finalExpr)) {									
                            newNode.setStats('e', commonForm);
                        } else {
                        //	System.out.println("3333333333");	
                            newNode.setStats('a', commonForm);
                        }
                    }
                }
            }
            for (int m = 1; m < lineSplit.length; m++) {
                for (int n = 0; n < symbolList.size(); n++) {
                    newNode = (SymbolNode) symbolList.get(n);

                    if (lineSplit[m].equals(newNode.getName())) {
                        newNode.setStats('r', commonForm);
                     //   System.out.println("44444444444");	                        
                    }
                }
            }
            if (nLevel > cLevel) { //if this layer contains the next.
                commonForm = commonForm.concat("(");
            } else if (nLevel < cLevel) { //if the next layer wraps this layer.
                if (commonForm.length() > 0) {
                    commonForm = commonForm.substring(0,
                            commonForm.length() - 1);
                    int last = commonForm.lastIndexOf("(");
                    if (last != -1) {
                        commonForm = commonForm.substring(0, last + 1);
                    }
                }
            }
            //System.gc();
        }
        return symbolList;
    }

    /**
     * Checks whether the input String matches to a type keyword, namely "int",
     * "long", "double", "short", "float", "byte", "char", "String", and
     * "boolean".
     *
     * @param typeBlock  the input String to be checked.
     * @return an int identifying the type of this String block.
     * <br/>(int = 1; long = 2; double = 3; short = 4; float = 5; byte = 6; char =
     * 7; String = 8; boolean = 9.)
     */
    public int getDataType(String typeBlock) {
        int dataType = -1;
        if (typeBlock.equals("int")) {
            dataType = 1;
        } else if (typeBlock.equals("long")) {
            dataType = 2;
        } else if (typeBlock.equals("double")) {
            dataType = 3;
        } else if (typeBlock.equals("short")) {
            dataType = 4;
        } else if (typeBlock.equals("float")) {
            dataType = 5;
        } else if (typeBlock.equals("byte")) {
            dataType = 6;
        } else if (typeBlock.equals("char")) {
            dataType = 7;
        } else if (typeBlock.equals("string")) {
            dataType = 8;
        } else if (typeBlock.equals("boolean")) {
            dataType = 9;
        } else if (typeBlock.equals("Scanner")) {
            dataType = 10;
        }
        return dataType;
    }

    /**
     * Searches for the corresponding symbol node in the symbol table Vector.
     *
     * @param sname the name of the target node in String.
     * @return the symbol node in SymbolNode structure.
     */
    private SymbolNode searchSymbol(String sname) {
        SymbolNode sn = null;
        int index = 0; //index for searching Symbol List.
        if (symbolList.size() > 0) {
            do {
                sn = (SymbolNode) symbolList.get(index);
                if (sn.getName().equals(sname)) {
                    break;
                } else {
                    sn = null;
                }
                index++;
            } while (index < symbolList.size());
        }
        return sn;
    }

    /**
     * Checks whether a variable is local by accepting the line number where
     * this variable is declared.
     *
     * @param lineNum  the line number where the variable is declared.
     * @return true if the variable is a local variable, or false if it is a
     * class attribute.
     */
    private boolean isLocal(int lineNum) {
        boolean isLocal;
        if (getLayer(lineNum) == 1) {
            isLocal = false;
        } else {
            isLocal = true;
        }
        return isLocal;
    }

    /**
     * Returns the layer where this line, identified by its line number,
     * resides in context of the source code.
     *
     * @param lineNum the line number to be checked.
     * @return the corresponding layer in <i>int</i>.
     */
    public int getLayer(int lineNum) {
        Integer intObj = (Integer) layerVector.get(lineNum);
        return intObj.intValue();
    }

    /**
     * Returns the constructed symbol table.
     *
     * @return the completed symbol table in Vector object.
     */
    public Vector getSymbolList() {
        return this.symbolList;
    }

    /**
     * Returns the variable index and its end index attached to increment ("++")
     * or decrement ("--") operators as specified.
     *
     * @param str  the String where the variable is present.
     * @param opr  the operator that needs to be separated, namely "++" or "--".
     * @return  an int array that stores the begin index (of the variable String)
     * at position 0, and the end index at position 1.
     */
    private int[] findOprndIndex(String str, char opr) {
        int[] indices = { -1, -1};
        char[] tokens = str.toCharArray(); //字串變成字元陣列
        Character newChar;
        for (int pos = 0; pos < tokens.length; pos++) {
            newChar = new Character(tokens[pos]);
            if (indices[0] == -1 && newChar.isJavaIdentifierStart(tokens[pos])) { //isJavaIdentifierStart:判斷是否為數字或英文字母 ,operator AFTER variable
                indices[0] = pos;
            }
            if (tokens[pos] == opr && tokens[++pos] == opr) { //假如連續兩個字元為 ++或--
                if (indices[0] != -1) { //indices[0]變數開始位置
                    indices[1] = --pos; //indices[1]變數結尾位置
                } else { //operator BEFORE variable
                    indices[0] = ++pos;
                    indices[1] = tokens.length; //之前tokens.length-1有問題,變數名稱字元少一個
                }
                break;
            }
        }
        return indices; //returns the index of the ++ or -- operator with index of identifier.
    }

    /**
     * Checks whether a String object is a valid mathematical expression.
     *
     * @param expr the String object to be checked.
     * @return true if String is a valid mathematical expression, false if not.
     */
    private boolean isExpression(String expr) {
        boolean isExpr;
        if (expr.indexOf("+") == -1 && expr.indexOf("-") == -1 &&
            expr.indexOf("*") == -1 && expr.indexOf("/") == -1 &&
            expr.indexOf("%") == -1) {
            isExpr = false;
        } else {
            isExpr = true;
        }
        return isExpr;
    }
}
