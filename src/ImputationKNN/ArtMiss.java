package ImputationKNN;

public class ArtMiss {
	//����ȱʧ���ݵ����λ��
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
	//�������λ�����ȱʧ���ݾ���
	public int[][] generateData(int [][]data,int [][]XOY){
		int m = XOY.length;
		int[][] dataMiss = data.clone();
		for(int i=0;i<m;i++){
			dataMiss[XOY[i][0]][XOY[i][1]] = -1;
		}
		return dataMiss;
	}
	
}