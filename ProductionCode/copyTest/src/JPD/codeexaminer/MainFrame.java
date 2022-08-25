package JPD.codeexaminer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import JPD.filemanager.*;
import JPD.winnowing.WinnowingAlgorithm;
import JPD.winnowing.StringFilter;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.Box;
import java.awt.Component;
import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JProgressBar;
import java.awt.GridLayout;

public class MainFrame extends JFrame {

    public static FilenameFilter filter(final String regex) {
        // Creation of anonymous inner class:
        return new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            public boolean accept(File dir, String name) {
                return pattern.matcher(new File(name).getName()).matches();
            }
        }; // End of anonymous inner class
    }

    private String hashAlgorithm = "TRD";
    JPanel contentPane;
    TitledBorder titledBorder1 = new TitledBorder("");
    JLabel labErrHomeWork = new JLabel();
    JLabel labPlagiary = new JLabel();
    Component glue1 = Box.createGlue();
    JList lstPlagiary = new JList();
    JScrollPane jScrollPane2 = new JScrollPane();
    JScrollPane jScrollPane3 = new JScrollPane();
    JTextArea txtPlagiary = new JTextArea();
    JTextArea textErrorFile = new JTextArea();
    JComboBox cmbAlgorithm = new JComboBox();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JButton btnCompare = new JButton();
    JButton btnJad = new JButton();
    JButton btnCompile = new JButton();
    JTextField txtKgramSize = new JTextField();
    JTextField txtWindowSize = new JTextField();
    JLabel jLabel1 = new JLabel();
    JLabel labInterval = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel labSetting = new JLabel();
    JPanel jPanel3 = new JPanel();
    JLabel labSimarityLimit = new JLabel();
    JTextField textStructureLimit = new JTextField();
    JTextField textVariableLimit = new JTextField();
    JTextField textSimarityLimit = new JTextField();
    JCheckBox chkText = new JCheckBox();
    JCheckBox chkStructure = new JCheckBox();
    JCheckBox chkVariable = new JCheckBox();
    JButton btnPath = new JButton();
    JTextField txtPath = new JTextField();
    JButton btnSaveInvalidFile = new JButton();
    JButton btnSavePlagiaryFile = new JButton();
    JLabel jLabel3 = new JLabel();
    JTextField txtInterval = new JTextField();
    public MainFrame() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(540, 480));
        setTitle("Code Police Officer");
        labErrHomeWork.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        labErrHomeWork.setForeground(new Color(0, 110, 0));
        labErrHomeWork.setText("Invalid File");
        labErrHomeWork.setBounds(new Rectangle(167, 46, 186, 15));
        labPlagiary.setFont(new java.awt.Font("Arial", Font.BOLD, 14));
        labPlagiary.setForeground(new Color(0, 110, 0));
        labPlagiary.setText("Plagiary File");
        labPlagiary.setBounds(new Rectangle(167, 237, 186, 15));
        lstPlagiary.setBounds(new Rectangle(223, 278, 287, 113));
        jScrollPane2.setBounds(new Rectangle(167, 67, 346, 156));
        jScrollPane3.setBounds(new Rectangle(167, 257, 345, 175));
        txtPlagiary.setText("");
        textErrorFile.setText("");
        cmbAlgorithm.setSelectedItem(labErrHomeWork);
        cmbAlgorithm.setBounds(new Rectangle(7, 21, 71, 23));
        cmbAlgorithm.addItemListener(new MainFrame_cmbAlgorithm_itemAdapter(this));
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setBounds(new Rectangle(8, 57, 129, 176));
        jPanel1.setLayout(null);
        jPanel2.setBorder(BorderFactory.createEtchedBorder());
        jPanel2.setBounds(new Rectangle(13, 339, 128, 94));
        jPanel2.setLayout(null);
        btnCompare.setBounds(new Rectangle(22, 63, 75, 23));
        btnCompare.setEnabled(false);
        btnCompare.setText("Compare");
        btnCompare.addActionListener(new MainFrame_btnCompare_actionAdapter(this));
        btnJad.setBounds(new Rectangle(23, 35, 74, 23));
        btnJad.setEnabled(false);
        btnJad.setText("JAD");
        btnJad.addActionListener(new MainFrame_btnJad_actionAdapter(this));
        btnCompile.setBounds(new Rectangle(23, 7, 73, 23));
        btnCompile.setText("Compile");
        btnCompile.addActionListener(new MainFrame_btnCompile_actionAdapter(this));
        txtKgramSize.setText("4");
        txtKgramSize.setBounds(new Rectangle(6, 66, 70, 20));
        txtWindowSize.setText("3");
        txtWindowSize.setBounds(new Rectangle(7, 147, 70, 20));
        jLabel1.setText("Hash Function");
        jLabel1.setBounds(new Rectangle(8, 6, 78, 15));
        labInterval.setText("Interval");
        labInterval.setBounds(new Rectangle(6, 90, 63, 15));
        jLabel2.setText("Window Size");
        jLabel2.setBounds(new Rectangle(7, 133, 63, 15));
        labSetting.setText("Winnowing Parameter");
        labSetting.setBounds(new Rectangle(11, 42, 115, 15));
        jPanel3.setBorder(BorderFactory.createEtchedBorder());
        jPanel3.setBounds(new Rectangle(12, 254, 129, 83));
        jPanel3.setLayout(null);
        labSimarityLimit.setText("Simarity Limit");
        labSimarityLimit.setBounds(new Rectangle(11, 237, 124, 15));
        textStructureLimit.setText("0.8");
        textStructureLimit.setBounds(new Rectangle(78, 29, 39, 20));
        textVariableLimit.setText("0.8");
        textVariableLimit.setBounds(new Rectangle(79, 56, 39, 20));
        textSimarityLimit.setText("0.8");
        textSimarityLimit.setBounds(new Rectangle(79, 4, 39, 20));
        chkText.setSelected(true);
        chkText.setText("Text");
        chkText.setBounds(new Rectangle(8, 1, 56, 23));
        chkStructure.setSelected(true);
        chkStructure.setText("Structure");
        chkStructure.setBounds(new Rectangle(8, 26, 67, 23));
        chkVariable.setSelected(true);
        chkVariable.setText("Variable");
        chkVariable.setBounds(new Rectangle(9, 55, 66, 23));
        btnPath.setBounds(new Rectangle(67, 12, 72, 23));
        btnPath.setText("Path");
        btnPath.addActionListener(new MainFrame_btnPath_actionAdapter(this));
        txtPath.setEditable(false);
        txtPath.setBounds(new Rectangle(168, 15, 343, 20));
        btnSaveInvalidFile.setBounds(new Rectangle(438, 42, 73, 23));
        btnSaveInvalidFile.setText("Save");
        btnSaveInvalidFile.addActionListener(new
                MainFrame_btnSaveInvalidFile_actionAdapter(this));
        btnSavePlagiaryFile.setBounds(new Rectangle(439, 231, 73, 23));
        btnSavePlagiaryFile.setText("Save");
        btnSavePlagiaryFile.addActionListener(new
                MainFrame_btnSavePlagiaryFile_actionAdapter(this));
        jLabel3.setText("K-gram Size");
        jLabel3.setBounds(new Rectangle(7, 48, 63, 15));
        txtInterval.setText("1");
        txtInterval.setBounds(new Rectangle(6, 106, 70, 20));
        contentPane.add(glue1);
        jPanel1.add(jLabel1);
        jPanel1.add(cmbAlgorithm);
        jPanel1.add(txtKgramSize);
        jPanel1.add(jLabel3);
        jPanel1.add(jLabel2);
        jPanel1.add(txtWindowSize);
        jPanel1.add(labInterval);
        jPanel1.add(txtInterval);
        jPanel2.add(btnCompile);
        jPanel2.add(btnCompare);
        jPanel2.add(btnJad);
        contentPane.add(jPanel3);
        contentPane.add(btnSaveInvalidFile);
        contentPane.add(btnPath);
        jPanel3.add(chkText);
        jPanel3.add(chkStructure);
        jPanel3.add(chkVariable);
        jPanel3.add(textStructureLimit);
        jPanel3.add(textSimarityLimit);
        jPanel3.add(textVariableLimit);
        contentPane.add(labSimarityLimit);
        contentPane.add(labSetting);
        contentPane.add(jPanel1);
        contentPane.add(jScrollPane2);
        contentPane.add(labErrHomeWork);
        contentPane.add(txtPath);
        contentPane.add(jScrollPane3);
        contentPane.add(labPlagiary);
        contentPane.add(btnSavePlagiaryFile);
        contentPane.add(jPanel2);
        jScrollPane3.getViewport().add(txtPlagiary);
        jScrollPane2.getViewport().add(textErrorFile);
        cmbAlgorithm.addItem(new String("TRD"));
        cmbAlgorithm.addItem(new String("TRDAD"));
        cmbAlgorithm.addItem(new String("MD5"));
        cmbAlgorithm.addItem(new String("SHA"));
    }


    public void btnCompile_actionPerformed(ActionEvent e) {

        String path = txtPath.getText();
        File f = new File(path);
        if(!f.exists()){
            JOptionPane.showMessageDialog(null, "Invaild directory","Message",JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.setEnabled(false);
        btnCompile.setEnabled(false);
        FileEditor codeComplier = new FileEditor(path);
        codeComplier.convertJavaToClass();
        btnCompile.setEnabled(true);
        btnJad.setEnabled(true);
        this.setEnabled(true);

       // WinnowingAlgorithm w = new WinnowingAlgorithm("A do run run run , a do run run" , "Do a run run,do a  run run" , "TRD",4, 1);
       // w.excute();
    }

    public void btnJad_actionPerformed(ActionEvent e) {
        String path = txtPath.getText();
        File f = new File(path);
        if(!f.exists()){
            JOptionPane.showMessageDialog(null, "Invaild directory","Message",JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.setEnabled(false);
        btnJad.setEnabled(false);
        FileEditor codeComplier = new FileEditor(path);
        codeComplier.convertClassToJad();
        btnCompare.setEnabled(true);
        this.setEnabled(true);
        btnJad.setEnabled(true);
    }

    public void btnCompare_actionPerformed(ActionEvent e) {
       // cmbAlgorithm.

       FileList fileList;
       Vector blackList, compileErrorFileList, textSimarity, structureSimarity,symbolSimarity , similarityInfo;
       double txtSimLimit ,structureSimLimit , symbolSimLimit;
       int kgramSize , interval , windowSize;
       boolean textSelected = chkText.isSelected();
       boolean structureSelected = chkStructure.isSelected();
       boolean variableSelected = chkVariable.isSelected();
       String information;
       int list = 0;
       String path = txtPath.getText();
       File f = new File(path);
       if(!f.exists()){
           JOptionPane.showMessageDialog(null, "Invaild directory","Message",JOptionPane.ERROR_MESSAGE);
           return;
       }
       this.setEnabled(false);
       btnCompare.setEnabled(false);
       kgramSize = Integer.valueOf(txtKgramSize.getText());
       interval = Integer.valueOf(txtInterval.getText());
       windowSize = Integer.valueOf(txtWindowSize.getText());
       txtSimLimit = Double.valueOf(textSimarityLimit.getText());
       structureSimLimit = Double.valueOf(textStructureLimit.getText());
       symbolSimLimit = Double.valueOf(textVariableLimit.getText());
       fileList = new FileList(path, this.hashAlgorithm ,kgramSize, interval, windowSize ,txtSimLimit,structureSimLimit, symbolSimLimit
                  ,textSelected , structureSelected ,variableSelected );

        int size = fileList.length();
        String filename;
        int len;
        Vector temp;
        textErrorFile.setText("");
        txtPlagiary.setText("");
        for (int i = 0; i < size; i++) {
            filename = fileList.getFilename(i);
            fileList.compare(i);
            blackList = fileList.getBlackList();
            compileErrorFileList = fileList.getEorrorFileList();
            textSimarity = fileList.getTextSimarity();
            structureSimarity = fileList.getStructureSimarity();
            symbolSimarity = fileList.getSymbolSimarity();
            similarityInfo = fileList.getSimarityInformation();
            len = compileErrorFileList.size();
            System.out.println(filename);
            for(int k=0 ; k<len ; k++){
               textErrorFile.append((String)compileErrorFileList.elementAt(k)+"\r\n");
            }
            len = blackList.size();
           // len = similarityInfo.size();

            if(len>0){
                txtPlagiary.append( "----------------------------------LIST " +Integer.toString(++list) + "----------------------------\r\n");
                txtPlagiary.append("SourceFile:" + filename + ":\r\n");
                for(int k=0 ; k<len ; k++){
                   txtPlagiary.append("DestinationFile-"+ Integer.toString(k)+ ":" + (String)blackList.elementAt(k)+"\r\n");
                   information = (String)similarityInfo.elementAt(k);
                   txtPlagiary.append(information);
                   //if(textSelected) txtPlagiary.append("Text="+(String)textSimarity.elementAt(k) + "  ");
                  // if(structureSelected) txtPlagiary.append("Structure="+(String)structureSimarity.elementAt(k) + "  ");
                  // if(variableSelected) txtPlagiary.append("Symbol="+(String)symbolSimarity.elementAt(k));

                 // txtPlagiary.append("\r\n");
               }
               txtPlagiary.append("----------------------------------------------------------------------\r\n");
            }
        }
        btnCompare.setEnabled(true);
        this.setEnabled(true);

    }

    public void cmbAlgorithm_itemStateChanged(ItemEvent e) {
        this.hashAlgorithm = (String)e.getItem();
    }

    public void btnPath_actionPerformed(ActionEvent e) {
        int result = 1;
        JFileChooser jc = new JFileChooser();
        jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        result = jc.showOpenDialog(null);
        if (result == jc.CANCEL_OPTION) return;
        txtPath.setText(jc.getSelectedFile().toString());
    }

    public void btnSaveInvalidFile_actionPerformed(ActionEvent e) {
        try
        {
            String path = txtPath.getText();
            int len = path.length();
            if (!path.substring(len - 1, len).equals("\\"))
                path += "\\";
            File f = new File(path + "InvalidFile.log");
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(textErrorFile.getText().getBytes());
        }catch(IOException ae)
        {
        }
    }

    public void btnSavePlagiaryFile_actionPerformed(ActionEvent e) {
        try
        {
            String path = txtPath.getText();
            int len = path.length();
            if (!path.substring(len - 1, len).equals("\\"))
                path += "\\";
            File f = new File(path + "PlagiaryFile.log");
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(txtPlagiary.getText().getBytes());
        }catch(IOException ae)
        {
        }
    }
}


class MainFrame_btnSaveInvalidFile_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_btnSaveInvalidFile_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSaveInvalidFile_actionPerformed(e);
    }
}


class MainFrame_btnSavePlagiaryFile_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_btnSavePlagiaryFile_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnSavePlagiaryFile_actionPerformed(e);
    }
}


class MainFrame_btnPath_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_btnPath_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnPath_actionPerformed(e);
    }
}


class MainFrame_cmbAlgorithm_itemAdapter implements ItemListener {
    private MainFrame adaptee;
    MainFrame_cmbAlgorithm_itemAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void itemStateChanged(ItemEvent e) {
        adaptee.cmbAlgorithm_itemStateChanged(e);
    }
}


class MainFrame_btnCompare_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_btnCompare_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCompare_actionPerformed(e);
    }
}


class MainFrame_btnJad_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_btnJad_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnJad_actionPerformed(e);
    }
}


class MainFrame_btnCompile_actionAdapter implements ActionListener {
    private MainFrame adaptee;
    MainFrame_btnCompile_actionAdapter(MainFrame adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.btnCompile_actionPerformed(e);
    }
}
