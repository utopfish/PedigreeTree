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
 * 控制器，所设定了功能方法 
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
	public boolean isEdited = false;// 是否已经被修改过
	// file path which now is been reading
	public File txt = null;// 读写文件路径
	// file chooser dialog
	public static JFileChooser fileChooser = new JFileChooser();// 文件对话框
	// 包含<物种名，特征1，特征2，特征3，，...，特征n>
	public ArrayList<dataStructe> data = new ArrayList<dataStructe>();
	public ArrayList<adjacencyStructe> adjacency = new ArrayList<adjacencyStructe>();
	//物种名
	public String[] dataSpecial;
	//物种特征
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
	 * 文件管理模块
	 */

	// 新建文件
	// create new file
	public void createFile() {
		Object jta = null;
		fileChooser.setDialogTitle("新建");
		// 用户输入文件名，并新建该文件
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
				// 打开文件
				String cmd = "rundll32 url.dll FileProtocolHandler file:" + pathname; // 启动相应的windows程序来打开文件
				Process p = Runtime.getRuntime().exec(cmd);

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// 打开文件
	// open file
	public void openFile() {
		// 打开文件对话框
		// show open file dialog
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.showDialog(new JLabel(), "选择");
		file = fileChooser.getSelectedFile();

		try {
			data = read.loadData(file);
			
			// ArrayList<dataStructe> data = new ArrayList<dataStructe>();
			Scanner fileScanner = new Scanner(file);
			/*
			 * row 代表有row个物种 col 代表有col个特征
			 */
			rows = fileScanner.nextInt();
			cols = fileScanner.nextInt();

		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// 读取文件中的数据信息
		// 判断该数据文件有多少行（即多少个物种），多少列（即多少个特征）
		// file.getAbsolutePath();+txtfile.getSelectedFile().getName(); //文件的绝对路径
		mf.ShowtextArea.append('\n' + "Read file : " + file.getAbsolutePath() + '\n');
		mf.ShowtextArea.append("---------------Document content---------------" + '\n');

		try {
			// 打开文件
			String cmd = "rundll32 url.dll FileProtocolHandler file:" + file.getAbsolutePath(); // 启动相应的windows程序来打开文件
			Process p = Runtime.getRuntime().exec(cmd);
			// 读取文件
			String encoding = "GBK";
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
		} catch (Exception e1) {
			mf.ShowtextArea.append("读取文件内容出错" + '\n');
			e1.printStackTrace();
		}

		mf.ShowtextArea.append('\n' + "Data matrix has " + rows + " taxa, " + cols + " characters" + '\n'
				+ "Valid character-state symbols: 012345" + '\n' + "Missing data identified by '?'" + '\n'
				+ "Gaps identified by 'N'" + '\n');
		mf.ShowtextArea.append('\n' + "Processing of input file " + file.getAbsolutePath() + " completed."+'\n');

		//物种矩阵初始化
		dataSpecial = new String[rows];
		//物种特征初始化
		dataMatrix = new int[rows][cols];
		
		//数据输出
		for (int i = 0; i < rows; i++) {
			//物种名
			dataSpecial[i] = data.get(i).dataSpeci;

			for (int j = 0; j < cols; j++) {
				//物种特征
				dataMatrix[i][j] = data.get(i).dataMat[j];
			}
		}
	}
	public void openAdjacencyFile() {
		// 打开文件对话框
		// show open file dialog
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.showDialog(new JLabel(), "选择");
		file = fileChooser.getSelectedFile();

		try {
				adjacency = read.loadAdjacency(file);

			// ArrayList<dataStructe> data = new ArrayList<dataStructe>();
			Scanner fileScanner = new Scanner(file);
			/*
			 * row 代表有row个物种 col 代表有col个特征
			 */
			int rows = fileScanner.nextInt();
			int cols = fileScanner.nextInt();

		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		// 读取文件中的数据信息
		// 判断该数据文件有多少行（即多少个物种），多少列（即多少个特征）
		// file.getAbsolutePath();+txtfile.getSelectedFile().getName(); //文件的绝对路径
		mf.ShowtextArea.append('\n' + "Read file : " + file.getAbsolutePath() + '\n');
		mf.ShowtextArea.append("---------------Document content---------------" + '\n');

		try {
			// 打开文件
			String cmd = "rundll32 url.dll FileProtocolHandler file:" + file.getAbsolutePath(); // 启动相应的windows程序来打开文件
			Process p = Runtime.getRuntime().exec(cmd);
			// 读取文件
			String encoding = "GBK";
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
		} catch (Exception e1) {
			mf.ShowtextArea.append("读取文件内容出错" + '\n');
			e1.printStackTrace();
		}

		mf.ShowtextArea.append('\n' + "Adjacency matrix has "  + cols + " characters" + '\n'
				+ "Valid character-state symbols: 012345" + '\n' + "Missing data identified by '?'" + '\n'
				+ "Gaps identified by 'N'" + '\n');
		mf.ShowtextArea.append('\n' + "Processing of input file " + file.getAbsolutePath() + " completed."+'\n');


		//物种特征初始化
		adjacencyMatrix = new int[rows][cols];
		
		//数据输出
		for (int i = 0; i < rows; i++) {


			for (int j = 0; j < cols; j++) {
				//物种特征
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
		// 用户输入文件名，并新建该文件
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
				// 给log文件中写内容
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
	 * Edit编辑模块
	 */
	// Clear Display Buffer
	public void ClearDisplayBuffer() {
		// 出现弹窗
		int res = JOptionPane.showConfirmDialog(null, "是否清空output display buffer的内容", "是否继续",
				JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			mf.ShowtextArea.setText(""); // 点击“是”后执行这个代码块
		}
	}

	// Edit Display Buffer
	public void EditDisplayBuffer() {
		mf.ShowtextArea.setEditable(!mf.ShowtextArea.isEditable());
	}

	/*
	 * Data模块
	 */
	// 显示数据矩阵
	public void ShowDataMat() {
		//定义输出格式
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
		
		//数据输出
		for (int i = 0; i < rows; i++) {
			mf.ShowtextArea.append(data.get(i).dataSpeci);
			
			for (int nullNUM = 0; nullNUM <= stringwidth - data.get(i).dataSpeci.length(); nullNUM++)
				mf.ShowtextArea.append(" ");

			for (int j = 0; j < cols; j++) {
				if(data.get(i).dataMat[j] == -1)
				{// -1说明该数据缺失
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
		//定义输出格式
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
		
		//数据输出
		for (int i = 0; i < cols; i++) {
			mf.ShowtextArea.append(String.valueOf(i % 10));
			for (int nullNUM = 0; nullNUM <= stringwidth-1; nullNUM++)
				mf.ShowtextArea.append(" ");

			for (int j = 0; j < cols; j++) {
				
				if(adjacency.get(i).dataMat[j] == -1)
				{// -1说明该数据缺失
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
	//KNN插值处理
	public void KnnInterpolation()
	{
		int NTimes = 5;
		int MISRate = 2;
	        //读入数据
			int Data[][] = dataMatrix;
			int TData[][] =new int[Data.length][Data[0].length];
			for(int i=0;i<Data.length;i++) {
				TData[i] = Data[i].clone();
			}
			
				//人为的缺失数据处理
/*				ArtMiss art = new ArtMiss();
				int[][] MisDataLocation = art.generateXOY(Data, MISRate);
				int[][] ArtData = art.generateData(Data, MisDataLocation);*/
				//数据完成初始化的模式插补
				mode modeimputation = new mode();
				findMiss Miss =new findMiss();
				//找到缺失数据位置
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
				mf.ShowtextArea.append("KNN插补完成！KNN插补后的数据集为"+'\n'+'\n');
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
			   //计算插补的准确率
//				Accuracy calAccuracy = new Accuracy();
//
//				double accuracy = calAccuracy.calAccuracy(TData, onceImputationData,MISRate);
//				mf.ShowtextArea.append(String.format("插补准确率： %.2f",accuracy*100));
//				mf.ShowtextArea.append("%");
			

	}
	

	/*
	 * 分析模块
	 */

	// Simulated Annealing
	public void SimulatedAnnealing() {
		// TODO Auto-generated method stub
		int []out = new int[cols];
		//指定外类群, 默认为第一个物种的特征状态
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
		
		double normalCi = 0.1;//设置标准CI
		
		// bestTnum为最合适的几棵树
		int bestTnum = 1;
		int Tnum = 0;
		while (Tnum < bestTnum ) {
			int MaxGen = 10000; //设置最大迭代次数
		    int generate = 0;
		    while(generate < MaxGen) {
		        //扰动产生新解w0     
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
		        double diertaF = discribeTreenew.getCI() - discribeTree.getCI();//正数？越好
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
			mf.ShowtextArea.append('\n'+"最优树的拓扑结构："+ i.Topology+'\n');
			mf.ShowtextArea.append("评价指标："+'\n');
			mf.ShowtextArea.append("树长(Tree Length） = "+ i.Length+'\n');
			mf.ShowtextArea.append("一致性指数(Consistency index) = " +String.format("%.2f", i.CI)+'\n');
			mf.ShowtextArea.append("同塑性指数 (Homoplasy index) = " +String.format("%.2f", i.HI)+'\n');
			mf.ShowtextArea.append("保留性指数(Retention index) = " +String.format("%.2f", i.RI)+'\n');
			mf.ShowtextArea.append("校正一致性指数(Rescaled consistency index) =" +String.format("%.2f", i.RC)+'\n');
		}
		mf.ShowtextArea.append("其中："+'\n');
		for(int i = 0 ;i<rows;i++)
		{
			mf.ShowtextArea.append(String.format("%-3d", i)+"---"+dataSpecial[i]+'\n');
		}
		
		// 出现弹窗：是否保存.tree文件
		int res = JOptionPane.showConfirmDialog(null, "是否导出树文件", "是否继续",
				JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) {
			outputTre(); // 点击“是”后执行这个代码块
		}
	}
	// 是否导出树文件
	public void outputTre()
	{
		Object jta = null;
		fileChooser.setDialogTitle("保存树文件");
		// 用户输入文件名，并新建该文件
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
				// 给.tree文件中写内容
				FileWriter treefw = new FileWriter(pathname);
				
				//获取当前系统时间
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
	 * Tree模块
	 */
	// Show Tree
	public void showTree() {
		// 调用jar包中的主程序
		String jarPath = System.getProperty("user.dir") + "\\lib\\figtree-1.3.1.jar";
		try {
			// 打开文件
			String cmd = "rundll32 url.dll FileProtocolHandler file:" + jarPath; // 启动相应的windows程序来打开文件
			Process p = Runtime.getRuntime().exec(cmd);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/*
	 * 关于模块
	 */
	// about
	public void about() {
		// show the dialog for introduce the copyright of this appliaction
		JOptionPane.showMessageDialog(null, "一个关于生物建树的软件 " + "Copyright2018 Liu。email：1418685745@qq.com");
	}

	// 退出
	// exit method
	public void exit() {
		System.exit(0);
	}

	// 在构造函数中设置file的默认选项
	// make the default saving file path to desktop
	public Controller() {
		File path = new File(fileChooser.getCurrentDirectory().getParent() + "\\Desktop\\");
		fileChooser.setCurrentDirectory(path);
	}

}
