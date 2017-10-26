import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;

public class LeetServer {
    public static final int PORT = 8082;

    public static void main(String[] args) {
        LeetServer server = new LeetServer();
        // server.startServer(args[0]);
        server.startServer("mmix.cs.hm.edu");

    }

    private void startServer(String host) {

        try (ServerSocket servSock = new ServerSocket(PORT)) {
            System.out.println("Server started, waiting for clients...");
            while(true){
            try (Socket s = servSock.accept();
                 BufferedReader fromClient =
                         new BufferedReader(
                                 new InputStreamReader(s.getInputStream()));
                 BufferedWriter toClient =
                         new BufferedWriter(
                                 new OutputStreamWriter(s.getOutputStream()))) {
                System.out.println("Got client connection!");

                String resource;

                do{
                    resource = fromClient.readLine();
                }while (!resource.contains("GET"));

                String url = "http://" + host + resource.split(" ")[1];
                System.out.println(url);

                Document doc;
                try {
                    doc = Jsoup.connect(url).get();
                }catch (UnsupportedMimeTypeException e) {
                    s.close();
                    continue;
                }

                String html = doc.html();
                html = RegexController.toLeet(html);
                System.out.println(html);
                toClient.write("HTTP/1.1 200 OK\r\n");
                toClient.write("Content-Type: text/html\r\n");
                toClient.write("Connection: close\r\n");
                toClient.write("\r\n");
                toClient.write(html);
                toClient.flush();


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
