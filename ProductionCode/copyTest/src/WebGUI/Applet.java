package WebGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class Applet extends JApplet 
{
	
	public static void main(String[] args) throws IOException 
	{
		Applet ap = new Applet();
		JFrame jf = new JFrame();
		jf.setBounds(100, 100, 450, 400);
		jf.add(ap.getGui(), BorderLayout.CENTER);
		jf.setVisible(true);
	}
	
    SettingInformation settingInformation;//偵測參數

	JPanel mainPanel;
	JPanel menuPanel;
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openFileMenuItem;	
	JMenu settingMenu;
	JMenu languageChooser;
	JRadioButtonMenuItem lainguageC;
	JRadioButtonMenuItem lainguageJava;
	JMenu analyzerChooser;
	JCheckBoxMenuItem structureAnalyzerMenuItem;
	JCheckBoxMenuItem variableAnalyzerMenuItem;
	JCheckBoxMenuItem textAnalyzerMenuItem;	
	JMenuItem thresholdSetting;
	JMenuItem weightSetting;
	JMenu helpMenu;
	JMenuItem aboutMenuItem;	

	JPanel buttonPanel;
	JButton start;
	JButton openFile;
	JButton setting;

	JPanel pathPanel;
	JLabel pathLabel;
	JTextField pathTextField;
	
	JPanel fileAndResultPanel;
    JScrollPane fileScrollPane;
    JList fileList;
    JScrollPane resultScrollPane;
    JTable resultTable;
	
	// APPLET初始方法
	public void init()
	{
		try 
		{
			setContentPane(getGui());
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
	}

	JPanel getGui() throws MalformedURLException
	{
		final SettingInformation settingInformation = new SettingInformation();
		
		/*主畫面的Panel*/
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		/*上方menu、button及目錄的Panel*/
		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(3,1));
		mainPanel.add(menuPanel,BorderLayout.NORTH);
		
		/*上方的menu*/
		menuBar = new JMenuBar();
		
		/*開檔的menu*/
		fileMenu = new JMenu();
		fileMenu.setText("File");
		openFileMenuItem = new JMenuItem("Open");
		fileMenu.add(openFileMenuItem);
		menuBar.add(fileMenu);			
		
		/*設定的menu*/
		settingMenu = new JMenu();
		settingMenu.setText("Settings");
		
		/*選擇偵測語言的menu*/
		languageChooser = new JMenu();
		languageChooser.setText("Language");
		lainguageC = new JRadioButtonMenuItem("C/C++");
		lainguageC.setSelected((settingInformation.getLanguage().equals("C/C++"))?true:false);
		languageChooser.add(lainguageC);
		lainguageJava = new JRadioButtonMenuItem("Java");
		lainguageJava.setSelected((settingInformation.getLanguage().equals("Java"))?true:false);
		languageChooser.add(lainguageJava);
		ButtonGroup languageGroup = new ButtonGroup();
		languageGroup.add(lainguageC);
		languageGroup.add(lainguageJava);
		settingMenu.add(languageChooser);
		
		/*選擇分析器的menu*/
		analyzerChooser = new JMenu();
		analyzerChooser.setText("Analyzer");
		textAnalyzerMenuItem = new JCheckBoxMenuItem("Text");
		textAnalyzerMenuItem.setSelected(settingInformation.isTextAnalyzer());
		analyzerChooser.add(textAnalyzerMenuItem);
		variableAnalyzerMenuItem = new JCheckBoxMenuItem("Variable");
		variableAnalyzerMenuItem.setSelected(settingInformation.isVariableAnalyzer());
		analyzerChooser.add(variableAnalyzerMenuItem);
		structureAnalyzerMenuItem = new JCheckBoxMenuItem("Structure");
		structureAnalyzerMenuItem.setSelected(settingInformation.isStructureAnalyzer());
		analyzerChooser.add(structureAnalyzerMenuItem);
		settingMenu.add(analyzerChooser);
		
		/*設定門檻值的menu*/
		thresholdSetting = new JMenuItem("Threshold");
		settingMenu.add(thresholdSetting);
		
		/*設定權重的menu*/
		weightSetting = new JMenuItem("Weight");
		settingMenu.add(weightSetting);
		menuBar.add(settingMenu);
		
		/*help的menu*/
		helpMenu = new JMenu("Help");
		aboutMenuItem = new JMenuItem("About");
		helpMenu.add(aboutMenuItem);
		menuBar.add(helpMenu);
		menuPanel.add(menuBar);
		
		/*上方的button*/
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		URL openFileURL = new URL("http://www.cc.ntut.edu.tw/~t9598031/openfile.png");
		ImageIcon openFileThumb = new ImageIcon(openFileURL);		
		openFileThumb.setImage(openFileThumb.getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));				
		openFile = new JButton(openFileThumb);
		openFile.setPreferredSize(new Dimension(40,25));
		buttonPanel.add(openFile);
		
		URL startURL = new URL("http://www.cc.ntut.edu.tw/~t9598031/start.gif");
		ImageIcon startThumb = new ImageIcon(startURL);		
		startThumb.setImage(startThumb.getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));				
		start = new JButton(startThumb);
		start.setPreferredSize(new Dimension(40,25));
		buttonPanel.add(start);
		
		URL settingURL = new URL("http://www.cc.ntut.edu.tw/~t9598031/setting.png");
		ImageIcon settingThumb = new ImageIcon(settingURL);		
		settingThumb.setImage(settingThumb.getImage().getScaledInstance(18,18,Image.SCALE_DEFAULT));				
		setting = new JButton(settingThumb);
		setting.setPreferredSize(new Dimension(40,25));
		buttonPanel.add(setting);
		menuPanel.add(buttonPanel);
		
		/*上方的路徑*/
		pathPanel = new JPanel();
		pathPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		pathLabel = new JLabel("Directory：");
		pathTextField = new JTextField();
		pathTextField.setPreferredSize(new Dimension(350,20));
		pathPanel.add(pathLabel);
		pathPanel.add(pathTextField);
		menuPanel.add(pathPanel);
		
		/*中間的檔案列表及偵測結果*/
		fileAndResultPanel = new JPanel();
		fileAndResultPanel.setLayout(new GridLayout(1,2));
		mainPanel.add(fileAndResultPanel,BorderLayout.CENTER);
		
		/*左邊的檔案列表*/
		fileScrollPane = new JScrollPane();
		fileList = new JList();
		fileScrollPane.setViewportView(fileList);
		fileAndResultPanel.add(fileScrollPane);
		
		/*右邊的結果*/
		resultScrollPane = new JScrollPane();
		resultTable = new JTable();
		TableModel jTable1Model = new DefaultTableModel(new String[][] {},new String[] {"File 1", "File 2", "Similarity"});
		resultTable.setModel(jTable1Model);
		resultScrollPane.setViewportView(resultTable);
		fileAndResultPanel.add(resultScrollPane);
		
		/*ActionListener*/
		openFileMenuItem.addActionListener(new openFileListener(mainPanel,pathTextField,fileList,resultTable,settingInformation));
		openFile.addActionListener(new openFileListener(mainPanel,pathTextField,fileList,resultTable,settingInformation));
		lainguageC.addActionListener(new languageChooserListener(settingInformation));
		lainguageJava.addActionListener(new languageChooserListener(settingInformation));
		structureAnalyzerMenuItem.addActionListener(new analyzerChooserListener(structureAnalyzerMenuItem,settingInformation));
		variableAnalyzerMenuItem.addActionListener(new analyzerChooserListener(variableAnalyzerMenuItem,settingInformation));
		textAnalyzerMenuItem.addActionListener(new analyzerChooserListener(textAnalyzerMenuItem,settingInformation));
		thresholdSetting.addActionListener(new thresholdSettingListener(mainPanel,settingInformation));
		weightSetting.addActionListener(new weightSettingListener(mainPanel,settingInformation));
		aboutMenuItem.addActionListener(new aboutListener(mainPanel));
		setting.addActionListener(new settingListener(mainPanel,settingInformation,lainguageC,lainguageJava,structureAnalyzerMenuItem,variableAnalyzerMenuItem,textAnalyzerMenuItem));
		start.addActionListener(new startListener(mainPanel,resultTable,settingInformation));
		return mainPanel;
	}
}

