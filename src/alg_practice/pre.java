package alg_practice;

public class pre {// Ԥ����
	private int data[][];
	private int out[];

	public pre(int data[][]) {
		this.data = data;
	}
	public int[][] getData() {
		return data;
	}
	public void setOut(int out[]) {
		this.out = out;
	}

	public void Do() {
		int row = data.length;
		int col = data[0].length;
		for (int i = 0; i < col; i++)
			// ����Ⱥ�ĸ�����0
			if (out[i] != 0) {
				int temp = out[i];
				for (int j = 0; j < row; j++) {
					if (data[j][i] == temp) {
						data[j][i] = 0;
					} else if (data[j][i] == 0) {
						data[j][i] = temp;
					}
				}
			}
	}
}
