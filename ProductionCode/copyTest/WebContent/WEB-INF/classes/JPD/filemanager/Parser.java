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
    private int nextDirection;          //�U�@�Ӹ`�I��m,�O�p��(�U���`�I),�٬O�S��(�k��`�I)
    private Vector method;              //�����R�{��method�϶�
    private final int child = 0;        //�w�q�p��(�U���`�I) as child�ܼ�
    private final int rightSibling = 1; //�S��(�k��`�I)as rightSibling�ܼ�
    public Parser() {
        tree = new DCSTree();
        nextDirection = 1;               //�U�@�Ӹ`�I��m,��l�Ƭ��S��(�k��`�I)
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

    //�}�l����{��
    public void run() {
        String line = (String)this.method.remove(0);
        line = parse(line);
        this.tree.modify();
        this.tree.preorderTraversal();
    }

    /*���Rmethod�϶��禡,�C���yvector method����Nremove����
      �b���禡���|�h���R�U�Ӭ���������.���ѤΣ����Ʀ�,�÷|�I�s�U�����禡,
      �禡�j���O�i�洡�J�`�I,�÷|�b�I�s���禡.
     */
    public String parse(String line) {
        line = line.trim();

        while (line != null) {
            line = line.trim();

            if (line.equals("")) { //�ˬd�{�e�˵��{���X(��)�O�_���ť�

                if (this.method.size() != 0) { //�p�Gvector method�٦����,remove�ثe�ťճo�@��,�p�G�S�����,�]line=null���{������
                    line = (String)this.method.remove(0);
                } else {
                    line = null;
                }
            } else if (line.charAt(0) == '/') { //�ˬd�ثe��ƬO�_������,���Ѥ���//��/* */���
                if (line.charAt(1) == '/') {
                    line = parseComment(line, 1);
                } else if (line.charAt(1) == '*') {
                    line = parseComment(line, 2);
                }
            } else if (line.startsWith("if (")) { //�ˬd�ثe��ƬO�_�� If ����
                line = parseIfElse(line);
            } else if (line.startsWith("for (")) { //�ˬd�ثe��ƬO�_�� for �j��
                line = parseFor(line);
            } else if (line.equals("do")) { //�ˬd�ثe��ƬO�_�� do-while �j��
                line = parseDoWhile(line);
            } else if (line.startsWith("switch (")) { //�ˬd�ثe��ƬO�_��switch����
                line = parseSwitch(line);
            } else if (line.equals("}")) { //�ˬd�ثe��ƬO�_"}"�Ÿ�,�ت��b�ˬd�U����O�_�w�g����
                return line;
            } else if (line.startsWith("} while (")) { //�ˬd�ثe��ƬO�_} while (�Ÿ�,�ت��b�ˬddo-while �j��O�_�w�g����
                return line;
            } else if (line.equals("} else")) { //�ˬd�ثe��ƬO�_} else�Ÿ�,�ت��b�ˬdif���󦡪���L����
                return line;
            } else if (line.equals("break;")) { //�ˬd�ثe��ƬO�_break�Ÿ�,���O����switch���R
                return line;
            } else if (line.startsWith("case ")) { //�ˬd�ثe��ƬO�_case�Ÿ�,���O����switch���R
                return line;
            } else if (line.startsWith("default:")) { //�ˬd�ثe��ƬO�_default�Ÿ�,���O����switch���R
                return line;
            } else if (line.equals("continue;")) { //�ˬd�ثe��ƬO�_break�Ÿ�,���O����for���R
                return line;
            } else if (line.equals("return;")) { //�ˬd�ثe��ƬO�_return�Ÿ�
                return line;
            } else {
                line = parseCommand(line); //��������r�H�~��L���
            }
        }
        return line;
    }

    /*���R����:
      input :�ثe�檺���,�ε��Ѻ���-��(//)��(/*)���)   (//)��J��1 (/*)��2
           optput:�������Ѧ�ƫ�U�@���Ƥ��e
      */
     public String parseComment(String line, int type) {

         if (type == 1) { //���Ѭ� "//" ���������o�@��
             if (this.method.size() != 0) {
                 line = (String)this.method.remove(0);
             }
         } else if (type == 2) { //����/**/���Ҧ���r��

             int len;
             do {
                 if (this.method.size() != 0) {
                     line = ((String)this.method.remove(0)).trim();
                 }
                 len = line.length();
                 if (len >= 2) {
                     line = line.substring(len - 2, len).trim(); //�b�j���Ѥ�����u���@�Ӥ�rline.substring(0, 2)�|�X�{bug,�ҥH�Haa���N
                 } else {
                     line = "aa";
                 }
             } while (!line.equals("*/")); //���e������ line != "*/"�]����,�n��equals
         }
         return line.trim();
     }

    /*
     ���Rif-else�禡
     �����ץ���,�i���R�_���Φh��if-else����,���󦡵�����]����U�@�`�I�����S�̸`�I
     */
    public String parseIfElse(String line) {
        int count = 0;
        DCSNode fNode, sNode;
        fNode = sNode = null; //����F , S1 , S2 node , �᭱���j�ݭn�N�`�I�A��_�^��
        nextDirection = this.tree.getDirection(); //���X���W�@�`�I���@��V 0->child 1->right sibling
        this.tree.insertNode(line, "F", nextDirection); // insert "If" node
        this.tree.setChildConjunction("i"); //�U�@�`�IS��F���p��,��include(i)���
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
                        this.tree.insertNode("", "S", this.child); //�Ĥ@�ӧP�_��insert S node��F Node��child
                    } else {
                        this.tree.SetCurrentNode(sNode); //�ĤG�ӧP�_��insert S node���Ĥ@�� S node��sibling,�l����
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
        this.tree.SetCurrentNode(fNode); //�^�즹switch�Ĥ@��F Node
        this.tree.setDirection(this.rightSibling);
        if (this.method.size() != 0) { // return �U����{����C
            line = ((String)this.method.remove(0)).trim();
            return line;
        } else {
            return null;
        }
    }

    /*
         parse for loop:
         �o�Ө禡���ҹL,�S�����D
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
        this.tree.setDirection(this.rightSibling); //pointer �^�쥿�T���a��

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
        this.tree.setDirection(this.rightSibling); //pointer �^�쥿�T���a��

        if (this.method.size() != 0) { // return new line
            line = (String)this.method.remove(0);
            line = line.trim();
            return line;
        } else {
            return null;
        }
    }

    /*
         ���R����{����,���F�C�X���R�����󦡤�case�H�~
     */
    public String parseCommand(String line) {

        if (!line.startsWith("case")) { // ������case���󦨥�,insert B Node
            nextDirection = this.tree.getDirection();
            this.tree.insertNode(line, "B", nextDirection); //node �Ÿ���B
            this.tree.setRightSiblingConjunction("c");
            // t's childCounction = ""
            this.tree.setDirection(this.rightSibling);
        }

        if (this.method.size() != 0) { // return �U����{����C
            line = (String)this.method.remove(0);
            line = line.trim();
            return line;
        } else {
            return null;
        }
    }

    /*
         parse switch loop:
         ������g,�i�H�e�\�_��switc-case,�ӥBcase�����S��break�]�i�H���R�X��
     */
    public String parseSwitch(String line) {
        DCSNode fNode = null;
        DCSNode sNode = null;
        int breakCount = 1;
        int count = 0;
        boolean first = true;
        this.tree.insertNode(line, "F", this.tree.getDirection()); //insert F node ,Switch�i�H½Ķ��if-else����
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
                        this.tree.insertNode("", "S", this.child); //�Ĥ@�ӧP�_��insert S node��F Node��child
                    } else {
                        this.tree.SetCurrentNode(sNode); //�ĤG�ӧP�_��insert S node���Ĥ@�� S node��sibling,�l����
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
            this.tree.SetCurrentNode(fNode); //�^�즹switch�Ĥ@��F Node
            this.tree.setDirection(this.rightSibling);
        }
        if (this.method.size() != 0) { // return �U����{����C
            line = ((String)this.method.remove(0)).trim();
            return line;
        } else {
            return null;
        }
    }
}
