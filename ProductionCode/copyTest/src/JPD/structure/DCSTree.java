package JPD.structure;

public class DCSTree {
    private DCSNode root; //the top node
    private DCSNode currentNode;
    private int direction;
    private String expression = "";
    private String DCSExpression = "";
    public DCSTree() {
        root = currentNode = null;
    }

    public void insertNode(String context, String symbol, int type) { // type:0->child 1->right node 2->left node
        DCSNode node = new DCSNode(context, symbol);
        if (root == null) {
            root = node;
            currentNode = node;
        } else {
            if (type == 0) {
                currentNode.insertChild(node); //insert child
            } else if (type == 1) {
                currentNode.insertRightSibling(node); //insert right sibling
            } else if (type == 2) {
                currentNode.insertLeftSibling(node); //insert left sibling
            }
            currentNode = node;
        }
    }

    /*
         direction : the value is definited as following
        1
      2   4
        3
     */
    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return this.direction;
    }

    public DCSNode getCurrentNode() {
        return this.currentNode;
    }

    public void SetCurrentNode(DCSNode node) {
        this.currentNode = node;
    }

    public void goToParent() {
        this.currentNode = this.currentNode.getParent();
    }

    public void goToChild() {
        this.currentNode = this.currentNode.getChild();
    }

    public void goToRightSibling() {
        this.currentNode = this.currentNode.getRightSibling();
    }

    public void goToLeftSibling() {
        this.currentNode = this.currentNode.getLeftSibling();
    }

    //set conjunction of right sibling
    public void setRightSiblingConjunction(String symbol) {
        this.currentNode.setRightSiblingConjunction(symbol);
    }

    //set conjunction of left sibling
    public void setLeftSiblingConjunction(String symbol) {
        this.currentNode.setLeftSiblingConjunction(symbol);
    }

    //set conjunction of child
    public void setChildConjunction(String symbol) {
        this.currentNode.setChildConjunction(symbol);
    }

    public void preorderTraversal() {
        preorderHelper(root);
        DCSExpression = expression;
        expression = "";
    }

    private void preorderHelper(DCSNode node) {
        if (node == null) {
            return;
        }
        expression = expression + node.getSymbol();

        if (node.getChild() != null) {
            expression = expression + node.getChildConjunction() + "(";
            preorderHelper(node.getChild());
            expression = expression + ")";
        }
        expression = expression + node.getRightSiblingConjunction();
        preorderHelper(node.getRightSibling());
    }

    // modify tree
    // delete single seq and third lambda
    public void modify() {
        modifyHelper(root);
    }

    private void modifyHelper(DCSNode node) {
        DCSNode temp = node;
        DCSNode delete = node;
        DCSNode tempSibling = node;
        int childNumber = 0;
        if (node == null) {
            return;
        }
        // correct conjunction
        if (node.getChild() == null) {
            node.setChildConjunction("");
        }

        if (node.getRightSibling() == null) {
            node.setRightSiblingConjunction("");
        }
        // kill third lambda
        if (node.getSymbol().equals("F")) {
            if (findNumberOfChild(node) == 3) {
                delete = node.getChild(); // delete = first seq
                delete = delete.getRightSibling(); // delete = second seq
                temp = delete; // temp = second seq
                delete = delete.getRightSibling(); // delete = lambda
                delete.setParent(null);
                delete.setChild(null);
                delete.setRightSibling(null);
                delete.setLeftSibling(null);
                // correct pointer
                temp.setRightSibling(null);
                temp.setRightSiblingConjunction("");
                System.gc();
            }
            // no delete lambda but correct conjunction
            else {
                temp = node.getChild(); // temp = first seq
                if(temp!= null)
                {
                    temp = temp.getRightSibling(); // temp = second seq
                }
                if (temp != null) {
                    temp.setRightSiblingConjunction("");
                }
            }
        } else if (node.getSymbol().equals("S")) { // kill seq if seq only have one child
            if (findNumberOfChild(node) == 1) {
                delete = node; // delete = seq
                temp = delete.getChild(); // temp = seq's child
                // correct pointer
                temp.setParent(delete.getParent());
                temp.setRightSibling(delete.getRightSibling());
                temp.setLeftSibling(delete.getLeftSibling());
                temp.setRightSiblingConjunction(delete.
                                                getRightSiblingConjunction());
                // delete is first child of parent
                if (delete.getParent() != null) {
                    if (delete.getParent().getChild() == delete) {
                        delete.getParent().setChild(temp);
                    }
                }
                // correct sibling pointer of delete.leftSibling
                tempSibling = delete.getLeftSibling();
                if (tempSibling != null) {
                    tempSibling.setRightSibling(temp);
                }
                // correct leftSibling pointer of delete.sibling
                if (delete.getRightSibling() != null) {
                    delete.getRightSibling().setLeftSibling(temp);
                }
                // kill delete
                delete.setParent(null);
                delete.setRightSibling(null);
                delete.setLeftSibling(null);
                delete.setChild(null);
                node = delete = null;
                node = temp.getParent();
            }
        }
        // recursive call for modifing

        modifyHelper(node.getChild());
        modifyHelper(node.getRightSibling());

    }

    // return number of child node
    private int findNumberOfChild(DCSNode node) {
        DCSNode temp = node.getChild();
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.getRightSibling();
        }
        return count;
    }

    public String getExpression() {
        return DCSExpression;
    }
}
