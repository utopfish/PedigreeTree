package controller;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class findMiss {
public int[][] location(int[][]data){//获取缺失数据的位置
	List<Integer>  listx = new ArrayList<Integer>();
	List<Integer>  listy = new ArrayList<Integer>();
	for(int i=0;i<data.length;i++)
	{
		for(int j=0;j<data[0].length;j++){
			if(data[i][j]==-1){
				listx.add(i);
				listy.add(j);
			}
		}
	}
	int location[][]=new int[listx.size()][2];
	for(int i=0;i<location.length;i++){
		location[i][0]=listx.get(i);
		location[i][1]=listy.get(i);
	}
	return location;
	
}
}
