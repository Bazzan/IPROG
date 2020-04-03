package Gesällprov;


import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

import java.awt.Dimension;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaperComp extends JPanel {
    private HashSet<Point> hs = new HashSet<Point>();
    private Draw draw;
  
    public PaperComp() {
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
        // sendPacket(creatPacket(me.getPoint()));
      }
    }
  
    class L2 extends MouseMotionAdapter {
      public void mouseDragged(MouseEvent me) {
        addPoint(me.getPoint());
        // sendPacket(creatPacket(me.getPoint()));
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