/*開啟檔案*/
class openFileListener implements ActionListener
{
	JPanel mainPanel;
	JList fileList;
	JTable resultTable;
	JTextField pathTextField;
	SettingInformation settingInformation;
	
	openFileListener(JPanel mainPanel,JTextField pathTextField,JList fileList,JTable resultTable,SettingInformation settingInformation)
	{
		this.mainPanel = mainPanel;
		this.fileList = fileList;
		this.resultTable = resultTable;
		this.pathTextField = pathTextField;
		this.settingInformation = settingInformation;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JFileChooser fileChooser= new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		if(fileChooser.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION)
		{
			settingInformation.setPath(fileChooser.getSelectedFile().getPath().replace("\\", "/"));
			pathTextField.setText(settingInformation.getPath());
			fileList.setListData(generatefileList().toArray());
			TableModel nullTable = new DefaultTableModel(new String[][] {},new String[] {"File 1", "File 2", "Similarity"});
			resultTable.setModel(nullTable);
		}		
	}
	
	/*產生檔案列表*/
	private ArrayList<String> generatefileList()
	{
		ArrayList<String> fileNameList=new ArrayList<String>();		
		File file = new File(settingInformation.getPath());//設定file的路徑
		File[] files = file.listFiles();//file內所有的檔案

		for(int i = 0; i < files.length; i++) 
		{                       
			if(files[i].isFile()&&filterFile(files[i].getName()))//是否為檔案且為欲偵測之檔案類型 
			{ 
				fileNameList.add(files[i].getName());
			}           
		}
		
		if(fileNameList.size()<2)
		{
			settingInformation.setDetectedFile(false);
		}
		else
		{
			settingInformation.setDetectedFile(true);			
		}

		return fileNameList;
	}
	
