package alg_practice;

public class Index {
	public int getMinLength(int [][]data) {
		int[] num = new int[data[0].length];
		int[][] NewData = convert(data);
		for (int i = 0; i < NewData.length; i++) {
			num[i] = getMax(NewData[i]);	
		}
		int sum = 0;
		for (int i = 0; i < num.length; i++) {
			sum += num[i];
		}
		return sum;
		
	}
	public int getMax(int []array) {
		int max = array[0];
		for (int i = 0; i < array.length; i++) {
			if (max < array[i]) {
				max = array[i];
			}
		}
		return max;
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
