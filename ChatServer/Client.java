package ChatServer;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{


    private String hostName;
    static private int port;
    private String clientName;

    public Client(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }


    public void runClient(){
        try {
            Socket socket = new Socket(hostName, port);
            System.out.println("trying to connect");
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        
        
        } catch (UnknownHostException e) {
            System.out.println("cant find Server -> " + e.getMessage());
        }catch(IOException ioe){
            System.out.println("IO exception ->" + ioe.getMessage() );
        }
    }

    public void setClientName(String clientName){
        this.clientName = clientName;
    }

    public String getClientName(){
        return this.clientName;
    }


    public static void main(String[] args) {
        if(args.length == 0){
            port = 2000;
        }else if(args.length == 1){
            port = Integer.parseInt(args[1]);
        }

        Client client = new Client("hostName", port);
        client.runClient();
    }



}