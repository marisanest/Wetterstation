import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
			String[] csvArray = parseCSV("/Users/admin/Documents/workspace-java/Wetterstation/Temperaturen.csv");
			eliminateInvisibles(csvArray);
			
			String inputLine, outputLine;
			outputLine = "Bitte gebe ein Datum ein (Format: tt.mm.jj):";
			out.println(outputLine);
			
			inputLine = in.readLine();
	        
	        if(dateValidation(inputLine)){
	        	int index = findIndexWhereEqual(csvArray, inputLine);
	        	if(index != -1){
	        		if(tempComplete(getTemperatures(csvArray, index)))
	        			outputLine = getTemperatures(csvArray, index);
	        		else 
	        			outputLine = "ERROR: Temperaturangaben zu diesem Datum sind unvollständig!";
	        	}
				else
					outputLine = "ERROR: Das angegebene Datum existiert nicht!";
	        }
	        else
	        	outputLine = "ERROR: Das Datum wurde in einem falschen Format angegeben!";
			
			out.println(outputLine);
		}
	}
	
	private static String[] parseCSV(String filePath) throws FileNotFoundException {
		
		ArrayList<String> csvArrayList = new ArrayList<String>();

		Scanner scanner = new Scanner(new File(filePath));
        
		scanner.useDelimiter(";");
		
		while(scanner.hasNext())
			csvArrayList.add(scanner.next());
        
        scanner.close();
         
        return csvArrayList.toArray(new String[csvArrayList.size()]);
	}
	
	private static void eliminateInvisibles(String[] arr) {
		for(int j = 0; j < arr.length; j++) {
			arr[j] = arr[j].replaceAll("\\s+","");
			arr[j] = arr[j].replaceAll("\\n+","");
			arr[j] = arr[j].replaceAll("\\r+","");
			arr[j] = arr[j].replaceAll("\\t+","");
			arr[j] = arr[j].replaceAll("\\b+","");
			arr[j] = arr[j].replaceAll("\\f+","");
		}
	}
	
	private static int findIndexWhereEqual(String[] arr, String str) {
		int index = -1;
		for(int j = 0; j < arr.length ; j++) {
	        if(arr[j].equals(str)){
	        	index = j;
	        	break;
	        }
		}
		return index;
	}
	
	private static String getTemperatures(String[] arr, int index) {
		String temp = "";
		for(int k = 1; k <= 24; k++)
			temp += arr[index+k]+"; ";
		return temp;
	}
	
	private static boolean dateValidation(String date){
		
		Scanner scanner = new Scanner(date);
		scanner.useDelimiter("\\.");
		
		ArrayList<Integer> dateParts = new ArrayList<Integer>();

		while(scanner.hasNext()){
			String str = scanner.next();
        	try{
        		dateParts.add(Integer.parseInt(str));
        	} catch (Exception e){}
		}
        scanner.close();
        
        
        if(dateParts.size() == 3)
        	if(0 < dateParts.get(0) && dateParts.get(0) < 32)
        		if(0 < dateParts.get(1) && dateParts.get(1) < 13)
        			if(00 <= dateParts.get(2) && dateParts.get(2) <= 99)
        				return true;
        
        return false; 
	}
	
	private static boolean tempComplete(String data) {
		Scanner scanner = new Scanner(data);
	    ArrayList<Double> temperatures = new ArrayList<Double>();
	    
	    scanner.useDelimiter(";");
        
        while(scanner.hasNext()){
        	String str = scanner.next().replaceAll("\\s+","").replaceAll("\\n+","");
        	try{
        		temperatures.add(Double.parseDouble(str));
        	} catch (Exception e){}
        }
        scanner.close();
        
        return (temperatures.size() == 24);
	}
}
