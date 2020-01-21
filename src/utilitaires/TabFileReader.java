package utilitaires;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class TabFileReader {
	
	private static String[][] words=null;
	private static int nrow,ncol;
	
	public static int ncol() { return ncol; }
	public static int nrow() { return nrow; }	
	public static String wordAt(int i, int j){
		return words[i][j];
	}
	
	  public static void readTextFile(String fileName,char separator,String packageName){
		  words=new String[1000][100];
			String line;
			String fullfilename;
			if (packageName==null) fullfilename=fileName;
			else  fullfilename="./"+packageName+"/"+fileName;
			int nb=0;
			try {
				BufferedReader in= new BufferedReader(new FileReader(fullfilename));
			while ((line = in.readLine()) != null) {
				String []fields=line.split(""+separator);
				if (ncol<fields.length) ncol=fields.length;
				for (int j=0;j<fields.length;j++) words[nb][j]=fields[j];
				nb++;
			}
			nrow=nb;
			in.close();
			}

			catch(IOException e){
				 System.out.println("file: " +fullfilename+ " does not exist."); 	
				 System.exit(-1);
			}     		  
	  }

	public static void main(String[] args) {
			String filename="departs.txt";
			TabFileReader.readTextFile(filename,'\t',"data");
			for (int i=0;i<TabFileReader.nrow();i++){
				for (int j=0; j<TabFileReader.ncol();j++)
					System.out.print(TabFileReader.wordAt(i,j)+"\t");
				System.out.println();
			}
	}

}
