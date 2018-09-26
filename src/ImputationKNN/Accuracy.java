package ImputationKNN;

public class Accuracy {
	public double calAccuracy(int [][]realData,int [][]imputationData,int MISRate) {
		double count = 0.0;
		int numData = 0;
		double Accuracy = 0.0;
		for(int i=0;i<realData.length;i++) {
			for(int j=0;j<realData[0].length;j++){
				if(realData[i][j] != imputationData[i][j]) {
						count++;
				}
			}
		}
		numData = ((realData.length * realData[0].length)*MISRate)/100;
		Accuracy = 1-count/numData;
		return Accuracy;
	}
}