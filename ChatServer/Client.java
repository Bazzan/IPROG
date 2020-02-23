
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client{


    private String hostName;
    private int port;
    private String clientName;

    public Client(/*String hostName*/ int port){
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
        
        String hostName;
        int port;

        // if(args.length == 2) {
        //     hostName = args[0];
        //     port = Integer.parseInt(args[1]);
    
        // }else 
        if( args.length == 1){
            // hostName = args[0];
            port = Integer.parseInt(args[0]);
        }else if(args.length == 0){
            // hostName = "127.0.0.1";
            port = 2000;
        }else return;

        Client client = new Client( port);
        client.runClient();


    }



}