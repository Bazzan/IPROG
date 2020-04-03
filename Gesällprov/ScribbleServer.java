import java.net.*;
import java.util.HashSet;
import java.util.Set;
import java.io.*;

public class ScribbleServer {
    private int port;
    private Set<String> clientNames = new HashSet<>();
    private Set<ClientThread> clientThreads = new HashSet<>();


    public ScribbleServer(int port){
        this.port = port;
    }

    public ScribbleServer(){
        this.port = 2000;
    }

    public void runServer(){
        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Server Listening to: " + port);


            while(true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("New user connected: -> " + clientSocket.getInetAddress());
                ClientThread newClient = new ClientThread(clientSocket, this);
                  
                clientThreads.add(newClient);
                newClient.start();
            
            }
            
        
        
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
    }
    public static void main(String[] args) {
        // int portNumber = 2000;
        // serverSocket = null;
        // try {
        //     serverSocket = new ServerSocket(portNumber);
        //     acceptClients();
        // } catch (Exception e) {
        //     System.err.println("can not listen on " + portNumber);
        //     System.out.println(e);
        // }

        if(args.length == 0){
            ScribbleServer server = new ScribbleServer();
            server.runServer();
        }else if(args.length == 1){
            ScribbleServer server = new ScribbleServer(Integer.parseInt(args[0]));
            server.runServer();
        }



    }

public synchronized void  sendMessageToClients(String message, ClientThread clientToExclude){
    System.out.println(clientToExclude.socket.getRemoteSocketAddress() + "> " + message );

    for(ClientThread client : clientThreads){
        if( client != clientToExclude){
            client.sendMessage(message);

        }
    }
}
    

    public synchronized void addClientName(String clientName){
        clientNames.add(clientName);
    }
    public synchronized void removeClientFormSet(String clientName, ClientThread client){
        clientNames.remove(client);
        System.out.println("removed " + clientName);
    }

    public boolean hasClient(){
        return !this.clientNames.isEmpty();
    }

    public Set<String> getClientNames(){
        return this.clientNames;
    }


}