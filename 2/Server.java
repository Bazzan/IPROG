import java.io.IOError;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.net.*;

public class Server {
    // static ServerSocket serverSocket;
    static ArrayList<ClientThread> clients;
    static ServerSocket serverSocket;
    static int portNumber = 4444;

    public static void main(String[] args) {
       serverSocket = null;

        try {
            serverSocket = new ServerSocket(portNumber);
            acceptClients();
        } catch (IOException e) {
            System.out.println("Could not listen on port " + portNumber);
            System.exit(1);
        }

    }

    public static void acceptClients() {
        clients = new ArrayList<ClientThread>();
        
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket);
                Thread thread = new Thread(client);
                thread.start();
                clients.add(client);

            } catch (IOException e) {
                System.out.println("Accept fail on " + portNumber);
            }
        }
    }
}