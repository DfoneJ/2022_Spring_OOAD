package JPD.filemanager;

import java.util.Vector;

import JPD.structure.DCSNode;
import JPD.structure.DCSTree;

/*input: method
  output: DCS expression
  store a DCS tree to file as object
*/
public class Parser {
    private DCSTree tree;
    private int nextDirection;          //下一個節點位置,是小孩(下面節點),還是兄弟(右邊節點)
    private Vector method;              //欲分析程式method區塊
    private final int child = 0;        //定義小孩(下面節點) as child變數
    private final int rightSibling = 1; //兄弟(右邊節點)as rightSibling變數
    public Parser() {
        tree = new DCSTree();
        nextDirection = 1;               //下一個節點位置,初始化為兄弟(右邊節點)
    }

    // input is method block
    public Parser(Vector method) {
        this.tree = new DCSTree();
        this.method = method;
        this.nextDirection = 1;
    }

    // set input vector
    public void setMethod(Vector method) {
        this.method = method;
    }

    // return tree object
    public DCSTree getTree() {
        return tree;
    }

    //開始執行程式
    public void run() {
        String line = (String)this.method.remove(0);
        line = parse(line);
        this.tree.modify();
        this.tree.preorderTraversal();
    }

    /*分析method區塊函式,每掃描vector methodㄧ行就removeㄧ行
      在此函式中會去分析各個相關的條件式.註解及ㄧ般資料行,並會呼叫各相關函式,
      函式大都是進行插入節點,並會在呼叫本函式.
     */
    public String parse(String line) {
        line = line.trim();

        while (line != null) {
            line = line.trim();

            if (line.equals("")) { //檢查現前檢視程式碼(行)是否為空白

                if (this.method.size() != 0) { //如果vector method還有資料,remove目前空白這一行,如果沒有資料,設line=null讓程式結束
                    line = (String)this.method.remove(0);
                } else {
                    line = null;
                }
            } else if (line.charAt(0) == '/') { //檢查目前資料是否為註解,註解分成//及/* */兩種
                if (line.charAt(1) == '/') {
                    line = parseComment(line, 1);
                } else if (line.charAt(1) == '*') {
                    line = parseComment(line, 2);
                }
            } else if (line.startsWith("if (")) { //檢查目前資料是否為 If 條件式
                line = parseIfElse(line);
            } else if (line.startsWith("for (")) { //檢查目前資料是否為 for 迴圈
                line = parseFor(line);
            } else if (line.equals("do")) { //檢查目前資料是否為 do-while 迴圈
                line = parseDoWhile(line);
            } else if (line.startsWith("switch (")) { //檢查目前資料是否為switch條件式
                line = parseSwitch(line);
            } else if (line.equals("}")) { //檢查目前資料是否"}"符號,目的在檢查各條件是否已經結束
                return line;
            } else if (line.startsWith("} while (")) { //檢查目前資料是否} while (符號,目的在檢查do-while 迴圈是否已經結束
                return line;
            } else if (line.equals("} else")) { //檢查目前資料是否} else符號,目的在檢查if條件式的其他條件式
                return line;
            } else if (line.equals("break;")) { //檢查目前資料是否break符號,它是有關switch分析
                return line;
            } else if (line.startsWith("case ")) { //檢查目前資料是否case符號,它是有關switch分析
                return line;
            } else if (line.startsWith("default:")) { //檢查目前資料是否default符號,它是有關switch分析
                return line;
            } else if (line.equals("continue;")) { //檢查目前資料是否break符號,它是有關for分析
                return line;
            } else if (line.equals("return;")) { //檢查目前資料是否return符號
                return line;
            } else {
                line = parseCommand(line); //條件式關鍵字以外其他行數
            }
        }
        return line;
    }

    /*分析註解:
      input :目前行的資料,及註解種類-有(//)及(/*)兩種)   (//)輸入為1 (/*)為2
           optput:移除註解行數後下一行資料內容
      */
     public String parseComment(String line, int type) {

         if (type == 1) { //註解為 "//" 直接移除這一行
             if (this.method.size() != 0) {
                 line = (String)this.method.remove(0);
             }
         } else if (type == 2) { //移除/**/內所有文字行

             int len;
             do {
                 if (this.method.size() != 0) {
                     line = ((String)this.method.remove(0)).trim();
                 }
                 len = line.length();
                 if (len >= 2) {
                     line = line.substring(len - 2, len).trim(); //在大註解內ㄧ行只有一個文字line.substring(0, 2)會出現bug,所以以aa替代
                 } else {
                     line = "aa";
                 }
             } while (!line.equals("*/")); //之前直接用 line != "*/"也不對,要用equals
         }
         return line.trim();
     }

