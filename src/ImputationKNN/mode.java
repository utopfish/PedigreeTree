package ImputationKNN;

public class mode {	
	//得到含缺失数据的那一列数据
	public int[] targetMode(int [][]data,int []anis) {
		int[] target =new int[data.length-1];
		int j = 0;
		
		for(int i=0;i<data.length;i++) {
			if(i != anis[0]) {
				target[j] = data[i][anis[1]];
				j++;

			}
		}
		return target;
	}
	
public static int getMost(int[] array){		
		for(int j=0;j<array.length;j++){
				array[j] = array[j]+1;
		}		
		int maxvalue = array[0];
		for(int j=0;j<array.length;j++){
			if(array[j]>maxvalue)
				maxvalue = array[j];
		}
		int[] result = new int[maxvalue+1];
		for (int i=0;i<array.length;i++) {
			result[array[i]]++;
		}		
		int max = result[1];
		int maxIndex = 1;
		for(int j=1;j<result.length-1;j++){
			if(result[j]>max)
				max = result[j];
				maxIndex = j;
		}
		return maxIndex-1;
	}
	
	public int[][] modeImputation(int[][] data,int [][]XOY){
		int len = XOY.length;
		for(int i=0;i<len;i++) {
			int[] array = this.targetMode(data, XOY[i]);
			int p = this.getMost(array);
			data[XOY[i][0]][XOY[i][1]] = p;
		}
		return data;
	}
}
