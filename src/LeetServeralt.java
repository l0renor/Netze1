import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LeetServeralt {


    public static void main(String[] args) {
        LeetServeralt server = new LeetServeralt();
        server.startServer(args[0]);

    }
    private void startServer(String host) {

        try(ServerSocket servSock = new ServerSocket(8082)) {

            System.out.println("Server started, waiting for clients...");

            try (Socket server = servSock.accept();
                 BufferedReader fromClient =
                         new BufferedReader(
                                 new InputStreamReader(server.getInputStream()));
                 BufferedWriter toClient =
                         new BufferedWriter(
                                 new OutputStreamWriter(server.getOutputStream()));
                 Socket s = new Socket(host, 80);
                 BufferedWriter toServer =
                         new BufferedWriter(
                                 new OutputStreamWriter(
                                         s.getOutputStream()));
                 BufferedReader fromServer =
                         new BufferedReader(
                                 new InputStreamReader(
                                         s.getInputStream()))) {
                System.out.println("Got client connection!");
                for(String line = fromClient.readLine();
                    line != null && line.length()>0;
                    line = fromClient.readLine()){
                    System.out.println("Client says: "+line);
                }

                toServer.write("GET /index.html HTTP/1.1\r\n");
                toServer.write("Host: "+host+"\r\n");
                toServer.write("Connection: keep-alive");
                toServer.write("\r\n");
                toServer.flush();

//                String htmlContent = "halllol";
//                toClient.write("HTTP/1.0 200 OK\r\n");
//				toClient.write("Connection: keep-alive\r\n");
//                toClient.write("Content-length: "+htmlContent.length()+"\r\n");
//				toClient.write("\r\n");
//				toClient.write(htmlContent);

                    String result = "";
                    for (String line = fromServer.readLine();
                         line != null /*&& line.length()>0*/;
                         line = fromServer.readLine()) {
                        System.out.println(line);
                        if(line.equals("Connection: close")){
                            line = "Connection: keep-alive";
                            System.out.println(line);
                        }
                        result+= line;
                        result+="\r\n";



                    }
                    toClient.write(result);
                    toClient.flush();
                    System.out.println("-------------------------------------");
                    while (true){
                        String tmp = fromClient.readLine();
                        System.out.println("AA"+tmp);

                        toServer.write(tmp);
                        toServer.write("Host: "+host+"\r\n");
                        toServer.write("\r\n");
                        toServer.flush();
                        TimeUnit.SECONDS.sleep(1);
                        result = "";
                        System.out.println(fromServer.readLine());
                        for (String line = fromServer.readLine();
                             line != null /*&& line.length()>0*/;
                             line = fromServer.readLine()) {
                            System.out.println(line);
                            result+= line;
                            result+="\r\n";
                            System.out.println("-----a----");
                        }
                        toClient.write(result);
                        toClient.flush();

                }

            }
        }catch (Exception e) {

            e.printStackTrace();
        }

    }

}
