package alg_practice;

import java.util.HashMap;
import java.util.Map;

public class data {
	public DiscribeTree BuildTree(int[] out,int[][] data) {
		/*
		 * String[] name 为物种名 ；int[] out 为外类群的特征状态 ； int[][] data 为物种的特征状态矩阵
		 * */
		String name[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42" };
		int max;
		Map resultMap = new HashMap();
		String result = "";
		Index index = new Index();
		int num = index.getMinLength(data);
		
		GIndex gIndex = new GIndex();
		int gnum = gIndex.GetG(data);
		//根据原始特征预处理数据，使得0——>祖征
		pre p = new pre(data);

		p.setOut(out);
		p.Do();
		
		int length = 0;
		alg a = new alg();
		a.setDataMat(data);
		a.setName(name);
		
		resultMap = a.Do();
		length = length + (Integer) resultMap.get("P");
		data = a.getNewData();
		name = a.getNewName();

		while (a.getMax() != 0) {
			a.setDataMat(data);
			a.setName(name);

			resultMap.clear();
			resultMap = a.Do();
			length = length + (int) resultMap.get("P");
			
			data = a.getNewData();
			name = a.getNewName();
		}

		double CI = (double)num/length;
		double HI = 1 - CI;
		double RI = (double)(gnum-length)/(gnum-num);
		double RC = CI * RI;
		
		if (resultMap.containsKey("result")) {
			result = resultMap.get("result").toString();			
		}
		DiscribeTree discribeTree = new DiscribeTree(result,CI,HI,RI,RC,length);		
		return discribeTree;
	}
}

