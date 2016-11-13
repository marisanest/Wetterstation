import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TempClient {

	public static void main(String[] args) throws IOException {
	
		Socket socket = new Socket("localhost", 1234);
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String fromServer, fromUser;
		
		while ((fromServer = in.readLine()) != null) {
		    System.out.println("Server: " + fromServer);

		    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			fromUser = stdIn.readLine();
		    if (fromUser != null) {
		        System.out.println("Client: " + fromUser);
		        out.println(fromUser);
		    }
		}	
		socket.close();
	}
}
