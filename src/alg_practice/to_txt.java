package alg_practice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class to_txt {
	public static void contentToTxt(String filePath, String content) {  
        String str = new String(); //ԭ��txt����  
        String s1 = new String();//���ݸ���  
        try {  
            File f = new File(filePath);  
            if (f.exists()) {  
                System.out.print("�ļ�����");  
            } else {  
                System.out.print("�ļ�������");  
                f.createNewFile();// �������򴴽�  
            }  
            BufferedReader input = new BufferedReader(new FileReader(f));  
  
            while ((str = input.readLine()) != null) {  
                s1 += str + "\n";  
            }  
            System.out.println(s1);  
            input.close();  
            s1 += content;  
  
            BufferedWriter output = new BufferedWriter(new FileWriter(f));  
            output.write(s1);  
            output.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
  
        }  
    }  
}  

