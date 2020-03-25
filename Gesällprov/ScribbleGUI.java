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

public class ScribbleGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField server;
    private JTextField username;
    private JTextField password;
    private JTextField from;
    private JTextField to;
    private JTextField subject;
    private JTextArea msg;

    public ScribbleGUI() {
        final JPanel textFields = new JPanel();

        this.server = new JTextField(5);
        textFields.add(new JLabel("Server:"));
        textFields.add(this.server);

        this.username = new JTextField(5);
        textFields.add(new JLabel("Username:"));
        textFields.add(this.username);

        this.password = new JTextField(5);
        textFields.add(new JLabel("Password:"));
        textFields.add(this.password);

        this.from = new JTextField(5);
        textFields.add(new JLabel("From:"));
        textFields.add(this.from);

        this.to = new JTextField(5);
        textFields.add(new JLabel("To:"));
        textFields.add(this.to);

        this.subject = new JTextField(5);
        textFields.add(new JLabel("Subject:"));
        textFields.add(this.subject);

        this.msg = new JTextArea();
        this.setDefaultCloseOperation(3);
        JScrollPane msgScroll = new JScrollPane(msg);
        Paper paper = new Paper();
        JScrollPane paperPane = new JScrollPane(paper);
        // msgScroll.setAlignmentX(JScrollPane.RIGHT_ALIGNMENT);
        // msgScroll1.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
        msgScroll.setPreferredSize(new Dimension(200,500));
        paperPane.setPreferredSize(new Dimension(700,500));
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        center.add(paperPane);

        center.add(msgScroll);

        final JButton sendB = new JButton("Send");
        sendB.addActionListener(new Listener());

        this.getContentPane().add("North", textFields);
        this.getContentPane().add("Center", center);
    
        
        this.getContentPane().add("South", sendB);
        this.setSize(1000, 1000);
        this.setVisible(true);
    }

    class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub

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
}