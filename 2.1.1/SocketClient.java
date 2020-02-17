import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) {
        Socket socket = null;
        System.out.println("Username: ");
        Scanner scan = new Scanner(System.in);
        String name = scan.nextLine();
        scan.close();
        int portNumber = 2000;

        try {
            socket = new Socket("LocalHost", portNumber);
            Thread.sleep(1000);
            Thread server = new Thread(new ServerThread(socket, name));
            server.start();
            

        } catch (IOException ioe) {
            System.err.println("Connection error");
            ioe.printStackTrace();
        }catch ( InterruptedException e){
            System.err.println("Fatal Connection error");
            e.printStackTrace();
        }

    }



public static void acceptClients(){

}






}