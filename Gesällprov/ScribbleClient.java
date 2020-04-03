package Gesällprov;

import javax.swing.*;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;

import java.awt.event.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.*;
import java.util.*;

public class ScribbleClient {

    private static ScribbleGUI scribbleGUI;
    private static int myPort;
    public static int remotePort;
    public static InetAddress address;
    public static DatagramSocket socket;

    private String clientName;
    

    ScribbleClient(int myPort) {
        this.myPort = myPort;
    }
    public void connectToServer(){
        try {
          Socket socket = new Socket("localhost", remotePort);
            System.out.println("trying to connect to server");
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
        try {

            // myPort = Integer.parseInt(args[0]);
            remotePort = 2000;
            // remotePort = Integer.parseInt(args[1]);
            address = InetAddress.getLocalHost();
            socket = new DatagramSocket(myPort);
            System.out.println(socket.getPort() + " | " + socket.getInetAddress());

            scribbleGUI = new ScribbleGUI();

        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();

        }

    }
}

class ClientThread extends Thread implements Runnable {

    public Socket socket;
    public ScribbleServer server;
    private PrintWriter writer;

    ClientThread(Socket socket, ScribbleServer server) {
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

            do {
                clientMessage = reader.readLine();
                serverMessage = "[ " + clientName + " ]: " + clientMessage;
                server.sendMessageToClients(serverMessage, this);
            } while (!clientMessage.equals("exit"));

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
        } else {
            writer.println("No Clients");
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

}

class ReadThread extends Thread {

    private BufferedReader reader;
    private Socket socket;
    private ScribbleClient client;

    public ReadThread(Socket socket, ScribbleClient client) {
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
                // System.out.println("[" + client.getClientName() + "]: ");

                // }
                // else{
                // System.out.println(response);
                // }

            } catch (IOException ex) {
                ex.getMessage();
                ex.printStackTrace();
                break;
            }
        }
        try {
            reader.close();
            socket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}

class ReciveThread implements Runnable {

    @Override
    public void run() {
        try {

            System.out.println("reciver is running");
            while (true) {
                byte[] buffer = new byte[256];
                DatagramPacket rdp = new DatagramPacket(buffer, buffer.length);

                Draw.socket.receive(rdp);
                System.out.println("dp Revecieved");
                byte[] data = rdp.getData();
                String message = new String(data);

                String[] messageArray = message.split(" ");
                Point p = new Point(Integer.parseInt(messageArray[0]), Integer.parseInt(messageArray[1]));
                System.out.println(p.toString());

                Draw.p.addPoint(p);

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }

}

class WriteThread extends Thread {

    private PrintWriter writer;
    private Socket socket;
    private ScribbleClient client;

    public WriteThread(Socket socket, ScribbleClient client) {
        this.socket = socket;
        this.client = client;

        try {
            OutputStream output = socket.getOutputStream();
            // writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),
            // "ISO-8859-1") , true);
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
        do {
            // "[" + userName + "]: "
            text = console.readLine();
            writer.println(text);
        } while (!text.equals("bye"));

        try {

            socket.close();

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

    }

}
