package ImputationKNN;

public class GetMissPos {
	public int[][] GetPos(int [][]data,int MISRate){
		int[][] Pos = new int[((data.length*data[0].length)*MISRate)/100][2];
		System.out.println(Pos.length);
		System.out.println(data.length);
		System.out.println(data[0].length);
		int k = 0;
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[0].length; j++) {
				if (data[i][j] < 0) {
					Pos[k][0] = i;
					Pos[k][1] = j;
					k++;
				}
			}		
		}
		return Pos;		
	}
}
