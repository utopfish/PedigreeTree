package ImputationKNN;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class read {

	int row = 1;
	int col = 0;
	char b[] = null;
	int data[][];

	public int[][] getData() {
		return data;
	}

	public void file() throws IOException {
		try {
			FileReader fr = new FileReader("C:\\Users\\HP\\Desktop\\4.txt");
			BufferedReader br = new BufferedReader(fr);
			// ����ͳ����������0��ʼ
			String a = br.readLine();
			b = a.toCharArray();
			while (br.readLine() != null) { // readLine()�����ǰ��ж��ģ�����ֵ�����е�����
				row++;
			}
			for (int i = 0; i < b.length; i++)
				if (b[i] != ' ')
					col++;
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		java.io.File file = new java.io.File("C:\\Users\\HP\\Desktop\\4.txt");
		Scanner in = new Scanner(file);
		int data1[][] = new int[row][col];

		for (int i = 0; i < row; i++)
			for (int j = 0; j < col; j++)
				data1[i][j] = in.nextInt();

		this.data = data1;
	}
}
