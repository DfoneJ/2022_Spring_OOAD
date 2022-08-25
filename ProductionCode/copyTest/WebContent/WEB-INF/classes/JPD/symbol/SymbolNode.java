package JPD.symbol;

import java.util.Vector;

class BindedSymbolNode {
    private int statsSimNodeLoc;
    private double statsSim;
    private int formsSimNodeLoc;
    private double formsSim;

    protected BindedSymbolNode(int statsSimNodeLoc, double statsSim,
                               int formsSimNodeLoc, double formsSim) {
        this.statsSimNodeLoc = statsSimNodeLoc;
        this.statsSim = statsSim;
        this.formsSimNodeLoc = formsSimNodeLoc;
        this.formsSim = formsSim;
    }

    protected int getStatsSimNodeLoc() {
        return this.statsSimNodeLoc;
    }

    protected double getStatsSim() {
        return this.statsSim;
    }

    protected int getFormsSimNodeLoc() {
        return this.formsSimNodeLoc;
    }

    protected double getFormsSim() {
        return this.formsSim;
    }

    protected void setStatsSimNodeLoc(int statsSimNodeLoc) {
        this.statsSimNodeLoc = statsSimNodeLoc;
    }

    protected void setStatsSim(double statsSim) {
        this.statsSim = statsSim;
    }

    protected void setFormsSimNodeLoc(int formsSimNodeLoc) {
        this.formsSimNodeLoc = formsSimNodeLoc;
    }

    protected void setFormsSim(double formsSim) {
        this.formsSim = formsSim;
    }
}


/**
 * This class builds a symbol node, which is an entry to a symbol table.
 *
 * @author Louisa Chu 490512570, Project SCAN.
 * @version 1.0
 * @since 1.0
 */
public class SymbolNode {
    private String name; // name of the variable.
    /** data type of the variable, specifically: <br/>
     * 1 = int; 2 = long; 3 = double; 4 = short; 5 = float; 6 = byte; 7 = char;
     * 8 = String; 9 = boolean. */
    private int type;
    private boolean isLocal;       // a boolean that specifies whether this variable is local.
    private int[] stats;           // a statistical record of structural occurrences.
    private String currentForm;    // the structural formation of this SymbolNode object.
    private Vector forms;          // the Vector storing structural information of this SymbolNode object.
    private BindedSymbolNode bsn;  // the most similar node.

    /**
     * Empty constructor that builds a template of SymbolNode object.
     */
    public SymbolNode() {
        name = "";
        type = 0;
        isLocal = true;
    }

    /**
     * Builds a complete SymbolNode object with the input of name, data type, and
     * its global status.
     *
     * @param name  name of the variable in this symbol table entry.
     * @param type  data type of the variable.
     * @param isLocal  true if this variable is global, false if not.
     */
    public SymbolNode(String name, int type, boolean isLocal) {
        this.name = name; // name of the variable
        this.type = type; // data type of the variable
        this.isLocal = isLocal;
        stats = new int[8];
        forms = new Vector();
        this.bsn = new BindedSymbolNode( -1, -1, -1, -1);
    }

    /**
     * Returns the data type of this variable.<br/>
     * <b>(</b>
     * 1-<i>int</i>&nbsp;
     * 2-<i>long</i>&nbsp;
     * 3-<i>double</i>&nbsp;
     * 4-<i>short</i>&nbsp;
     * 5-<i>float</i>&nbsp;
     * 6-<i>byte</i>&nbsp;
     * 7-<i>char</i>&nbsp;
     * 8-<i>String</i>&nbsp;
     * 9-<i>boolean</i>
     * <b>)</b>
     *
     * @return the data type of the variablein <i>int</i>.
     */
    public int getDataType() {
        return this.type;
    }

    /**
     * Returns the structural formation of this symbol.
     *
     * @return the formation in String.
     */
    public Vector getForms() {
        return this.forms;
    }

    /**
     * Returns the highest formation structure similarity.
     *
     * @return the similarity in <i>double</i>, between 0 and 1.
     */
    public double getFormsSim() {
        return bsn.getFormsSim();
    }

    /**
     * Returns the structural formation at a particular location.
     *
     * @param pos the indicated position to retrive the formation.
     * @return the structure formation in String.
     */
    public String getStoredForm(int pos) {
        return (String)this.forms.get(pos);
    }

    /**
     * Returns the statistical information of this particular SymbolNode object.
     *
     * @return the statistical information in an <i>int</i> array.
     */
    public int[] getStats() {
        return stats;
    }