	/*過濾掉非偵測對象的檔案*/
	private boolean filterFile(String name)
	{
		ArrayList<String> extensionFileName = new ArrayList<String>();
		
		if(settingInformation.getLanguage().equals("C/C++"))
		{
			extensionFileName.add("c");
			extensionFileName.add("cpp");
		}
		else if(settingInformation.getLanguage().equals("Java"))
		{
			extensionFileName.add("java");			
		}
		
		name = name.substring(name.lastIndexOf(".")+1).toLowerCase();
		
		for(int i=0;i<extensionFileName.size();i++)
		{
			if(extensionFileName.get(i).equals(name))
			{
				return true;
			}
		}
		
		return false;
	}
}

/*選擇偵測語言*/
class languageChooserListener implements ActionListener
{
	SettingInformation settingInformation;
	
	languageChooserListener(SettingInformation settingInformation)
	{
		this.settingInformation = settingInformation;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		settingInformation.setLanguage(e.getActionCommand());
	}
}

/*選擇偵測方法*/
class analyzerChooserListener implements ActionListener
{
	JCheckBoxMenuItem analyzerMenuItem;
	SettingInformation settingInformation;

	analyzerChooserListener(JCheckBoxMenuItem analyzerMenuItem,SettingInformation settingInformation)
	{
		this.analyzerMenuItem = analyzerMenuItem;
		this.settingInformation = settingInformation;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Structure"))
		{
			settingInformation.setStructureAnalyzer(analyzerMenuItem.isSelected());
		}
		else if(e.getActionCommand().equals("Variable"))
		{
			settingInformation.setVariableAnalyzer(analyzerMenuItem.isSelected());
		}
		else if(e.getActionCommand().equals("Text"))
		{
			settingInformation.setTextAnalyzer(analyzerMenuItem.isSelected());			
		}
	}
}

/*設定門檻值*/
class thresholdSettingListener implements ActionListener
{
	JPanel mainPanel;
	SettingInformation settingInformation;

