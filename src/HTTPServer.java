

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class HTTPServer {
	public static final int PORT = 8082;

	/**
	 * @param args TargetHost
	 */
	public static void main(String[] args) {
		HTTPServer server = new HTTPServer();
		server.startServer(args[0]);

	}

	private void startServer(String host) {
		
		try(ServerSocket servSock = new ServerSocket(PORT)){
			
			System.out.println("Server started, waiting for clients...");
			
			try(Socket s = servSock.accept();
					BufferedReader fromClient = 
							new BufferedReader(
									new InputStreamReader(s.getInputStream()));
					BufferedWriter toClient =
							new BufferedWriter(
									new OutputStreamWriter(s.getOutputStream()))){
				System.out.println("Got client connection!");
			
				for(String line = fromClient.readLine();
						line != null && line.length()>0;
						line = fromClient.readLine()){
					System.out.println("Client says: "+line);
				}
				
				String testString = new TCPClient(host).doRequest();
				
				toClient.write("HTTP/1.0 200 OK\r\n");
				toClient.write("Content-length: "+testString.length()+"\r\n");
				toClient.write("\r\n");
				toClient.write(testString);
				toClient.flush();
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

}
