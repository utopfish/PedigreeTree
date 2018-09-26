package ImputationKNN;

public class IterationKNN {
	
	public int[][] iteration(int [][]data,int [][]anis){
		
		int[][] newData = new int[data.length][data[0].length];
		for(int i=0;i<data.length;i++) {
			newData[i] = data[i].clone();
		}
		int[][] trainData = new int[data.length-1][data[0].length];
		int[] target = new int[data.length-1];
		
		int len = anis.length;
		for(int i=0;i<len;i++) {
			//对于每一个缺失值，调整特征矩阵结构，得到训练集和目标样本
			int[][] NData = new int[data.length][data[0].length];
			for(int j=0;j<data.length;j++) {
				NData[j] = data[j].clone();
			}
			adjustData adjustData = new adjustData();
			trainData = adjustData.getData(NData, anis[i]);
			target = adjustData.getTargetData(NData, anis[i]);
			
			KNN knn = new KNN(); 
			Possibility like = new Possibility();
			like = knn.termsKnn(trainData, target);
			like.setAnis(anis[i]);
			like.toString();
			newData[anis[i][0]][anis[i][1]] = like.getValueClass();
		}
		return newData;		
	}
}

