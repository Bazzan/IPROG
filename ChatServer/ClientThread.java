package ChatServer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket socket;
    public Server server;
    private PrintWriter writer;

    ClientThread(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {

            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);

            String clientName = reader.readLine();
            server.addClientName(clientName);

            String serverMessage = "new client connected " + clientName;
            server.broadcast(serverMessage, this);

            String clientMessage;

            do{
                clientMessage = reader.readLine();
                serverMessage = "["+ clientName+ "]" + clientMessage;
                server.broadcast(serverMessage,this); 
            }while(!clientMessage.equals("exit"));

            
            
            


        } catch (Exception e) {
            // TODO: handle exception
        }

    }

    public void printClients() {
        if (server.hasUser()) {
            writer.println("connected Clients:" + server.getUserNames());
        }else{
            writer.println("No Clients");
        }
    }


    public void sendMessage(){
        writer.println(x);
    }

}