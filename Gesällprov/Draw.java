
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.*;
import java.util.*;

public class Draw extends JFrame {
  public static Paper p = new Paper();
  private static int myPort;
  public static int remotePort;
  public static InetAddress address;
  public static DatagramSocket socket;

  public static void main(String[] args) {
    try {

      myPort = Integer.parseInt(args[0]);
      remotePort = Integer.parseInt(args[1]);
      address = InetAddress.getLocalHost();
      socket = new DatagramSocket(myPort);
      System.out.println(socket.getPort() + " | " + socket.getInetAddress());

      new Draw();
      ReciveThread rt = new ReciveThread();
      Thread reciveThread = new Thread(rt);
      reciveThread.start();

    } catch (Exception e) {
      // TODO: handle exception
    }

  }

  public Draw() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    getContentPane().add(p, BorderLayout.CENTER);

    setSize(640, 480);
    setVisible(true);

  }
}

class Paper extends JPanel {
  private HashSet<Point> hs = new HashSet<Point>();
  private Draw draw;

  public Paper() {
    setBackground(Color.white);
    addMouseListener(new L1());
    addMouseMotionListener(new L2());
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.setColor(Color.black);
    Iterator i = hs.iterator();
    while (i.hasNext()) {
      Point p = (Point) i.next();
      g.fillOval(p.x, p.y, 10, 10);
    }
  }

  public void addPoint(Point p) {
    hs.add(p);
    repaint();
  }

  class L1 extends MouseAdapter {
    public void mousePressed(MouseEvent me) {
      addPoint(me.getPoint());
      sendPacket(creatPacket(me.getPoint()));
    }
  }

  class L2 extends MouseMotionAdapter {
    public void mouseDragged(MouseEvent me) {
      addPoint(me.getPoint());
      sendPacket(creatPacket(me.getPoint()));
    }
  }

  private DatagramPacket creatPacket(Point p) {

    String message = Integer.toString(p.x) + " " + Integer.toString(p.y) + " ";
    byte[] bytes = message.getBytes();

    DatagramPacket dp = new DatagramPacket(bytes, bytes.length, Draw.address, Draw.remotePort);
    System.out.println("dp created");
    return dp;

  }

  private void sendPacket(DatagramPacket dp) {
    try {
      Draw.socket.send(dp);
      System.out.println("dp sent");
    } catch (IOException ex) {
      ex.printStackTrace();
      System.out.println(ex);
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