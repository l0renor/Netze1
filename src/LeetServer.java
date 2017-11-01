import java.io.*;
import java.net.*;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;

public class LeetServer {
    public static final int PORT = 8082;

    public static void main(String[] args) {
        LeetServer server = new LeetServer();
        server.startServer(args[0]);


    }

    private void startServer(String host) {

        try (ServerSocket servSock = new ServerSocket(PORT)) {
            System.out.println("Server started.");
            while(true){
            try (Socket s = servSock.accept();
                 BufferedReader fromClient =
                         new BufferedReader(
                                 new InputStreamReader(s.getInputStream()));
                 BufferedWriter toClient =
                         new BufferedWriter(
                                 new OutputStreamWriter(s.getOutputStream()))) {

                String resource;

                do{
                    resource = fromClient.readLine();
                }while (!resource.contains("GET")); // only get the line with the resource

                String url = "http://" + host + resource.split(" ")[1]; // build adress
                Document doc;
                try {
                    doc = Jsoup.connect(url).get();//get document from th
                }catch (UnsupportedMimeTypeException e) {
                    s.close();
                    continue;
                }

                String html = doc.html();//get html from document
                html = RegexController.toLeet(html); // add leet and change image source
                toClient.write("HTTP/1.1 200 OK\r\n"); // send response
                toClient.write("Content-Type: text/html\r\n");
                toClient.write("Connection: close\r\n");
                toClient.write("\r\n");
                toClient.write(html);
                toClient.flush();
                System.out.println("Leet HTML has been sent");

                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
