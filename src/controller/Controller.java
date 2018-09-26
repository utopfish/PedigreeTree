package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.AbstractButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import ImputationKNN.Accuracy;
import ImputationKNN.ArtMiss;
import ImputationKNN.IterationKNN;
import ImputationKNN.mode;
import view.MainFrame;
import alg_practice.*;

/**
 * �����������趨�˹��ܷ��� 
 * 
 * This class is a controller include some methods.
 * 
 * @author Liuzeyun
 * @email 1418685745@qq.com
 * @date 2018.3
 */
public class Controller {
	private MainFrame mf;
	// this sign for if the file has been edited
	public boolean isEdited = false;// �Ƿ��Ѿ����޸Ĺ�
	// file path which now is been reading
	public File txt = null;// ��д�ļ�·��
	// file chooser dialog
	public static JFileChooser fileChooser = new JFileChooser();// �ļ��Ի���
	// ����<������������1������2������3����...������n>
	public ArrayList<dataStructe> data = new ArrayList<dataStructe>();
	public ArrayList<adjacencyStructe> adjacency = new ArrayList<adjacencyStructe>();
	//������
	public String[] dataSpecial;
	//��������
	public int[][] dataMatrix;
	public int[][] adjacencyMatrix;
	public int rows = 0;
	public int cols = 0;
	public File file;
	
	public List<DiscribeTree> describeTreeList = new ArrayList<DiscribeTree>();

	public void setMainFrame(MainFrame mf) {
		this.mf = mf;
	}
	/*
	 * �ļ�����ģ��
	 */

