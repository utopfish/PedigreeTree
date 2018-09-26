package view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import controller.Controller;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.event.ActionListener;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.undo.UndoManager;
import ImputationKNN.Possibility;
import alg_practice.DiscribeTree;
import alg_practice.alg;
import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.datatransfer.Clipboard;
import java.awt.Button;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Color;
import java.awt.Toolkit;

public class MainFrame {
	/**
     * 软件的界面类，定义了控件以及控件的布局
     * this class is to make a frame to show the application ui
	 * 
	 * @author Liuzeyun
	 * @email 1418685745@qq.com
	 * @date 2018.3
	 */
	public JFrame frmBuildpedigreetree;
	
	public Controller con;
	public DiscribeTree dt;
	public alg algmain;
	private Possibility possib;
	
	public TextArea ShowtextArea = new TextArea();
	public JTextArea Input_textField = new JTextArea();
	
	public JMenuItem pasteItem; //粘贴功能
	//use for mouse right click menu
	private JPopupMenu popMenu = new JPopupMenu();     //右击鼠标弹出的菜单
	//use for auto wrap
	public JMenuItem lineItem = new JMenuItem(); //自动换行选项
	//use for undo function
	public UndoManager undoMgr = new UndoManager(); //撤销管理器
	//use clipboard to temporary storage of information
	public Clipboard clipboard = null; //剪贴板

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame mf = new MainFrame();
					
					//副类的定义
					Controller con = new Controller();
					DiscribeTree dt = new DiscribeTree();
					alg algmain = new alg();
					
					Possibility possib = new Possibility();
					
					//主类调用
					mf.setController(con);
					mf.setDiscribeTree(dt);
					mf.setAlg(algmain);
					mf.setPossib(possib);
					
					//algmain.setMainFrame(mf);
					con.setMainFrame(mf);
					dt.setMainFrame(mf);
					algmain.setMainFrame(mf);
					possib.setMainFrame(mf);
					//Map m = alg1.Do(con.rows,con.cols);
					
					mf.frmBuildpedigreetree.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void setPossib(Possibility possib) {
		// TODO Auto-generated method stub
		this.possib = possib;
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	public void setController(Controller con){
		this.con = con;
	}
	public void setDiscribeTree(DiscribeTree dt){
		this.dt = dt;
	}
	public void setAlg(alg algmain){
		this.algmain = algmain;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBuildpedigreetree = new JFrame();
		frmBuildpedigreetree.setIconImage(Toolkit.getDefaultToolkit().getImage("D:\\EclipseProgram\\PedigreeTree\\res\\icon.JPG"));
		frmBuildpedigreetree.setTitle("谱系分析软件PhyloTree v1.0");
		frmBuildpedigreetree.setBounds(100, 100, 644, 611);
		frmBuildpedigreetree.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmBuildpedigreetree.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNextTextFile = new JMenuItem("Next Text File");
		mntmNextTextFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				con.createFile();
			}
		});
		mnFile.add(mntmNextTextFile);
		
		JMenuItem mntmOpen = new JMenuItem("Open Matrix");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.openFile();
			}
		});
		mnFile.add(mntmOpen);
		JMenuItem mntmOpenAdjacency = new JMenuItem("Open Character hierarchy Matrix");
		mntmOpenAdjacency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.openAdjacencyFile();
			}
		});
		mnFile.add(mntmOpenAdjacency);
		
		JMenuItem mntmLogOutputTo = new JMenuItem("Log Output to File");
		mntmLogOutputTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.LogOutputtofile();
			}
		});
		mnFile.add(mntmLogOutputTo);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.exit();
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmClearDisplayBuffer = new JMenuItem("Clear Display Buffer");
		mntmClearDisplayBuffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.ClearDisplayBuffer();
			}
		});
		mnEdit.add(mntmClearDisplayBuffer);
		
		JMenuItem mntmEditDisplayBuffer = new JMenuItem("Edit Display Buffer");
		mntmEditDisplayBuffer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.EditDisplayBuffer();
			}
		});
		mnEdit.add(mntmEditDisplayBuffer);
		
		JMenu mnData = new JMenu("Data");
		menuBar.add(mnData);
		
		JMenuItem mntmShowDataMatrix = new JMenuItem("Show Matrix");
		mntmShowDataMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.ShowDataMat();
			}
		});
		mnData.add(mntmShowDataMatrix);
		
		JMenuItem mntmShowAdjacencyMatrix = new JMenuItem("Character hierarchy Matrix");
		mntmShowAdjacencyMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.ShowAdjacencyMat();
			}
		});
		mnData.add(mntmShowAdjacencyMatrix);
		
		JMenuItem mntmKnnInterpolation = new JMenuItem("KNN Interpolation");
		mntmKnnInterpolation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				con.KnnInterpolation();
			}
		});
		mnData.add(mntmKnnInterpolation);
		
		JMenu mnAnalysis = new JMenu("Analysis");
		menuBar.add(mnAnalysis);
		
		JMenuItem mntmSimulatedAnnealing = new JMenuItem("Simulated Annealing");
		mntmSimulatedAnnealing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				con.SimulatedAnnealing();
			}
		});
		mnAnalysis.add(mntmSimulatedAnnealing);
		
		JMenu mnTree = new JMenu("Tree");
		menuBar.add(mnTree);
		
		JMenuItem ShowTree = new JMenuItem("Show Trees");
		ShowTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				con.showTree();
			}
		});
		mnTree.add(ShowTree);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con.about();
			}
		});
		mnHelp.add(mntmAbout);
		
		JSplitPane splitPane = new JSplitPane();
		frmBuildpedigreetree.getContentPane().add(splitPane, BorderLayout.SOUTH);
		
		Button executeBt = new Button("Execute");
		splitPane.setLeftComponent(executeBt);
		
		
		splitPane.setRightComponent(Input_textField);
		ShowtextArea.setBackground(Color.WHITE);
		ShowtextArea.setForeground(Color.BLACK);
		ShowtextArea.setEditable(false);
		ShowtextArea.setFont(new Font("Consolas", Font.PLAIN, 13));
		ShowtextArea.setText("\t\t\t\tAbout BuildingTree\r\nVersion 1.0 for 64-bit Microsoft Windows created by Liu Zeyun.\r\n---------------------------------NOTICE---------------------------------\r\n    This is an alpha-test version that is still changing rapidly.\r\n    Please report bugs to 1418685745@qq.com\r\n---------------------------------------------------------------------------\r\n\t\t");
		
		//TextArea ShowtextArea = new TextArea();
		frmBuildpedigreetree.getContentPane().add(ShowtextArea, BorderLayout.CENTER);
	}

}