    /*
     分析if-else函式
     徹底修正後,可分析巢狀及多重if-else條件式,條件式結束後也讓其下一節點成為兄弟節點
     */
    public String parseIfElse(String line) {
        int count = 0;
        DCSNode fNode, sNode;
        fNode = sNode = null; //紀錄F , S1 , S2 node , 後面遞迴需要將節點再恢復回來
        nextDirection = this.tree.getDirection(); //取出位於上一節點哪一方向 0->child 1->right sibling
        this.tree.insertNode(line, "F", nextDirection); // insert "If" node
        this.tree.setChildConjunction("i"); //下一節點S為F的小孩,用include(i)表示
        this.tree.setRightSiblingConjunction("c");
        fNode = this.tree.getCurrentNode();

        if (this.method.size() != 0) { // line should be "{"
            line = ((String)this.method.remove(0)).trim();
        }

        if (line.equals("{")) {
            while (!line.equals("}")) {
                if (line.equals("{")) {
                    if (count == 0) {
                        this.tree.SetCurrentNode(fNode);
                        this.tree.insertNode("", "S", this.child); //第一個判斷式insert S node為F Node的child
                    } else {
                        this.tree.SetCurrentNode(sNode); //第二個判斷式insert S node為第一個 S node的sibling,餘類推
                        this.tree.insertNode("", "S", this.rightSibling);
                    }
                    count++;
                    this.tree.setRightSiblingConjunction("o");
                    this.tree.setChildConjunction("i");
                    this.tree.setDirection(this.child);
                    sNode = this.tree.getCurrentNode();
                    line = ((String)this.method.remove(0)).trim();
                    line = parse(line);
                } else {
                    line = ((String)this.method.remove(0)).trim();
                }
            }
            if (sNode != null) {
                this.tree.SetCurrentNode(sNode);
                this.tree.insertNode("", "L", this.rightSibling); // insert  lambda
            }
        }
        if (sNode != null) {
            this.tree.SetCurrentNode(sNode);
            this.tree.insertNode("", "L", this.rightSibling); // insert  lambda
        }
        this.tree.SetCurrentNode(fNode); //回到此switch第一個F Node
        this.tree.setDirection(this.rightSibling);
        if (this.method.size() != 0) { // return 下ㄧ行程式行列
            line = ((String)this.method.remove(0)).trim();
            return line;
        } else {
            return null;
        }
    }

    /*
         parse for loop:
         這個函式驗證過,沒有問題
     */
    public String parseFor(String line) {
        DCSNode wNode;
        wNode = null;
        line = line.trim();
        nextDirection = this.tree.getDirection(); // insert "W" node
        this.tree.insertNode(line, "W", nextDirection);
        // may be wrong here
        this.tree.setRightSiblingConjunction("c"); // set conjunction
        this.tree.setChildConjunction("i");
        wNode = this.tree.getCurrentNode();

        this.tree.insertNode("", "S", this.child); // insert "S" node
        // may be wrong
        this.tree.setRightSiblingConjunction("o"); // set conjunction
        this.tree.setChildConjunction("i");
        this.tree.setDirection(this.child);

        if (this.method.size() != 0) { //line should be {
            line = (String)this.method.remove(0);
            line = line.trim();
        }

        if (!line.equals("{")) { // empty loop, No third lambda
            nextDirection = this.tree.getDirection();
            this.tree.insertNode("", "L", nextDirection);
            //t's siblingConjunction = childConjunction = ""
            this.tree.setDirection(this.rightSibling);
            this.tree.goToParent();
            this.tree.insertNode("", "L", this.rightSibling); // insert second lambda
            //t's siblingConjunction = childConjunction = ""
            this.tree.goToParent();
            this.tree.setDirection(this.rightSibling);
            return line;
        } else {

            if (this.method.size() != 0) { // line should be parsed
                line = (String)this.method.remove(0);
                line = line.trim();
            }
        }
        line = parse(line); // line should be }
        if (this.tree.getCurrentNode().getParent() != null) {
            this.tree.goToParent();
        }

        this.tree.insertNode("", "L", this.rightSibling); // may be third lambda
        //t's siblingConjunction = childConjunction = ""
        /* if (this.tree.getCurrentNode().getParent() != null) {
             this.tree.goToParent();
         }*/
        this.tree.SetCurrentNode(wNode);
        this.tree.setDirection(this.rightSibling); //pointer 回到正確的地方

        if (this.method.size() != 0) {
            line = (String)this.method.remove(0);
            line = line.trim();
            return line;
        } else {
            return null;
        }
    }

