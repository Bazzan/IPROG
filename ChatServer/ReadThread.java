
import java.net.*;
import java.io.*;

public class ReadThread extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        while (true) {
            try {
                // !this.client.getUserName().equals(client.getUserName())
                // if(client.getUserName().equals(userName))
                String response = reader.readLine();
                System.out.println("\n" + response);

                // if (client.getClientName() != null) {
                //     System.out.println("[" + client.getClientName() + "]: ");

                // }
                // else{
                //     System.out.println(response);
                // }


            } catch (IOException ex) {
                ex.getMessage();
                ex.printStackTrace();
                break;
            }
        }
    }

}