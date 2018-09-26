package alg_practice;

import java.util.Random;
import java.util.Stack;

public class merge_alg {// �ϲ�����
	private int data[][];
	private String name[];
	private int dis[][];
	private Stack<String> resultStack = new Stack<String>();
	private String random[];
	
	private int n;

	public merge_alg(int data[][], String name[], int dis[][]) {
		this.data = data;
		this.name = name;
		this.dis = dis;
	}

	public int[][] getData() {
		return data;
	}

	public String[] getName() {
		return name;
	}

	public void Do(int Max) {// ����������ֵ
		int row = data.length;
		int col = data[0].length;
		int max_same = 0;// Ϊͬһ������Ŀ

		for (int x = 0; x < row; x++) {
			for (int y = 0; y < row; y++) {
				if (dis[x][y] == Max) {
					if (dis[x][y] > max_same)
						max_same = dis[x][y];
				}
			}
		}

		OUT: for (int x = 0; x < row; x++) {
			for (int y = 0; y < row; y++) {
				if (dis[x][y] == Max && dis[x][y] == max_same) {
					//************************************************��X��Y���ж��ٸ�����ֵ
					int s = 0;
					int t = 0;
					for (int i = 0; i < data[0].length; i++) {
						if (data[x][i] != 0 && data[x][i] > 0) {
							s++;
						}
						if (data[y][i] != 0 && data[y][i] > 0) {
							t++;
						}
					}
				
					for (int i = 0; i < col; i++) {
						data[x][i] = data[x][i] == data[y][i] ? data[x][i] : 0;

					}
					//***************************����ϲ����ж��ٸ�����ֵ
					int r=0;
					for (int i = 0; i < data[0].length; i++) {
						if (data[x][i] != 0 && data[x][i] > 0) {
							r++;
						}
					}
					int p = s + t -2*r;
					this.setN(p);

					for (int i = 0; i < col; i++)
						data[y][i] = -1;// ��y����Ϊ-1���
					name[x] = "(" + name[x] + "," + name[y] + ")";
					name[y] = "null";
					break OUT;

				}
			}
		}
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
}