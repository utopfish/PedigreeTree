package alg_practice;

public class polarization {
	public int[] polar(int[][] data) {
		int n = data.length;
		int[] outg = new int[data[0].length];
		int[][] NewData = convert(data);
		for (int i = 0; i < NewData.length; i++) {
			int s = (int) (Math.random()*n);
			while(NewData[i][s] == -1) {//-1表示不可适用状态
				s = (int) (Math.random()*n);			
			}
			outg[i] = NewData[i][s];
		}
		return outg;
	}
	public int[][] convert(int [][]data){
		int[][] NewData = new int[data[0].length][data.length];
		for (int  i= 0;  i< data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				NewData[j][i] = data[i][j];
			}
		}
		return NewData;
	}
}
