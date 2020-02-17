import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread implements Runnable{

    private Socket socket;
    private String name;
    private BufferedReader serverIn;
    private BufferedReader userIn;
    private PrintWriter out;



    public ServerThread(Socket socket, String name){
        this.socket = socket;
        this.name = name;
    }

    @Override
    public void run(){
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            // serverIn = (new BufferedReader(new InputStreamReader(socket.getInputStream())));
            serverIn = socket.getInputStream();
            userIn = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("New user " + userIn);
            while(!socket.isClosed()){
                System.out.println("Waiting for line");

                if(serverIn.ready()){
                    System.out.println("Waiting for line");
                    String input = serverIn.readLine();
                    if(input != null){
                        System.out.println(input);
                    }
                }else{
                    System.out.println(serverIn.ready());
                }
                if(userIn.ready()){
                    out.println(name+ " > " + userIn.readLine());
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}