    public String getTypeStr(int type) {
        String typeStr = "";
        switch (type) {
        case 1:
            typeStr = "int";
            break;
        case 2:
            typeStr = "long";
            break;
        case 3:
            typeStr = "double";
            break;
        case 4:
            typeStr = "short";
            break;
        case 5:
            typeStr = "float";
            break;
        case 6:
            typeStr = "byte";
            break;
        case 7:
            typeStr = "char";
            break;
        case 8:
            typeStr = "String";
            break;
        case 9:
            typeStr = "boolean";
            break;
        default:
            typeStr = "unknown type";
            break;
        }
        return typeStr;
    }

    public String printStats() {
        String statsStr = "\n";
        for (int r = 0; r < stats.length; r++) {
            switch (r) {
            case 0:
                statsStr += " if (" + stats[r] + ")\t";
                break;
            case 1:
                statsStr += "for (" + stats[r] + ")\t";
                break;
            case 2:
                statsStr += "do-w (" + stats[r] + ")\t";
                break;
            case 3:
                statsStr += "increment (" + stats[r] + ")\n";
                break;
            case 4:
                statsStr += " decrement (" + stats[r] + ")\t";
                break;
            case 5:
                statsStr += "assignment (" + stats[r] + ")\t";
                break;
            case 6:
                statsStr += "expression (" + stats[r] + ")\t";
                break;
            case 7:
                statsStr += "referenced (" + stats[r] + ")";
                break;
            }
        }
        return statsStr;
    }

    /**
     * Returns the name of this symbol.
     *
     * @return the name of the symbol in String.
     */
    public String getName() {
        return this.name;
    }

    public int getSimNodeLoc() {
        return bsn.getStatsSimNodeLoc();
    }

    /**
     * Returns the highest statistical similarity.
     *
     * @return the similarity in <i>double</i>, between 0 and 1.
     */
    public double getStatsSim() {
        return bsn.getStatsSim();
    }

    /**
     * Checks if the new similarity result exceeds that of the previous ones.
     * If yes, the result will be renewed.
     *
     * @param sf  the identifying char for the type of similarity check.
     * ('s' for statistical, 'f' for formation)
     * @param loc  the location in a symbol node where the most similar node
     * resides.
     * @param sim  the actual degree of similarity, in <i>double</i> between 0
     * and 1.
     */
    public void checkSimNode(char sf, int loc, double sim) {
        switch (sf) {
        case 's':
            if (sim >= bsn.getStatsSim()) {
                bsn.setStatsSim(sim);
                bsn.setStatsSimNodeLoc(loc);
            }
            break;
        case 'f':
            if (sim >= bsn.getFormsSim()) {
                bsn.setFormsSim(sim);
                bsn.setFormsSimNodeLoc(loc);
            }
            break;
        default:
        }
    }

    /** Checks if the input formation is the same as the existing formation.
     *
     *  @param  newForm  the input formation to be checked.
     *  @return  true if the two formations are equal, false if not.
     */
    public boolean isEqual(String newForm) {
        boolean equals;
        if (newForm.equals(currentForm)) {
            equals = true;
        } else {
            equals = false;
        }
        return equals;
    }

    /**
     * Records a statistical table of this SymbolNode object.
     *
     * @param type the type of structure identified.
     * @param commonForm a common formation previous to this strucutre, if any.
     */
    public void setStats(char type, String commonForm) {
        if (!commonForm.equals(this.currentForm)) {
            currentForm = commonForm;
            forms.add(currentForm);
            char[] formChars = currentForm.toCharArray();
            for (int pos = 0; pos < formChars.length; pos++) {
                switch (formChars[pos]) {
                case 'i': //if-structure
                    stats[0]++;
                    break;
                case 'f': //for-loop
                    stats[1]++;
                    break;
                case 'w': //do-while loop
                    stats[2]++;
                    break;
                default:
                }
            }
        }
        switch (type) {
        case 'n': //increment
            stats[3]++;
            break;
        case 'd': //decrement
            stats[4]++;
            break;
        case 'a': //assignment operation
            stats[5]++;
            break;
        case 'e': //expression
            stats[6]++;
            break;
        case 'r': //referenced
            stats[7]++;
            break;
        default:
        }
    }

    /**
     * Prints out the content of a SymbolNode object.
     *
     * @return the content of the object in String.
     */
    public String toString() {
        String s = " Name: " + name;
        s += "\n Type: " + getTypeStr(type);
        s += printStats();
        s += "\n----------";
        return s;
    }
}
