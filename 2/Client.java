
import java.io.*;
import java.net.*;

public class Client {

    private String hostName;
    private int port;
    private String userName;


    public Client(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }

    public void execute(){
        try{
            Socket socket = new Socket(hostName, port);
            System.out.println("connected");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        }catch (UnknownHostException uh){
            System.out.println(uh);
        }catch(IOException e){
            System.out.println(e);
        }
    }


    void setUserName(String userName){
        this.userName = userName;
    }

    String getUserName(){
        return this.userName;
    }

    public static void main(String[] args) {
        String hostName;
        int port;

        if(args.length == 2) {
            hostName = args[0];
            port = Integer.parseInt(args[1]);
    
        }else if( args.length == 1){
            hostName = args[0];
            port = 2000;
        }else if(args.length == 0){
            hostName = "127.0.0.1";
            port = 2000;
        }else return;

        Client client = new Client(hostName, port);
        client.execute();

        }


        
    

}







// Socket socket = null;
// try {
//     socket = new Socket("localHost", 2000);
//     socket.setSoTimeout(15000);
//     InputStream in = socket.getInputStream();
//     BufferedReader reader = new BufferedReader(new InputStreamReader( in, "UTF-8"));

//     StringBuilder time = new StringBuilder();




// } catch (IOException e) {
//     System.out.println(e);
// } finally {
//     if (socket != null) {
//         try {
//             socket.close();

//         } catch (IOException ex) {
//             System.out.println(ex);
//         }

//     }
// }
