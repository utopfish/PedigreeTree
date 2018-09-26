package ImputationKNN;
/**  
 * KNN�����ٽ��㷨  
 * ʵ�ֲ��裺  
 * 1. ���ȼ�������е��ٽ�����ֵ  
 * 2. ���ٽ�ֵ��������  
 * 3. ѡ���ٽ�ֵ��С��K����  
 * 4. ͶƱѡ�����  
 */  
public class KNN {  	
	private static int K = 6;  
	private static int CLASSFIY=7; 
	
	public static int getK() {
		return K;
	}
	
	public static void setK(int k) {
	     K = k;
	}	
	public static int getCLASSFIY() {
		return CLASSFIY;
	}
	public static void setCLASSFIY(int cLASSFIY) {
		CLASSFIY = cLASSFIY;
	} 
	
	public Possibility termsKnn(int [][]data,int []distince) {
		// ���������  distince[]; 
		int[][] Data = new int[data.length][data[0].length-1];

		for(int i=0;i<data.length;i++) {
			for(int j=0;j<data[0].length-1;j++) {
				Data[i][j] = data[i][j];
			}
		}
        
		KNN knn = new KNN();  
        //������ķ������ά�����Ԫ�ص��ٽ�����  
        double[] questionDistinces = new double[Data.length]; 
        double[] sortDistinces = new double[Data.length];
        for(int i=0;i<Data.length;i++){  
            int[] item = Data[i];  
            questionDistinces[i] = knn.distince(item, distince);  
        }  

        //��ȡ�����ľ������        
        int nearest[] = knn.paraseKDistince(questionDistinces, K);   
        sortDistinces = knn.sortDistinceArray(questionDistinces);
        int y = data[0].length-1;
        Possibility index = knn.vote_weight(data,nearest, sortDistinces,y);  
        return index;	
	}

	
    //�����ٽ�����[����������]  
    public double distince(int []paraFirstData,int []paraSecondData){  
        double tempDistince = 0;  
        if((paraFirstData!=null && paraSecondData!=null) && paraFirstData.length==paraSecondData.length){  
            for(int i=0;i<paraFirstData.length;i++){  
            	if(paraFirstData[i] != paraSecondData[i]) {
            		tempDistince +=1;
            	}
            }  
        }else{  
            System.out.println("firstData �� secondData ���ݽṹ��һ��");  
        }  
        return tempDistince;  
    }  
      
    //���ٽ���������,��С����[�������ð������]  
    public double[] sortDistinceArray(double []paraDistinceArray){  
        if(paraDistinceArray!=null && paraDistinceArray.length>0){  
            for(int i=0;i<paraDistinceArray.length;i++){  
                for(int j=i+1;j<paraDistinceArray.length;j++){  
                    if(paraDistinceArray[i]>paraDistinceArray[j]){  
                        double temp = paraDistinceArray[i];  
                        paraDistinceArray[i] = paraDistinceArray[j];  
                        paraDistinceArray[j] = temp;  
                    }  
                }  
            }  
        }  
        return paraDistinceArray;  
    }  
      
    //��ȡ�ٽ�ֵ�����У��ӽ���Զ��ȡk��ֵΪ������  
    public double[] paraseKDistince(double[] sortedDistinceArray,String sortTypeStr,int k){  
        double[] kDistince = new double[k];  
        if(SortType.ASC.equals(sortTypeStr)){  
            for(int i=0;i<k;i++){  
                kDistince[i] = sortedDistinceArray[i];  
            }  
        }  
        if(SortType.DES.equals(sortTypeStr)){  
            for(int i=0;i<k;i++){  
                kDistince[i] = sortedDistinceArray[sortedDistinceArray.length-i-1];  
            }  
        }  
          
        return kDistince;  
    }  
      
    //��ȡ�ٽ������е�K�ľ�����±�����  
    public int[] paraseKDistince(double[] distinceArray,int k){  
        double[] tempDistince = new double[k+2];  
        int[] tempNearest = new int[k+2];  
        //��ʼ����������  
        tempDistince[0] = Double.MIN_VALUE;  
        for(int i=1;i<k+2;i++){  
            tempDistince[i] = Double.MAX_VALUE;  
            tempNearest[i] = -1;  
        }  
        //׼��ɸѡ�ٽ�����  
        for(int i=0;i<distinceArray.length;i++){  
            for(int j=k;j>=0;j--){  
                if(distinceArray[i]<tempDistince[j]){  
                    tempDistince[j+1] = tempDistince[j];  
                    tempNearest[j+1] = tempNearest[j];  
                }else{  
                    tempDistince[j+1] = distinceArray[i];  
                    tempNearest[j+1] = i;  
                    break;  
                }  
            }  
        }  
        int[] returnNearests = new int[k];  
        for (int i = 0; i < k; i++) {  
            returnNearests[i] = tempNearest[i + 1];  
        }  
        return returnNearests;  
    }  
      
    //�õ��Ǳ��Ӧ�ķ���  ,indexY��Ǵ��岹�������ڵ��У���Ϊ���ǩ��
    public int getClasssify(int [][]data,int indexX,int indexY){  
        return data[indexX][indexY];  
    }  
    
    //����ǰK��ֵ��Ȩ��
    public double[] calWeight(double[] sortDistinces) {
    	double[] weight = new double[K];
        for(int i=0;i<K;i++) {
        	if(sortDistinces[K-1] - sortDistinces[0] == 0) {
        		weight[i] = 1.0;
        	}else {
        		weight[i] = (sortDistinces[K-1] - sortDistinces[i])/(sortDistinces[K-1] - sortDistinces[0]);
        	}
        }
    	return weight;
    }
    //����Ȩ�ضԷ������ͶƱ�õ����[�õ�����Ʊ�����ķ���]
    public Possibility vote_weight(int [][]data,int[] nearestIndex,double[] sortDistinces,int indexY){ 
    	
        double[] votes = new double[CLASSFIY]; 
        double[] weight = calWeight(sortDistinces);
        for(int i=0;i<nearestIndex.length;i++){          	
            votes[getClasssify(data,nearestIndex[i],indexY)] = votes[getClasssify(data,nearestIndex[i],indexY)] + weight[i];
        }    
        Possibility like = new Possibility();
        double[] votes_nomal = new double[votes.length];
        int tempMajority = -1;  
        double tempMaximalVotes = -1; 
        double sum = this.sum(0, votes);
        for(int i=0;i < votes.length;i++) {
        	votes_nomal[i] = votes[i]/sum;
        	
        	 if (votes_nomal[i] > tempMaximalVotes) {  
                 tempMaximalVotes = votes_nomal[i];  
                 tempMajority = i;  
             }  
        }
        like.setChance(votes_nomal);
        like.setValueClass(tempMajority); 
        return like;
    }
    public static double sum(int i, double[] arr){
    	if(i < arr.length - 1)//���ص�ǰ��ֵ + ʣ�µ���һ�������еĺ�
    	    return arr[i] + sum(i+1, arr);
    	else return arr[i];//ֻ��һ���ˣ�����ֻ��Ҫ���ص�ǰ��
    }
}
