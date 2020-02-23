
import java.io.*;
import java.net.*;

public class WriteThread extends Thread {

    private PrintWriter writer;
    private Socket socket;
    private Client client;

    public WriteThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            // writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "ISO-8859-1") , true);
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Console console = System.console();

        String userName = console.readLine("\nEnter youre username");
        client.setClientName(userName);
        writer.println(userName);

        String text;
        do{
            // "[" + userName + "]: "
            text = console.readLine();
            writer.println(text);
        }while(!text.equals("bye"));
        
        try{
            
            socket.close();
            
        } catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
        }

    }

}