	thresholdSettingListener( JPanel mainPanel,SettingInformation settingInformation)
	{
		this.mainPanel = mainPanel;
		this.settingInformation = settingInformation;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		final JDialog thresholdDialog = new JDialog(JOptionPane.getFrameForComponent(mainPanel), true);
		thresholdDialog.setLocationRelativeTo(null);
		thresholdDialog.setSize(230, 150);
		thresholdDialog.setLayout(new GridLayout(5,1));

		JPanel TextThresholdPanel = new JPanel(new GridLayout(1,2));
		JLabel TextThresholdLabel = new JLabel();
		TextThresholdPanel.add(TextThresholdLabel);
		TextThresholdLabel.setText("Text\uff1a");
		final JTextField TextThresholdjTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfText()));
		TextThresholdPanel.add(TextThresholdjTextField);
		thresholdDialog.add(TextThresholdPanel);
		
		JPanel VariableThresholdPanel = new JPanel(new GridLayout(1,2));
		JLabel VariableThresholdjLabel = new JLabel();
		VariableThresholdPanel.add(VariableThresholdjLabel);
		VariableThresholdjLabel.setText("Variable\uff1a");
		final JTextField VariableThresholdjTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfVariable()));
		VariableThresholdPanel.add(VariableThresholdjTextField);
		thresholdDialog.add(VariableThresholdPanel);
		
		JPanel StructureThresholdPanel = new JPanel(new GridLayout(1,2));
		JLabel StructureThresholdLabel = new JLabel();
		StructureThresholdPanel.add(StructureThresholdLabel);
		StructureThresholdLabel.setText("Structure\uff1a");
		final JTextField StructureThresholdLabeljTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfStructure()));
		StructureThresholdPanel.add(StructureThresholdLabeljTextField);
		thresholdDialog.add(StructureThresholdPanel);
		
		JPanel TotalThresholdPanel = new JPanel(new GridLayout(1,2));
		JLabel TotalThresholdjLabel = new JLabel();
		TotalThresholdPanel.add(TotalThresholdjLabel);
		TotalThresholdjLabel.setText("Total\uff1a");
		final JTextField TotalThresholdjTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfTotal()));
		TotalThresholdPanel.add(TotalThresholdjTextField);
		thresholdDialog.add(TotalThresholdPanel);
		
		JPanel EnterThresholdPanel = new JPanel(new GridLayout(1,3));
		JPanel LeftSpacePanel = new JPanel();
		JPanel RightSpacePanel = new JPanel();
		JButton enterThreshold = new JButton("Submit");
		enterThreshold.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				settingInformation.setThresholdOfText(Double.valueOf(TextThresholdjTextField.getText()));
				settingInformation.setThresholdOfStructure(Double.valueOf(StructureThresholdLabeljTextField.getText()));
				settingInformation.setThresholdOfVariable(Double.valueOf(VariableThresholdjTextField.getText()));
				settingInformation.setThresholdOfTotal(Double.valueOf(TotalThresholdjTextField.getText()));
				thresholdDialog.setVisible(false);
				thresholdDialog.dispose();
			}
		});
		
		EnterThresholdPanel.add(LeftSpacePanel);
		EnterThresholdPanel.add(enterThreshold);
		EnterThresholdPanel.add(RightSpacePanel);
		thresholdDialog.add(EnterThresholdPanel);
		
		thresholdDialog.setVisible(true);
	}
}

/*設定權重*/
class weightSettingListener implements ActionListener
{
	JPanel mainPanel;
	SettingInformation settingInformation;

	weightSettingListener(JPanel mainPanel,SettingInformation settingInformation)
	{
		this.mainPanel = mainPanel;
		this.settingInformation = settingInformation;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		final JDialog wightDialog = new JDialog(JOptionPane.getFrameForComponent(mainPanel), true);
		wightDialog.setLocationRelativeTo(null);
		wightDialog.setSize(230, 130);
		wightDialog.setLayout(new GridLayout(4,1));
		
		JPanel TextWeightPanel = new JPanel(new GridLayout(1,2));
		JLabel TextWeightLabel = new JLabel("Text\uff1a");
		TextWeightPanel.add(TextWeightLabel);
		final JTextField TextWeightTextField = new JTextField(String.valueOf(settingInformation.getWeightOfText()));
		TextWeightPanel.add(TextWeightTextField);
		wightDialog.add(TextWeightPanel);
		
		JPanel VariableWeightPanel = new JPanel(new GridLayout(1,2));
		JLabel VariableWeightLabel = new JLabel("Variable\uff1a");
		VariableWeightPanel.add(VariableWeightLabel);
		final JTextField VariableWeightTextField = new JTextField(String.valueOf(settingInformation.getWeightOfVariable()));
		VariableWeightPanel.add(VariableWeightTextField);
		wightDialog.add(VariableWeightPanel);
		
		JPanel StructureWeightPanel = new JPanel(new GridLayout(1,2));
		JLabel StructureWeightLabel = new JLabel("Structure\uff1a");
		StructureWeightPanel.add(StructureWeightLabel);
		final JTextField StructureWeightTextField = new JTextField(String.valueOf(settingInformation.getWeightOfStructure()));
		StructureWeightPanel.add(StructureWeightTextField);
		wightDialog.add(StructureWeightPanel);
				
		JPanel EnterPanel = new JPanel(new GridLayout(1,3));
		JPanel LeftSpacePanel = new JPanel();
		JPanel RightSpacePanel = new JPanel();
		JButton enterWeight = new JButton("Submit");
		enterWeight.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				settingInformation.setWeightOfText(Integer.valueOf(TextWeightTextField.getText()));
				settingInformation.setWeightOfStructure(Integer.valueOf(StructureWeightTextField.getText()));
				settingInformation.setWeightOfVariable(Integer.valueOf(VariableWeightTextField.getText()));
				wightDialog.setVisible(false);
				wightDialog.dispose();
			}
		});
		
		EnterPanel.add(LeftSpacePanel);
		EnterPanel.add(enterWeight);
		EnterPanel.add(RightSpacePanel);
		wightDialog.add(EnterPanel);	
		wightDialog.setVisible(true);
	}
}

