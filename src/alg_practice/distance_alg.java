package alg_practice;

public class distance_alg {// 求距离矩阵
	private int data[][];
	private int dis[][];

	public distance_alg(int data[][]) {
		this.data = data;
	}
	public int[][] getData() {
		return dis;
	}

	public void Do() {
		int row = data.length;
		int col = data[0].length;
		this.dis = new int[row][row];
		for (int i = 0; i < row; i++)
			for (int j = i + 1; j < row; j++) {
				int count1 = 0;// 相同衍征数
				int count2 = 0;// 不同衍征数
				for (int k = 0; k < col; k++) {
					if (data[i][k] == data[j][k] && data[i][k] != 0 && data[i][k] > 0 && data[j][k] > 0) {
						count1++;
					}
				}
				dis[i][j] = count1  ;
			}
	}
}