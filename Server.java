import java.net.*;
import java.io.*;

class Server {
    ServerSocket server;
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    // costructer
    public Server() {
        try {

            server = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("waiting");
            socket = server.accept();

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
                        System.out.println("client terminated the chat");
                        socket.close();
                        break;
                    }
                    System.out.println("Client : " + msg);
                 
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
        }; new Thread(r2).start();
    }


    public static void main(String[] args) {
        System.out.println("server is going to start");
        new Server();
    }
}