import java.net.*;
import java.io.*;

public class Client {

    Socket socket;

    BufferedReader br;
    PrintWriter out;

    public Client() {
        try {

            System.out.println("Sending req to server");
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("connection done");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWriting();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startReading() {
        // thread-read krke deta rhega
        Runnable r1 = () -> {
            System.out.println("Reader started");
            try {
                while (true) {
                
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        System.out.println("server terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Server : " + msg);
                 
            }
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println("Connenction close");
            }
            
        };
        new Thread(r1).start();
    }

    public void startWriting() {
        // thread-data user s lega and use krega client ko
        Runnable r2 = () -> {
            System.out.println("Writer started");
            try {
                while (true && !socket.isClosed()) {
                

                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit"))
                    {
                        socket.close();
                        break;
                    }           
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        };
        new Thread(r2).start();
    }

    public static void main(String[] args) {
        System.out.println("clint is started");
        new Client();
        // System.out.println("nn");
    }
}
