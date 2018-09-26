package ImputationKNN;

public class ArtMiss {
	//产生缺失数据的随机位点
	public int[][] generateXOY(int [][]data,int rate) {
		int x = data.length;
		int y = data[0].length;
		int n = (int)((x*y)*rate)/100;
		int[][] XOY = new int[n][2];
		for(int i=0;i<n;i++){
			XOY[i][0] =(int)(Math.random()*x);
			XOY[i][1] =(int)(Math.random()*y);
		}
		return XOY;
	}
	//根据随机位点产生缺失数据矩阵
	public int[][] generateData(int [][]data,int [][]XOY){
		int m = XOY.length;
		int[][] dataMiss = data.clone();
		for(int i=0;i<m;i++){
			dataMiss[XOY[i][0]][XOY[i][1]] = -1;
		}
		return dataMiss;
	}
	
}