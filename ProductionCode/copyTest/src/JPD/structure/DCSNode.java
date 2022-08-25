package JPD.structure;


/*
 用DCSNode表示程式碼
 */

public class DCSNode {
    private String context = "";
    private String symbol = ""; // B,S(seq),F(if),W,R,T,K,L(lam)
    //include: i, continue: c, one of two: o lambda: lam, null: ""
    private String rightSiblingConjunction = "";
    private String leftSiblingConjunction = "";
    private String childConjunction = "";
    private DCSNode parent;
    private DCSNode child;
    private DCSNode rightSibling;
    private DCSNode leftSibling;
    public DCSNode(String context, String symbol) {
        this.context = context;
        this.symbol = symbol;
        this.parent = this.child = this.rightSibling = this.leftSibling = null;
    }

    //set parent of this node
    public void setParent(DCSNode parent) {
        this.parent = parent;
    }

    //get parent of this node
    public DCSNode getParent() {
        return this.parent;
    }

    //set child of this node
    public void setChild(DCSNode child) {
        this.child = child;
    }

    //get child of this node
    public DCSNode getChild() {
        return this.child;
    }

    //set right sibling of this node
    public void setRightSibling(DCSNode rightSibling) {
        this.rightSibling = rightSibling;
    }

    //get right sibling of this node
    public DCSNode getRightSibling() {
        return this.rightSibling;
    }

    //set right sibling of this node
    public void setLeftSibling(DCSNode leftSibling) {
        this.leftSibling = leftSibling;
    }

    //get Right Sibling of this node
    public DCSNode getLeftSibling() {
        return this.leftSibling;
    }

    //set context of this node
    public void setContext(String context) {
        this.context = context;
    }

    //get context of this node
    public String getContext() {
        return this.context;
    }

    //set symbol of this node
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    //get symbol of this node
    public String getSymbol() {
        return this.symbol;
    }

    public void setChildConjunction(String symbol) {
        this.childConjunction = symbol;
    }

    public String getChildConjunction() {
        return this.childConjunction;
    }

    public void setRightSiblingConjunction(String symbol) {
        this.rightSiblingConjunction = symbol;
    }

    public String getRightSiblingConjunction() {
        return this.rightSiblingConjunction;
    }

    public void setLeftSiblingConjunction(String symbol) {
        this.leftSiblingConjunction = symbol;
    }

    public String getLeftSiblingConjunction() {
        return this.leftSiblingConjunction;
    }

    //insert node that is the child of this node
    public void insertChild(DCSNode child) {
        this.child = child;
        child.parent = this;
    }

    //insert node that is the right sibling of this node
    public void insertRightSibling(DCSNode sibling) {
        this.rightSibling = sibling;
        sibling.leftSibling = this;
        sibling.parent = this.parent;
    }

    //insert node that is the left sibling of this node
    public void insertLeftSibling(DCSNode sibling) {
        this.leftSibling = sibling;
        sibling.rightSibling = this;
        sibling.parent = this.parent;
    }
}
