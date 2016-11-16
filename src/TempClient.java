import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class TempClient {

	public static void main(String[] args) throws IOException {
	
		Socket socket = new Socket("localhost", 1234);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String fromServer, fromUser;
		
		fromServer = in.readLine();
		System.out.println("Server: " + fromServer);
		
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		
		fromUser = stdIn.readLine();
	    
		System.out.println("Client: " + fromUser);
		out.println(fromUser);
		
		String response = "";
	    
	    while ((fromServer = in.readLine()) != null) {
	    	response += fromServer;
	    }
	    
	    System.out.println("Server: " + response);
	    
	    if(!response.contains("ERROR")){
	    	ArrayList<Double> temperatures = scannResponse(response);
		    
		    if(temperatures.size() == 24){
		    	System.out.println("Client: Durchschnittliche Temperatur " + calcAverage(temperatures));
		    	System.out.println("Client: Minimal Temperatur " + Collections.min(temperatures));
		    	System.out.println("Client: Maximal Temperatur " + Collections.max(temperatures));
		    }
	    }
		socket.close();
	}
	
	private static ArrayList<Double> scannResponse(String response) {
		
		Scanner scanner = new Scanner(response);
	    ArrayList<Double> temperatures = new ArrayList<Double>();
	    
	    scanner.useDelimiter(",");
        
        while(scanner.hasNext()){
        	String str = scanner.next().replaceAll("\\s+","").replaceAll("\\n+","");
        	try{
        		temperatures.add(Double.parseDouble(str));
        	} catch (Exception e){}
        }
        scanner.close();
        
        return temperatures;
	}
	
	private static double calcAverage(ArrayList<Double> temperatures) {
		double res = 0;
		
        for(int j = 0; j < temperatures.size(); j++)
        	res +=temperatures.get(j);
        	
        res = res/temperatures.size();
        
        return res;
	}
}
