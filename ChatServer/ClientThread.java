
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread extends Thread implements Runnable {

    public Socket socket;
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

            printClients();

            String clientName = reader.readLine();
            server.addClientName(clientName);

            String serverMessage = "new client connected " + clientName;
            server.sendMessageToClients(serverMessage, this);

            String clientMessage;

            do{
                clientMessage = reader.readLine();
                serverMessage = "[ "+ clientName+ " ]: " + clientMessage;
                server.sendMessageToClients(serverMessage,this); 
            }while(!clientMessage.equals("exit"));

            server.removeClientFormSet(clientName, this);
            input.close();
            reader.close();
            writer.close();
            socket.close();
            
            
            serverMessage = clientName + " went away";
            server.sendMessageToClients(serverMessage, this);
            
            


        } catch (IOException e) {
            System.out.println(e.getMessage() + " -> error ClientThread");
            e.printStackTrace();
        }

    }

    public void printClients() {
        if (server.hasClient()) {
            writer.println("connected Clients:" + server.getClientNames());
        }else{
            writer.println("No Clients");
        }
    }


    public void sendMessage(String message){
        writer.println(message);
    }

}