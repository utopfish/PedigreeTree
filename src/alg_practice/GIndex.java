package alg_practice;

public class GIndex {
	public int[][] convert(int [][]data){
		int[][] NewData = new int[data[0].length][data.length];
		for (int  i= 0;  i< data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				NewData[j][i] = data[i][j];
			}
		}
		return NewData;
	}
	
	public int[] total(int []array) {
		int[] calnum = new int[20];//liumeng 9月26号将大小由10 改到20
		for (int i = 0; i < array.length; i++) {
			if (array[i] >= 0) {
				calnum[array[i]] ++ ;
			}
		}
		return calnum;
	}
	public void getMax(int []array) {
		int max = array[0];
		int p = 0;
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
				p = i;
			}
		}
		array[p] = 0;//算少不管多，简约原则
	}
	
	public int GetG(int [][]data) {
		int sum = 0;
		int[][] NewData = convert(data);
		for (int i = 0; i < NewData.length; i++) {
			int[] calnum = this.total(NewData[i]);
			this.getMax(calnum);
			int n = 0;
			for (int j = 0; j < calnum.length; j++) {
				n += calnum[j];
			}
			sum += n;
		}		
		return sum;		
	}
}
