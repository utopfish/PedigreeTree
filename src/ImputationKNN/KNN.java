package ImputationKNN;
/**  
 * KNN又名临近算法  
 * 实现步骤：  
 * 1. 首先计算出所有的临近距离值  
 * 2. 对临近值进行排序  
 * 3. 选出临近值最小的K个数  
 * 4. 投票选出结果  
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
		// 待求解样本  distince[]; 
		int[][] Data = new int[data.length][data[0].length-1];

		for(int i=0;i<data.length;i++) {
			for(int j=0;j<data[0].length-1;j++) {
				Data[i][j] = data[i][j];
			}
		}
        
		KNN knn = new KNN();  
        //求出求解的分类与二维数组间元素的临近距离  
        double[] questionDistinces = new double[Data.length]; 
        double[] sortDistinces = new double[Data.length];
        for(int i=0;i<Data.length;i++){  
            int[] item = Data[i];  
            questionDistinces[i] = knn.distince(item, distince);  
        }  

        //获取排序后的聚类矩阵        
        int nearest[] = knn.paraseKDistince(questionDistinces, K);   
        sortDistinces = knn.sortDistinceArray(questionDistinces);
        int y = data[0].length-1;
        Possibility index = knn.vote_weight(data,nearest, sortDistinces,y);  
        return index;	
	}

	
    //计算临近距离[除开求解分类]  
    public double distince(int []paraFirstData,int []paraSecondData){  
        double tempDistince = 0;  
        if((paraFirstData!=null && paraSecondData!=null) && paraFirstData.length==paraSecondData.length){  
            for(int i=0;i<paraFirstData.length;i++){  
            	if(paraFirstData[i] != paraSecondData[i]) {
            		tempDistince +=1;
            	}
            }  
        }else{  
            System.out.println("firstData 与 secondData 数据结构不一致");  
        }  
        return tempDistince;  
    }  
      
    //对临近距离排序,从小到大[这里采用冒泡排序]  
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
      
    //获取临近值数组中，从近到远获取k个值为新数组  
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
      
    //获取临近距离中的K的距离的下标数组  
    public int[] paraseKDistince(double[] distinceArray,int k){  
        double[] tempDistince = new double[k+2];  
        int[] tempNearest = new int[k+2];  
        //初始化两个数组  
        tempDistince[0] = Double.MIN_VALUE;  
        for(int i=1;i<k+2;i++){  
            tempDistince[i] = Double.MAX_VALUE;  
            tempNearest[i] = -1;  
        }  
        //准备筛选临近距离  
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
      
    //得到角标对应的分类  ,indexY标记待插补数据所在的列，即为类标签。
    public int getClasssify(int [][]data,int indexX,int indexY){  
        return data[indexX][indexY];  
    }  
    
    //计算前K个值的权重
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
    //依据权重对分类进行投票得到结果[得到分类票数最多的分类]
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
    	if(i < arr.length - 1)//返回当前的值 + 剩下的那一部分所有的和
    	    return arr[i] + sum(i+1, arr);
    	else return arr[i];//只有一个了，所以只需要返回当前的
    }
}
