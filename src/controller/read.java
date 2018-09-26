package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class read {

	 static ArrayList<dataStructe> loadData(File file) throws FileNotFoundException{
		Scanner fileScanner = new Scanner(file);
		
		int rows = fileScanner.nextInt();
		int cols = fileScanner.nextInt();
		
		String datalinestr ;
		 /* row ������row������
		  * col ������col������
		  * */
		ArrayList<dataStructe> data = new ArrayList<dataStructe>(rows); 
		
		//��ϲ�ͬ�ķ����Ӽ��̶�ȡ�ַ�����ʱ����ʱ���ò�����һ�������nextLine()���ã���ȥ���еĽ�������
		String nextline = fileScanner.nextLine();
		
		ArrayList list = new ArrayList();
		
		//System.out.println("correct answer :" );	
		for(int i =0; i<rows; i++) {
			//��ʼ��dataMat
			dataStructe dataline = new dataStructe(); 
			dataline.dataMat = new int[cols];
			
			datalinestr = fileScanner.nextLine();
			
			String []strArray = datalinestr.split("\\s+");
			dataline.dataSpeci = strArray[0];
			//System.out.print(strArray[0]+"   ");
			for (int j = 1;j<strArray.length;j++)
			{
				
				try {
						dataline.dataMat[j-1] = Integer.parseInt(strArray[j]);
					//System.out.print(dataline.dataMat[j-1]+"   ");
				} catch (NumberFormatException e) {
				    if(strArray[j].charAt(0)=='?'){
				    	dataline.dataMat[j-1] =-1;
					}else if(strArray[j].charAt(0)=='N'){
						dataline.dataMat[j-1] =-2;
					}
					//e.printStackTrace();
				}

			}
			
			data.add(i,dataline);
		}

		return data;

	}
	 static ArrayList<adjacencyStructe> loadAdjacency(File file) throws FileNotFoundException{
		Scanner fileScanner = new Scanner(file);
		
		int rows = fileScanner.nextInt();
		int cols = fileScanner.nextInt();
		
		String datalinestr ;
		 /* row ������row������
		  * col ������col������
		  * */
		ArrayList<adjacencyStructe> data = new ArrayList<adjacencyStructe>(rows); 
		
		//��ϲ�ͬ�ķ����Ӽ��̶�ȡ�ַ�����ʱ����ʱ���ò�����һ�������nextLine()���ã���ȥ���еĽ�������
		String nextline = fileScanner.nextLine();
		
		ArrayList list = new ArrayList();
		
		//System.out.println("correct answer :" );	
		for(int i =0; i<rows; i++) {
			//��ʼ��dataMat
			adjacencyStructe dataline = new adjacencyStructe(); 
			dataline.dataMat = new int[cols];
			
			datalinestr = fileScanner.nextLine();
			
			String []strArray = datalinestr.split("\\s+");
			dataline.datacharacter = strArray[0];
			//System.out.print(strArray[0]+"   ");
			//datacharacter�ڴ����ã�������û��������
			for (int j = 0;j<strArray.length;j++)
			{
				
				try {
					if(strArray[j].charAt(0)=='N')
						dataline.dataMat[j] =-2;
					else
						dataline.dataMat[j] = Integer.parseInt(strArray[j]);
					//System.out.print(dataline.dataMat[j-1]+"   ");
				} catch (NumberFormatException e) {
				   
					e.printStackTrace();
				}

			}
			
			data.add(i,dataline);
		}

		return data;

	}
}
