package ImputationKNN;

public class adjustData {
	//根据原矩阵得到完全数据的矩阵，即从原矩阵剔除含缺失值的行，得到训练数据
	public int[][] getData(int [][]data,int []Xoy){
		int[][] newData = new int[data.length-1][data[0].length];

		int s = 0;
		for(int i=0;i<data.length;i++) {
			int t = 0;
			if (i != Xoy[0])
			{
				for(int j=0;j<data[0].length;j++) {
					if(j != Xoy[1]) {
						newData[s][t] = data[i][j];
						t++;
					}
				}
				s++;
			}
		}	
		int r = 0;
		for(int k=0;k<data.length;k++) {
			if(k != Xoy[0]) {
				newData[r][data[0].length-1] = data[k][Xoy[1]];
				r++;
			}
		}
		return newData;	
	}
	
	//根据原矩阵得到含缺失数据的样本所在的行，并剔除缺失值所在的点,待测样本
	public int[] getTargetData(int [][]data,int []Xoy){
		int[] newTargetData = new int[data[0].length-1];
		
		int j = 0;
		for(int i=0;i<data[0].length;i++) {
			if(i != Xoy[1]) {
				newTargetData[j] = data[Xoy[0]][i];
				j++;
				}
			}	
		return newTargetData;	
	}

}
