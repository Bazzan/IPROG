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
  private JTextField myPort;
  private JTextField username;
  private JTextField from;
  private JTextField to;
  private JTextField subject;
  private JButton connecButton;
  private JTextArea msg;

  public ScribbleGUI() {
    final JPanel textFields = new JPanel();

    this.server = new JTextField(5);
    textFields.add(new JLabel("Server:"));
    textFields.add(this.server);

    this.myPort = new JTextField(5);
    textFields.add(new JLabel("My Port:"));
    textFields.add(this.myPort);

    this.username = new JTextField(5);
    textFields.add(new JLabel("Username"));
    textFields.add(this.username);

    this.connecButton = new JButton("Connect");
    textFields.add(this.connecButton);
    // textFields.

    // this.from = new JTextField(5);
    // textFields.add(new JLabel("From:"));
    // textFields.add(this.from);

    // this.to = new JTextField(5);
    // textFields.add(new JLabel("To:"));
    // textFields.add(this.to);

    // this.subject = new JTextField(5);
    // textFields.add(new JLabel("Subject:"));
    // textFields.add(this.subject);

    this.msg = new JTextArea();
    this.setDefaultCloseOperation(3);

    JScrollPane msgScroll = new JScrollPane(msg);
    PaperComp paper = new PaperComp();
    JScrollPane paperPane = new JScrollPane(paper);

    // msgScroll.setAlignmentX(JScrollPane.RIGHT_ALIGNMENT);
    // msgScroll1.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

    msgScroll.setPreferredSize(new Dimension(200, 500));
    paperPane.setPreferredSize(new Dimension(700, 500));

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

  class ConnectListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      ScribbleClient client = new ScribbleClient(Integer.parseInt(ScribbleGUI.this.myPort.getText()));
      client.connectToServer();
    }

  }

}