import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class LeetServer {


    public static void main(String[] args) {
        LeetServer server = new LeetServer();
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

                while(true) {
                   // System.out.println("oben");
                   // String clientResponse = "";
                    boolean flush = false;
                    for (String line = fromClient.readLine();
                         line != null && line.length() > 0;
                         line = fromClient.readLine()) {

                        System.out.println("Client says: " + line);
                        if (line.equals("Host: localhost:8082")) {
                            line = "Host: " + host;
                            System.out.println(line);
                        }
                        if(!line.contains("Accept-Encoding")) {
                            toServer.write(line);
                            toServer.write("\r\n");
                        }
                        flush = true;
                    }
                    if(flush) {
                        toServer.write("\r\n");
                        toServer.flush();
                        System.out.println("toServer");
                    }

                    flush = false;
                    for (String line = fromServer.readLine();
                         line != null;
                         line = fromServer.readLine()) {
                        System.out.println(line);
                        if (line.equals("Host: localhost:8082")) {
                            line = "Host: " + host;
                            System.out.println(line);
                        }
//                        if (line.equals("Connection: close")) {
//                            line = "Connection: keep-alive";
//                            System.out.println(line);
//                            System.out.println("-----------------------");
//                        }
                        toClient.write(line);
                        toClient.write("\r\n");
                        flush = true;


                    }
                    if(flush) {
                        toClient.write("\r\n");
                        toClient.flush();
                        System.out.println("toCLient");
                    }
                }
                    /*
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
                             line != null *//*&& line.length()>0*//*;
                             line = fromServer.readLine()) {
                            System.out.println(line);
                            result+= line;
                            result+="\r\n";
                            System.out.println("-----a----");
                        }
                        toClient.write(result);
                        toClient.flush();*/

                //}

            }
        }catch (Exception e) {

            e.printStackTrace();
        }

    }

}
