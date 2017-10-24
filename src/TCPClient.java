

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    public  String host ="www.mmix.cs.hm.edu";
	

	TCPClient(String host){
		this.host = host;
	}
	
	public String getLeetHTML(){
		String result = "";
		try(Socket s=new Socket(host, 80);
				BufferedWriter toServer = 
						new BufferedWriter(
								new OutputStreamWriter(
										s.getOutputStream()));
				BufferedReader fromServer =
						new BufferedReader(
								new InputStreamReader(
										s.getInputStream()))){
			toServer.write("GET /index.html HTTP/1.0\r\n");
			toServer.write("Host: "+host+"\r\n");
			toServer.write("\r\n");
			toServer.flush();
			boolean headFinished = false;
			for(String line=fromServer.readLine();
					line !=null /*&& line.length()>0*/;
					line = fromServer.readLine()){


			//	System.out.println(line);
				if(line.length()<4){
					headFinished = true;
				}
				if(headFinished) {
					result += line;
					result += "\n";
				}
	}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return RegexKontroller.toLeet(result);
	}
	
	
}