/*關於此程式的資料*/
class aboutListener implements ActionListener
{
	JPanel mainPanel;
	
	aboutListener(JPanel mainPanel)
	{
		this.mainPanel = mainPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		final JDialog aboutDialog = new JDialog(JOptionPane.getFrameForComponent(mainPanel), true);
		aboutDialog.setLocationRelativeTo(null);
		aboutDialog.setSize(200, 150);
		aboutDialog.setLayout(new GridLayout(2,1));
		JLabel informationLabel = new JLabel();
		informationLabel.setBackground(Color.white); 
		informationLabel.setOpaque(true);
		informationLabel.setText("<html><body>CPD<br>(C++ Plagiarism Detection)<br>Version:1.0<body></html>");		
		aboutDialog.add(informationLabel);
		
		JPanel EnterPanel = new JPanel(new GridLayout(2,1));
		JLabel TopSpaceLabel = new JLabel();
		TopSpaceLabel.setBackground(Color.white); 
		TopSpaceLabel.setOpaque(true);
		JPanel CenterPanel = new JPanel(new GridLayout(1,3));
		JPanel LeftSpacePanel = new JPanel();
		JPanel RightSpacePanel = new JPanel();
		JButton enter = new JButton("OK");
		enter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				aboutDialog.setVisible(false);
				aboutDialog.dispose();
			}
		});
		
		CenterPanel.add(LeftSpacePanel);
		CenterPanel.add(enter);
		CenterPanel.add(RightSpacePanel);
		EnterPanel.add(TopSpaceLabel);
		EnterPanel.add(CenterPanel);
		aboutDialog.add(EnterPanel);
		aboutDialog.setVisible(true);
	}
}

/*參數的設定(偵測對象語言、偵測方法、偵測門檻值、權重)*/
class settingListener implements ActionListener
{
	JPanel mainPanel;
	SettingInformation settingInformation;
	JRadioButtonMenuItem lainguageC;
	JRadioButtonMenuItem lainguageJava;
	JCheckBoxMenuItem structureAnalyzerMenuItem;
	JCheckBoxMenuItem variableAnalyzerMenuItem;
	JCheckBoxMenuItem textAnalyzerMenuItem;	
	
