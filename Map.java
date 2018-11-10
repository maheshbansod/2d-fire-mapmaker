
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
	
	int[][] layout;
	List<Integer> assets; //change data type to store image
	
	Map() {
		assets = new ArrayList<>();
	}
	
	Map(int [][]layout) {
		this.layout = layout;
	}
	
	void writeTo(String filename) {
		try {
			DataOutputStream outfile = new DataOutputStream(new FileOutputStream(filename));
			
			int m = layout.length;
			int n = layout[0].length;
			outfile.writeInt(m);
			outfile.writeInt(n);
			for(int i=0;i<m;i++) {
				for(int j=0;j<n;j++) {
					outfile.writeInt(layout[i][j]);
				}
			}
			
			outfile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void readFrom(String filename) {
		try {
			DataInputStream infile = new DataInputStream(new FileInputStream(filename));
			
			int m = infile.readInt();
			int n = infile.readInt();
			
			layout = new int[m][n];
			
			for(int i=0;i<m;i++) {
				for(int j=0;j<n;j++) {
					layout[i][j]=infile.readInt();
				}
			}
			
			infile.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void displayMatrix() {
		int m = layout.length,n=layout[0].length;
		
		for(int i=0;i<m;i++) {
			for(int j=0;j<n;j++) System.out.print(layout[i][j]+" ");
			System.out.println();
		}
		
	}
	
	public static void main(String[] args) {
		//TODO create new map here
		
		Scanner sc = new Scanner(System.in);
			System.out.println("Enter 4x4 matrix: ");
			int arr[][] = new int[4][4];
			for(int i=0;i<4;i++) {
				for(int j=0;j<4;j++) {
					arr[i][j] = sc.nextInt();
				}
			}
			sc.nextLine();
			
			System.out.println("Enter output file name: ");
			String outfilename = sc.nextLine();
			
			Map map = new Map(arr);
			map.writeTo(outfilename);
			
			System.out.println("Enter input file name to load the map: ");
			String infilename = sc.nextLine();
			
			Map map2 = new Map();
			
			map2.readFrom(infilename);
			
			map2.displayMatrix();
			
			sc.close();
	}

}
