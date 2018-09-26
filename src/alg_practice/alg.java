package alg_practice;

import java.util.HashMap;
import java.util.Map;
import view.MainFrame;

public class alg {
	private int dataMat[][];
	private String name[];
	private int newData[][];
	private String newName[];
	int max;
	private MainFrame mf;
	
	public void setMainFrame(MainFrame mf) {
		this.mf = mf;
	}	
	public void setDataMat(int newMat[][]) {
		dataMat = newMat;
	}
	public void setName(String[] newName) {
		name = newName;
	}
	public int[][] getNewData() {
		return newData;
	}
	public String[] getNewName() {
		return newName;
	}
	public int getMax() {
		return max;
	}
	
	public Map Do() {
		int p = 0;;
		int row = dataMat.length;
		int col = dataMat[0].length;
		Map resultMap = new HashMap();
		distance_alg dis = new distance_alg(dataMat);
		dis.Do();
		int b[][] = dis.getData();

		this.max = 0;
		for (int i = 0; i < row; i++)
			for (int j = 0; j < row; j++)
				if (b[i][j] > this.max)
					this.max = b[i][j];

		if (max == 0) {
			for (int i = 0; i < dataMat.length; i++) {
				for (int j = 0; j < dataMat[0].length; j++) {
					if (dataMat[i][j] == 1) {
						 p++;//当无共同衍征时，统计各个种群独有衍征的数目
					}
				}			
			}
			StringBuffer result = new StringBuffer();
			result.append("(");
		
			for (int i = 0; i < newName.length; i++) {
				if (i == newName.length - 1) {
					result.append(newName[i]);
				}else {
					result.append(newName[i] + ",");
				}
				
			}
			result.append(")");		
			resultMap.put("result", result);
			resultMap.put("P", p);
			
			//return result;
		} else {

			merge_alg mer = new merge_alg(dataMat, name, b);
			mer.Do(max);
			p = mer.getN();
		
			int c[][] = mer.getData();
			name = mer.getName();

			int count = 0;
			for (int i = 0; i < row; i++)
				if (name[i] == "null")
					count++;
			int newLine = row - count;
			this.newName = new String[newLine];
			this.newData = new int[newLine][col];
			int lin = 0;
			for (int i = 0; i < row; i++) {
				if (name[i] != "null") {
					newName[lin] = name[i];
					for (int k = 0; k < col; k++)
						newData[lin][k] = c[i][k];
					lin++;
				}
			}		
			resultMap.put("P", p);
		}
		return resultMap ;
	}
}