    settingListener(JPanel mainPanel,SettingInformation settingInformation,JRadioButtonMenuItem lainguageC,JRadioButtonMenuItem lainguageJava,JCheckBoxMenuItem structureAnalyzerMenuItem,JCheckBoxMenuItem variableAnalyzerMenuItem,JCheckBoxMenuItem textAnalyzerMenuItem)
	{	
		this.mainPanel = mainPanel;
		this.settingInformation = settingInformation;	
		this.lainguageC = lainguageC;
		this.lainguageJava = lainguageJava;	
		this.structureAnalyzerMenuItem = structureAnalyzerMenuItem;
		this.variableAnalyzerMenuItem = variableAnalyzerMenuItem;	
		this.textAnalyzerMenuItem = textAnalyzerMenuItem;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		final JDialog settingDialog = new JDialog(JOptionPane.getFrameForComponent(mainPanel), true);
		settingDialog.setLocationRelativeTo(null);
		settingDialog.setSize(600, 180);
		settingDialog.setLayout(new BorderLayout());
		
		JPanel settingPanel = new JPanel(new GridLayout(1,4));
		settingDialog.add(settingPanel,BorderLayout.CENTER);
		JPanel languagePanel = new JPanel(new GridLayout(2,1));
		languagePanel.setBorder(BorderFactory.createTitledBorder("Language"));
		final JRadioButton languageCRadioButton = new JRadioButton();
		languagePanel.add(languageCRadioButton);
		languageCRadioButton.setText("C/C++");
		final JRadioButton languageJavaRadioButton = new JRadioButton();
		languagePanel.add(languageJavaRadioButton);
		languageJavaRadioButton.setText("Java");
		ButtonGroup languageGroup = new ButtonGroup();
		languageGroup.add(languageCRadioButton);
		languageGroup.add(languageJavaRadioButton);
		settingPanel.add(languagePanel);
		
		JPanel analyzerPanel = new JPanel(new GridLayout(3,1));
		analyzerPanel.setBorder(BorderFactory.createTitledBorder("Analyzer"));
		final JCheckBox textAnalyzerCheckBox = new JCheckBox();
		analyzerPanel.add(textAnalyzerCheckBox);
		textAnalyzerCheckBox.setText("Text");
		final JCheckBox variableAnalyzerCheckBox = new JCheckBox();
		analyzerPanel.add(variableAnalyzerCheckBox);
		variableAnalyzerCheckBox.setText("Variable");
		final JCheckBox structureAnalyzerCheckBox = new JCheckBox();
		analyzerPanel.add(structureAnalyzerCheckBox);
		structureAnalyzerCheckBox.setText("Structure");
		settingPanel.add(analyzerPanel);
		
		JPanel thresholdPanel = new JPanel(new GridLayout(4,2));
		thresholdPanel.setBorder(BorderFactory.createTitledBorder("Threshold"));
		JLabel TextThresholdLabel = new JLabel();
		thresholdPanel.add(TextThresholdLabel);
		TextThresholdLabel.setText("Text\uff1a");
		final JTextField textThresholdTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfText()));
		thresholdPanel.add(textThresholdTextField);
		JLabel variableThresholdLabel = new JLabel();
		thresholdPanel.add(variableThresholdLabel);
		variableThresholdLabel.setText("Variable\uff1a");
		final JTextField variableThresholdTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfVariable()));
		thresholdPanel.add(variableThresholdTextField);
		JLabel structureThresholdLabel = new JLabel();
		thresholdPanel.add(structureThresholdLabel);
		structureThresholdLabel.setText("Structure\uff1a");
		final JTextField structureThresholdTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfStructure()));
		thresholdPanel.add(structureThresholdTextField);
		JLabel totalThresholdLabel = new JLabel();
		thresholdPanel.add(totalThresholdLabel);
		totalThresholdLabel.setText("Total\uff1a");
		final JTextField totalThresholdTextField = new JTextField(String.valueOf(settingInformation.getThresholdOfTotal()));
		thresholdPanel.add(totalThresholdTextField);
		settingPanel.add(thresholdPanel);
		
		
		JPanel weightPanel = new JPanel(new GridLayout(3,2));
		weightPanel.setBorder(BorderFactory.createTitledBorder("Weight"));
		JLabel TextWeightLabel = new JLabel("Text\uff1a");
		weightPanel.add(TextWeightLabel);
		final JTextField textWeightTextField = new JTextField(String.valueOf(settingInformation.getWeightOfText()));
		weightPanel.add(textWeightTextField);
		JLabel variableWeightLabel = new JLabel("Variable\uff1a");
		weightPanel.add(variableWeightLabel);
		final JTextField variableWeightTextField = new JTextField(String.valueOf(settingInformation.getWeightOfVariable()));
		weightPanel.add(variableWeightTextField);
		JLabel structureWeightLabel = new JLabel("Structure\uff1a");
		weightPanel.add(structureWeightLabel);
		final JTextField structureWeightTextField = new JTextField(String.valueOf(settingInformation.getWeightOfStructure()));
		weightPanel.add(structureWeightTextField);
		settingPanel.add(weightPanel);

		JPanel submitPanel = new JPanel(new GridLayout(1,3));
		JPanel leftSpacePanel = new JPanel();
		JPanel rightSpacePanel = new JPanel();
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{			
				if(languageCRadioButton.isSelected())
				{
					settingInformation.setLanguage("C/C++");
					lainguageC.setSelected(true);
				}
				else if(languageJavaRadioButton.isSelected())
				{
					settingInformation.setLanguage("Java");
					lainguageJava.setSelected(true);
				}
				
				settingInformation.setStructureAnalyzer(structureAnalyzerCheckBox.isSelected());
				settingInformation.setTextAnalyzer(textAnalyzerCheckBox.isSelected());
				settingInformation.setVariableAnalyzer(variableAnalyzerCheckBox.isSelected());
				settingInformation.setThresholdOfText(Double.valueOf(textThresholdTextField.getText()));
				settingInformation.setThresholdOfStructure(Double.valueOf(structureThresholdTextField.getText()));
				settingInformation.setThresholdOfVariable(Double.valueOf(variableThresholdTextField.getText()));
				settingInformation.setThresholdOfTotal(Double.valueOf(totalThresholdTextField.getText()));
				settingInformation.setWeightOfText(Integer.valueOf(textWeightTextField.getText()));
				settingInformation.setWeightOfVariable(Integer.valueOf(variableWeightTextField.getText()));
				settingInformation.setWeightOfStructure(Integer.valueOf(structureWeightTextField.getText()));
				structureAnalyzerMenuItem.setSelected(structureAnalyzerCheckBox.isSelected());
				textAnalyzerMenuItem.setSelected(textAnalyzerCheckBox.isSelected());
				variableAnalyzerMenuItem.setSelected(variableAnalyzerCheckBox.isSelected());
				
				settingDialog.setVisible(false);
				settingDialog.dispose();
			}
		});

		submitPanel.add(leftSpacePanel);
		submitPanel.add(submit);
		submitPanel.add(rightSpacePanel);
		settingDialog.add(submitPanel,BorderLayout.SOUTH);
		
		/*從主畫面拿值設定初始值*/
		if(settingInformation.getLanguage().equals("C/C++"))
		{
			languageCRadioButton.setSelected(true);
		}
		
		if(settingInformation.getLanguage().equals("Java"))
		{
			languageJavaRadioButton.setSelected(true);			
		}			

		structureAnalyzerCheckBox.setSelected(settingInformation.isStructureAnalyzer());
		variableAnalyzerCheckBox.setSelected(settingInformation.isVariableAnalyzer());
		textAnalyzerCheckBox.setSelected(settingInformation.isTextAnalyzer());
		
		settingDialog.setVisible(true);
	}
}