    /*
         paere do-while loop
     */
    public String parseDoWhile(String line) {
        DCSNode rNode = null;
        line = line.trim();

        nextDirection = this.tree.getDirection();
        this.tree.insertNode(line, "R", nextDirection); // insert "R" node
        // may be wrong
        // correct conjunction
        this.tree.setRightSiblingConjunction("c");
        this.tree.setChildConjunction("i");
        rNode = this.tree.getCurrentNode();

        this.tree.insertNode("", "S", this.child); // insert first "S" node
        // correct conjunction
        //t's siblingConjunction = ""
        this.tree.setChildConjunction("i");
        this.tree.setDirection(this.child);

        if (this.method.size() != 0) { // line should be "{"
            line = (String)this.method.remove(0);
        }
        // str should be nextDirection
        if (this.method.size() != 0) {
            line = (String)this.method.remove(0);
        }
        line = parse(line); //  line should be }

        this.tree.SetCurrentNode(rNode);
        this.tree.setDirection(this.rightSibling); //pointer 回到正確的地方

        if (this.method.size() != 0) { // return new line
            line = (String)this.method.remove(0);
            line = line.trim();
            return line;
        } else {
            return null;
        }
    }

    /*
         分析ㄧ般程式行,除了列出分析的條件式及case以外
     */
    public String parseCommand(String line) {

        if (!line.startsWith("case")) { // 當不等於case條件成立,insert B Node
            nextDirection = this.tree.getDirection();
            this.tree.insertNode(line, "B", nextDirection); //node 符號為B
            this.tree.setRightSiblingConjunction("c");
            // t's childCounction = ""
            this.tree.setDirection(this.rightSibling);
        }

        if (this.method.size() != 0) { // return 下ㄧ行程式行列
            line = (String)this.method.remove(0);
            line = line.trim();
            return line;
        } else {
            return null;
        }
    }

    /*
         parse switch loop:
         徹底改寫,可以容許巢狀switc-case,而且case結束沒有break也可以分析出來
     */
    public String parseSwitch(String line) {
        DCSNode fNode = null;
        DCSNode sNode = null;
        int breakCount = 1;
        int count = 0;
        boolean first = true;
        this.tree.insertNode(line, "F", this.tree.getDirection()); //insert F node ,Switch可以翻譯成if-else條件式
        this.tree.setChildConjunction("i");
        this.tree.setRightSiblingConjunction("c");
        fNode = this.tree.getCurrentNode();

        if (this.method.size() >= 2) {
            line = (String)this.method.remove(0); // str should be "{"
            line = (String)this.method.remove(0);
            while (breakCount > 0) {
                if (line.startsWith("case ") || line.startsWith("default:")) {
                    if (count == 0) {
                        this.tree.SetCurrentNode(fNode);
                        this.tree.insertNode("", "S", this.child); //第一個判斷式insert S node為F Node的child
                    } else {
                        this.tree.SetCurrentNode(sNode); //第二個判斷式insert S node為第一個 S node的sibling,餘類推
                        this.tree.insertNode("", "S", this.rightSibling);
                    }
                    count++;
                    this.tree.setRightSiblingConjunction("o");
                    this.tree.setChildConjunction("i");
                    this.tree.setDirection(this.child);
                    sNode = this.tree.getCurrentNode();
                    line = ((String)this.method.remove(0)).trim();
                    line = parse(line);
                } else if (line.startsWith("}")) {
                    breakCount--;
                } else if (line.startsWith("{")) {
                    breakCount++;
                } else {
                    line = ((String)this.method.remove(0)).trim();
                }
            }
            if (sNode != null) {
                this.tree.SetCurrentNode(sNode);
                this.tree.insertNode("", "L", this.rightSibling); // insert second lambda
            }
        }
        if (sNode != null) {
            this.tree.SetCurrentNode(fNode); //回到此switch第一個F Node
            this.tree.setDirection(this.rightSibling);
        }
        if (this.method.size() != 0) { // return 下ㄧ行程式行列
            line = ((String)this.method.remove(0)).trim();
            return line;
        } else {
            return null;
        }
    }
}
