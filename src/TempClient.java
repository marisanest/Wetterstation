import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
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
	    
	    ArrayList<Integer> temperatures = scannResponse(response);
	    
	    if(temperatures.size() == 24)
	        System.out.println("Client: Durchschnittliche Temperatur " + calcAverage(temperatures));
	    else 
	    	System.out.println("Client: Durchschnittliche Temperatur konnte nicht berechnet werden!");
	    
		socket.close();
	}
	
	private static ArrayList<Integer> scannResponse(String response) {
		
		Scanner scanner = new Scanner(response);
	    ArrayList<Integer> temperatures = new ArrayList<Integer>();
	    
	    scanner.useDelimiter(",");
        
        while(scanner.hasNext()){
        	String str = scanner.next().replaceAll("\\s+","").replaceAll("\\n+","");
        	try{
        		temperatures.add(Integer.parseInt(str));
        	} catch (Exception e){}
        }
        scanner.close();
        
        return temperatures;
	}
	
	private static double calcAverage(ArrayList<Integer> temperatures) {
		double res = 0;
		
        for(int j = 0; j < temperatures.size(); j++)
        	res +=temperatures.get(j);
        	
        res = res/temperatures.size();
        
        return res;
	}
}