/*執行後端偵測程式*/
class startListener implements ActionListener
{
	JPanel mainPanel;
	JTable resultTable;
	SettingInformation settingInformation;
	
	startListener( JPanel mainPanel,JTable resultTable,SettingInformation settingInformation)
	{
		this.mainPanel = mainPanel;
		this.resultTable = resultTable;
		this.settingInformation = settingInformation;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(settingInformation.getPath().equals(""))
		{
			JOptionPane.showMessageDialog(mainPanel, "Please select the directory!!", "Error", JOptionPane.INFORMATION_MESSAGE );
		}
		else if(!(settingInformation.isStructureAnalyzer()||settingInformation.isTextAnalyzer()||settingInformation.isVariableAnalyzer()))
		{
			JOptionPane.showMessageDialog(mainPanel, "Please choose the analysis methods!!", "Error", JOptionPane.INFORMATION_MESSAGE );			
		}
		else if(!settingInformation.haveDetectedFile())
		{
			JOptionPane.showMessageDialog(mainPanel, "The directory must have two detected file at least!!", "Error", JOptionPane.INFORMATION_MESSAGE );			
		}
		else
		{
			PlagiarismDetection plagiarismDetection = null;
			
			if(settingInformation.getLanguage().equals("C/C++"))
			{
				plagiarismDetection = new CAndCppPlagiarismDetection(settingInformation);
			}
			else if(settingInformation.getLanguage().equals("Java"))
			{
				plagiarismDetection = new JavaPlagiarismDetection(settingInformation);
			}

			TableModel resultTableModel = new DefaultTableModel(plagiarismDetection.execute(),new String[] {"File 1", "File 2", "Similarity"});
			resultTable.setModel(resultTableModel);	
		}			
	}
}