	// �½��ļ�
	// create new file
	public void createFile() {
		Object jta = null;
		fileChooser.setDialogTitle("�½�");
		// �û������ļ��������½����ļ�
		int returnVal = fileChooser.showSaveDialog(null);
		fileChooser.setVisible(true);
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			return;
		}
		String pathname = fileChooser.getSelectedFile().getAbsolutePath();
		FileWriter fw = null;
		BufferedWriter bw = null;
		String str = null;
		try {
			fw = new FileWriter(pathname);
			bw = new BufferedWriter(fw);
			str = ((AbstractButton) jta).getText();

			bw.write(((AbstractButton) jta).getText());

		} catch (Exception e1) {
			/* e1.printStackTrace(); */
		} finally {
			try {
				bw.close();
				fw.close();
				/* System.out.println(pathname); */
				// ���ļ�
				String cmd = "rundll32 url.dll FileProtocolHandler file:" + pathname; // ������Ӧ��windows���������ļ�
				Process p = Runtime.getRuntime().exec(cmd);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// ���ļ�
	// open file
	public void openFile() {
		// ���ļ��Ի���
		// show open file dialog
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.showDialog(new JLabel(), "ѡ��");
		file = fileChooser.getSelectedFile();

		try {
			data = read.loadData(file);
			
			// ArrayList<dataStructe> data = new ArrayList<dataStructe>();
			Scanner fileScanner = new Scanner(file);
			/*
			 * row ������row������ col ������col������
			 */
			rows = fileScanner.nextInt();
			cols = fileScanner.nextInt();

		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// ��ȡ�ļ��е�������Ϣ
		// �жϸ������ļ��ж����У������ٸ����֣��������У������ٸ�������
		// file.getAbsolutePath();+txtfile.getSelectedFile().getName(); //�ļ��ľ���·��
		mf.ShowtextArea.append('\n' + "Read file : " + file.getAbsolutePath() + '\n');
		mf.ShowtextArea.append("---------------Document content---------------" + '\n');

		try {
			// ���ļ�
			String cmd = "rundll32 url.dll FileProtocolHandler file:" + file.getAbsolutePath(); // ������Ӧ��windows���������ļ�
			Process p = Runtime.getRuntime().exec(cmd);
			// ��ȡ�ļ�
			String encoding = "GBK";
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
			BufferedReader bufferedReader = new BufferedReader(read);
		} catch (Exception e1) {
			mf.ShowtextArea.append("��ȡ�ļ����ݳ���" + '\n');
			e1.printStackTrace();
		}

		mf.ShowtextArea.append('\n' + "Data matrix has " + rows + " taxa, " + cols + " characters" + '\n'
				+ "Valid character-state symbols: 012345" + '\n' + "Missing data identified by '?'" + '\n'
				+ "Gaps identified by 'N'" + '\n');
		mf.ShowtextArea.append('\n' + "Processing of input file " + file.getAbsolutePath() + " completed."+'\n');

		//���־����ʼ��
		dataSpecial = new String[rows];
		//����������ʼ��
		dataMatrix = new int[rows][cols];
		
		//�������
		for (int i = 0; i < rows; i++) {
			//������
			dataSpecial[i] = data.get(i).dataSpeci;

			for (int j = 0; j < cols; j++) {
				//��������
				dataMatrix[i][j] = data.get(i).dataMat[j];
			}
		}
	}
	public void openAdjacencyFile() {
		// ���ļ��Ի���
		// show open file dialog
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.showDialog(new JLabel(), "ѡ��");
		file = fileChooser.getSelectedFile();

		try {
				adjacency = read.loadAdjacency(file);

			// ArrayList<dataStructe> data = new ArrayList<dataStructe>();
			Scanner fileScanner = new Scanner(file);
			/*
			 * row ������row������ col ������col������
			 */
			int rows = fileScanner.nextInt();
			int cols = fileScanner.nextInt();

		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// ��ȡ�ļ��е�������Ϣ
		// �жϸ������ļ��ж����У������ٸ����֣��������У������ٸ�������
		// file.getAbsolutePath();+txtfile.getSelectedFile().getName(); //�ļ��ľ���·��
		mf.ShowtextArea.append('\n' + "Read file : " + file.getAbsolutePath() + '\n');
		mf.ShowtextArea.append("---------------Document content---------------" + '\n');

		try {
			// ���ļ�
			String cmd = "rundll32 url.dll FileProtocolHandler file:" + file.getAbsolutePath(); // ������Ӧ��windows���������ļ�
			Process p = Runtime.getRuntime().exec(cmd);
			// ��ȡ�ļ�
			String encoding = "GBK";
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// ���ǵ������ʽ
			BufferedReader bufferedReader = new BufferedReader(read);
		} catch (Exception e1) {
			mf.ShowtextArea.append("��ȡ�ļ����ݳ���" + '\n');
			e1.printStackTrace();
		}

		mf.ShowtextArea.append('\n' + "Adjacency matrix has "  + cols + " characters" + '\n'
				+ "Valid character-state symbols: 012345" + '\n' + "Missing data identified by '?'" + '\n'
				+ "Gaps identified by 'N'" + '\n');
		mf.ShowtextArea.append('\n' + "Processing of input file " + file.getAbsolutePath() + " completed."+'\n');


		//����������ʼ��
		adjacencyMatrix = new int[rows][cols];
		
		//�������
		for (int i = 0; i < rows; i++) {


			for (int j = 0; j < cols; j++) {
				//��������
				adjacencyMatrix[i][j] = adjacency.get(i).dataMat[j];
			}
		}
		for(int i=0;i<rows;i++){
			Arrays.toString(adjacencyMatrix[i]);
		}
	}
	// Log Output to file
	public void LogOutputtofile() {
		Object jta = null;
		fileChooser.setDialogTitle("Log Out to File");
		// �û������ļ��������½����ļ�
		int returnVal = fileChooser.showSaveDialog(null);
		fileChooser.setVisible(true);
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			return;
		}
		String pathname = fileChooser.getSelectedFile().getAbsolutePath();
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {

			fw = new FileWriter(pathname);
			bw = new BufferedWriter(fw);
			String str = ((AbstractButton) jta).getText();
			System.out.println(str);
			bw.write(((AbstractButton) jta).getText());
		} catch (Exception e1) {
			/* e1.printStackTrace(); */
		} finally {
			try {
				// System.out.println(ShowtextArea.getText());
				// ��log�ļ���д����
				FileWriter logfw = new FileWriter(pathname);
				logfw.write(mf.ShowtextArea.getText());
				logfw.flush();
				logfw.close();
				bw.close();
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	 * Edit�༭ģ��
	 */
	// Clear Display Buffer
	public void ClearDisplayBuffer() {
		// ���ֵ���
		int res = JOptionPane.showConfirmDialog(null, "�Ƿ����output display buffer������", "�Ƿ����",
				JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			mf.ShowtextArea.setText(""); // ������ǡ���ִ����������
		}
	}

	// Edit Display Buffer
	public void EditDisplayBuffer() {
		mf.ShowtextArea.setEditable(!mf.ShowtextArea.isEditable());
	}

	/*
	 * Dataģ��
	 */
	// ��ʾ���ݾ���
	public void ShowDataMat() {
		//���������ʽ
		int stringwidth = dataSpecial[0].length()*3;;
		mf.ShowtextArea.append("Input data matrix:" + '\n');
		mf.ShowtextArea.append('\n' + "Taxon");
		for (int nullNUM = 0; nullNUM <= stringwidth - 5; nullNUM++)
			mf.ShowtextArea.append(" ");

		for (int i = 1; i <= cols; i++)
			mf.ShowtextArea.append(String.valueOf(i % 10));

		mf.ShowtextArea.append("\n");

		for (int i = 0; i < stringwidth + cols; i++)
			mf.ShowtextArea.append("-");
		mf.ShowtextArea.append("\n");
		
		//�������
		for (int i = 0; i < rows; i++) {
			mf.ShowtextArea.append(data.get(i).dataSpeci);
			
			for (int nullNUM = 0; nullNUM <= stringwidth - data.get(i).dataSpeci.length(); nullNUM++)
				mf.ShowtextArea.append(" ");

			for (int j = 0; j < cols; j++) {
				if(data.get(i).dataMat[j] == -1)
				{// -1˵��������ȱʧ
					mf.ShowtextArea.append("?");
				}
				else if(data.get(i).dataMat[j] == -1){
					mf.ShowtextArea.append("N");
				}else{
					mf.ShowtextArea.append(String.valueOf(data.get(i).dataMat[j]));
				}
				
			}
			mf.ShowtextArea.append("\n");
		}

	}
	public void ShowAdjacencyMat() {
		//���������ʽ
		int stringwidth = dataSpecial[0].length()*3;;
		mf.ShowtextArea.append("Input adjacency matrix:" + '\n');
		mf.ShowtextArea.append('\n' + "Taxon");
		for (int nullNUM = 0; nullNUM <= stringwidth - 5; nullNUM++)
			mf.ShowtextArea.append(" ");

		for (int i = 1; i <= cols; i++)
			mf.ShowtextArea.append(String.valueOf(i % 10));

		mf.ShowtextArea.append("\n");

		for (int i = 0; i < stringwidth + cols; i++)
			mf.ShowtextArea.append("-");
		mf.ShowtextArea.append("\n");
		
		//�������
		for (int i = 0; i < cols; i++) {
			mf.ShowtextArea.append(String.valueOf(i % 10));
			for (int nullNUM = 0; nullNUM <= stringwidth-1; nullNUM++)
				mf.ShowtextArea.append(" ");

			for (int j = 0; j < cols; j++) {
				
				if(adjacency.get(i).dataMat[j] == -1)
				{// -1˵��������ȱʧ
					mf.ShowtextArea.append("?");
				}
				else if(adjacency.get(i).dataMat[j] == -2){
					mf.ShowtextArea.append("N");
				}else{
					mf.ShowtextArea.append(String.valueOf(adjacency.get(i).dataMat[j]));
				}
				
			}
			mf.ShowtextArea.append("\n");
		}

	}
	//KNN��ֵ����
	public void KnnInterpolation()
	{
		int NTimes = 5;
		int MISRate = 2;
	        //��������
			int Data[][] = dataMatrix;
			int TData[][] =new int[Data.length][Data[0].length];
			for(int i=0;i<Data.length;i++) {
				TData[i] = Data[i].clone();
			}
			
				//��Ϊ��ȱʧ���ݴ���
/*				ArtMiss art = new ArtMiss();
				int[][] MisDataLocation = art.generateXOY(Data, MISRate);
				int[][] ArtData = art.generateData(Data, MisDataLocation);*/
				//������ɳ�ʼ����ģʽ�岹
				mode modeimputation = new mode();
				findMiss Miss =new findMiss();
				//�ҵ�ȱʧ����λ��
				int[][] MisDataLocation=Miss.location(Data);
				int[][] firstModeImputation = modeimputation.modeImputation(Data, MisDataLocation);

				int[][] onceImputationData =new int [Data.length][Data[0].length];
				for(int n=0;n<NTimes;n++) {
					
				IterationKNN iteration = new IterationKNN();
				onceImputationData = iteration.iteration(firstModeImputation, MisDataLocation);

				for(int k=0;k<onceImputationData.length;k++) {
					firstModeImputation[k] = onceImputationData[k].clone();
				}
				}
				int tab = dataSpecial[0].length()*3;
				mf.ShowtextArea.append("KNN�岹��ɣ�KNN�岹������ݼ�Ϊ"+'\n'+'\n');
				for(int i=0;i<onceImputationData.length;i++) {
					mf.ShowtextArea.append(dataSpecial[i] +" ");
					for(int tabI = 0;tabI<tab-dataSpecial[i].length();tabI++)
						mf.ShowtextArea.append(" ");
					dataStructe dataline = new dataStructe(); 
					dataline=data.get(i);
					for(int j = 0;j<onceImputationData[i].length;j++)
						{mf.ShowtextArea.append(String.valueOf(onceImputationData[i][j]));
						dataline.dataMat[j]=onceImputationData[i][j];
						}
					data.set(i, dataline);
					mf.ShowtextArea.append(""+'\n');
				}
				dataMatrix=onceImputationData;
			   //����岹��׼ȷ��
//				Accuracy calAccuracy = new Accuracy();
//
//				double accuracy = calAccuracy.calAccuracy(TData, onceImputationData,MISRate);
//				mf.ShowtextArea.append(String.format("�岹׼ȷ�ʣ� %.2f",accuracy*100));
//				mf.ShowtextArea.append("%");
			

	}
	

	/*
	 * ����ģ��
	 */

	// Simulated Annealing
	public void SimulatedAnnealing() {
		// TODO Auto-generated method stub
		int []out = new int[cols];
		//ָ������Ⱥ, Ĭ��Ϊ��һ�����ֵ�����״̬
		for(int i = 0 ;i < cols;i++)
		{
			out[i] = dataMatrix[0][i];
		}
		//System.out.println(Arrays.toString(out));
		
		DiscribeTree discribeTree = new DiscribeTree();
		data method = new data();
		int[][] Datanew = new int[rows][cols];
		//System.out.println(rows + " " + cols);
		for (int i = 0; i < rows; i++) {
			Datanew[i] = Arrays.copyOf(dataMatrix[i], cols);
		}
		
		discribeTree = method.BuildTree(out, Datanew);
		int[] bestout = out.clone();
		//System.out.println(Arrays.toString(bestout));
		DiscribeTree bestTree = discribeTree.Clone(discribeTree);
		//bestTree.toString();
		
		double normalCi = 0.1;//���ñ�׼CI
		
		// bestTnumΪ����ʵļ�����
		int bestTnum = 1;
		int Tnum = 0;
		while (Tnum < bestTnum ) {
			int MaxGen = 10000; //��������������
		    int generate = 0;
		    while(generate < MaxGen) {
		        //�Ŷ������½�w0     
		        polarization polar = new polarization();
		        int[][] dataCopy = new int[rows][cols];
		        for (int i = 0; i < dataCopy.length; i++) {
		        	dataCopy[i] = dataMatrix[i].clone();
	    		}
		        int[] newout = polar.polar(dataCopy);
		        while (Math.random() < 0.5) {
		        	dataCopy = new int[rows][cols];
		        	for (int i = 0; i < dataCopy.length; i++) {
		        		dataCopy[i] = dataMatrix[i].clone();
		    		}
			       newout = polar.polar(dataCopy);
		        }
		        DiscribeTree discribeTreenew = new DiscribeTree();
		        int[][] datanew = new int[rows][cols];
		        for (int i = 0; i < datanew.length; i++) {
		        	datanew[i] = dataMatrix[i].clone();
	    		}

		        discribeTreenew = method.BuildTree( newout, datanew);
		        double diertaF = discribeTreenew.getCI() - discribeTree.getCI();//������Խ��
		         if (diertaF > 0) {
					bestout = newout.clone();
					bestTree = discribeTree.Clone(discribeTreenew);
				}
			    else {
			    	if (Math.exp(diertaF/normalCi) > Math.random()) {
			    		bestout = newout.clone();
			    		bestTree = discribeTree.Clone(discribeTreenew);;
					}
			    }
			    generate = generate+1;
		    }
		   String isEqual = "";
		    if (!isEqual.contains(bestTree.isStringEqual())) {
				   describeTreeList.add(bestTree);
				   isEqual = isEqual + bestTree.isStringEqual();
			}
		   Tnum ++;
		}
		for (DiscribeTree i : describeTreeList) {
//			System.out.println(i.getTopology());
			//i.toString();
			mf.ShowtextArea.append('\n'+"�����������˽ṹ��"+ i.Topology+'\n');
			mf.ShowtextArea.append("����ָ�꣺"+'\n');
			mf.ShowtextArea.append("����(Tree Length�� = "+ i.Length+'\n');
			mf.ShowtextArea.append("һ����ָ��(Consistency index) = " +String.format("%.2f", i.CI)+'\n');
			mf.ShowtextArea.append("ͬ����ָ�� (Homoplasy index) = " +String.format("%.2f", i.HI)+'\n');
			mf.ShowtextArea.append("������ָ��(Retention index) = " +String.format("%.2f", i.RI)+'\n');
			mf.ShowtextArea.append("У��һ����ָ��(Rescaled consistency index) =" +String.format("%.2f", i.RC)+'\n');
		}
		mf.ShowtextArea.append("���У�"+'\n');
		for(int i = 0 ;i<rows;i++)
		{
			mf.ShowtextArea.append(String.format("%-3d", i)+"---"+dataSpecial[i]+'\n');
		}
		
		// ���ֵ������Ƿ񱣴�.tree�ļ�
		int res = JOptionPane.showConfirmDialog(null, "�Ƿ񵼳����ļ�", "�Ƿ����",
				JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			outputTre(); // ������ǡ���ִ����������
		}
	}
	// �Ƿ񵼳����ļ�
	public void outputTre()
	{
		Object jta = null;
		fileChooser.setDialogTitle("�������ļ�");
		// �û������ļ��������½����ļ�
		int returnVal = fileChooser.showSaveDialog(null);
		fileChooser.setVisible(true);
		if (returnVal == JFileChooser.CANCEL_OPTION) {
			return;
		}
		String pathname = fileChooser.getSelectedFile().getAbsolutePath();
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(pathname);
			bw = new BufferedWriter(fw);
			String str = ((AbstractButton) jta).getText();
			System.out.println(str);
			bw.write(((AbstractButton) jta).getText());
		} catch (Exception e1) {
			/* e1.printStackTrace(); */
		} finally {
			try {
				// ��.tree�ļ���д����
				FileWriter treefw = new FileWriter(pathname);
				
				//��ȡ��ǰϵͳʱ��
				Date day=new Date();    
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
 
				String treeContext = "#NEXUS \r\n" + 
						"\r\n" + 
						"Begin trees;  [Treefile saved" +df.format(day)+"]\r\n" + 
						"[!\r\n" + 
						">Data file = "+file.getAbsolutePath()+"\r\n" + 
						">Heuristic search settings:\r\n" + 
						">  Optimality criterion = parsimony\r\n" + 
						">    Character-status summary:\r\n" + 
						">      Of "+ cols +" total characters:\r\n" + 
						">        All characters are of type 'unord'\r\n" + 
						">        All characters have equal weight\r\n" + 
						">        4 characters are parsimony-uninformative\r\n" + 
						">        Number of parsimony-informative characters = 34\r\n" + 
						">    Gaps are treated as \"missing\"\r\n" + 
						">  Starting tree(s) obtained via stepwise addition\r\n" + 
						">  Addition sequence: random\r\n" + 
						">    Number of replicates = 10\r\n" + 
						">    Starting seed = 1058963645\r\n" + 
						">  Number of trees held at each step during stepwise addition = 1\r\n" + 
						">  Branch-swapping algorithm: tree-bisection-reconnection (TBR)\r\n" + 
						">  Steepest descent option not in effect\r\n" + 
						">  Initial 'MaxTrees' setting = 100\r\n" + 
						">  Branches collapsed (creating polytomies) if maximum branch length is zero\r\n" + 
						">  'MulTrees' option in effect\r\n" + 
						">  Topological constraints not enforced\r\n" + 
						">  Trees are unrooted\r\n" + 
						">\r\n" + 
						">Search terminated prematurely (no room to store new trees)\r\n" + 
						">   1 trees retained\r\n" + 
						">   Time used = 3.87 sec\r\n" + 
						"]\r\n" + 
						"	Translate\r\n" ;
				String treeSpeci = "";
		        for (int i = 1; i < rows; i++) {
		        	treeSpeci = treeSpeci + "		"+String.valueOf(i)+" "+dataSpecial[i-1]+ "," + '\r'+'\n';
		        }
		        treeSpeci = treeSpeci + "		"+String.valueOf(rows)+" "+dataSpecial[rows-1] + '\r'+'\n'+
						"		;\r\n" + 
						"tree PAUP_1 = [&U]"+ describeTreeList.get(0).Topology+";\r\n" +
						"End;\r\n" + 
						"";
		        treeContext = treeContext + treeSpeci;
		        
		        treefw.write(treeContext);
				treefw.flush();
				treefw.close();
				bw.close();
				fw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	/*
	 * Treeģ��
	 */
	// Show Tree
	public void showTree() {
		// ����jar���е�������
		String jarPath = System.getProperty("user.dir") + "\\lib\\figtree-1.3.1.jar";
		try {
			// ���ļ�
			String cmd = "rundll32 url.dll FileProtocolHandler file:" + jarPath; // ������Ӧ��windows���������ļ�
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/*
	 * ����ģ��
	 */
	// about
	public void about() {
		// show the dialog for introduce the copyright of this appliaction
		JOptionPane.showMessageDialog(null, "һ���������ｨ������� " + "Copyright2018 Liu��email��1418685745@qq.com");
	}

	// �˳�
	// exit method
	public void exit() {
		System.exit(0);
	}

	// �ڹ��캯��������file��Ĭ��ѡ��
	// make the default saving file path to desktop
	public Controller() {
		File path = new File(fileChooser.getCurrentDirectory().getParent() + "\\Desktop\\");
		fileChooser.setCurrentDirectory(path);
	}

}
