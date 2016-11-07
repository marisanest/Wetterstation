import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		try ( 
			ServerSocket serverSocket = new ServerSocket(1234);
		    Socket clientSocket = serverSocket.accept();
		    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		)	
		{
			String inputLine, outputLine;
			outputLine = "Bitte gebe ein Datum ein (Format: tt.mm.jj):";
			out.println(outputLine);
			
			String[] csvFile = new String[200];
			Scanner scanner = new Scanner(new File("/Users/admin/Git/Wetterstation/Temperaturen2.csv"));
	        scanner.useDelimiter(",");
	        int i = 0;
	        while(scanner.hasNext()){
	        	csvFile[i] = scanner.next();
	        	i++;
	        }
	        scanner.close();

	        String temp = "";
	        int index = 0;
	        inputLine = in.readLine();
	        
	        inputLine = inputLine.replaceAll("\\s+","");
	        inputLine = inputLine.replaceAll("\\n+","");
	        
	        System.out.println("INPUTLINE: Länge: "+inputLine.length()+" Inhalt: "+inputLine);
	        
			for(int j = 0; j < csvFile.length && csvFile[j] != null && !csvFile[j].equals(inputLine); j++) {
				
				csvFile[j] = csvFile[j].replaceAll("\\s+","");
		        csvFile[j] = csvFile[j].replaceAll("\\n+","");
		        
		        System.out.println("CSV-ARRAY: Länge: "+csvFile[j].length()+" Inhalt: "+csvFile[j]);
		        
		        index = j;
			}
			
			if(csvFile[index].equals(inputLine)){
				
				outputLine = "Bitte gebe ein Uhrzeit ein (Format: ss):";
				out.println(outputLine);
				
				inputLine = in.readLine();
				System.out.println(inputLine);
				
				switch (inputLine){
					case "0":	temp = csvFile[index+1];
								break;
					case "1":	temp = csvFile[index+2];
								break;
					case "2":	temp = csvFile[index+3];
								break;
					case "3":	temp = csvFile[index+4];
								break;
					case "4":	temp = csvFile[index+5];
								break;
					case "5":	temp = csvFile[index+6];
								break;
					case "6":	temp = csvFile[index+7];
								break;
					case "7":	temp = csvFile[index+8];
								break;
					case "8":	temp = csvFile[index+9];	
								break;
					case "9":	temp = csvFile[index+10];
								break;
					case "10":	temp = csvFile[index+11];
								break;
					case "11":	temp = csvFile[index+12];
								break;
					case "12":	temp = csvFile[index+13];
								break;
					case "13":	temp = csvFile[index+14];
								break;
					case "14":	temp = csvFile[index+15];
								break;
					case "15":	temp = csvFile[index+16];
								break;
					case "16":	temp = csvFile[index+17];
								break;
					case "17":	temp = csvFile[index+18];
								break;
					case "18":	temp = csvFile[index+19];
								break;
					case "19":	temp = csvFile[index+20];
								break;
					case "20":	temp = csvFile[index+21];
								break;
					case "21":	temp = csvFile[index+22];
								break;
					case "22":	temp = csvFile[index+23];
								break;
					case "23":	temp = csvFile[index+24];
								break;
					default: 	break;
				}
			} 
			else {
				outputLine = "Datum nicht vorhanden!";
				out.println(outputLine);	
			}
			
			if(temp.equals(""))
				outputLine = "Uhrzeit nicht vorhanden!";
			else
				outputLine = "Die Temperatur betraegt: "+temp;
			
			out.println(outputLine);
		}
	}
}
