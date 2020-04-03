package Gesällprov;



import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.*;
import java.util.*;

public class ReciveThread implements Runnable {

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