package skit;
import javax.*;

import java.awt.event.ActionEvent;
import javax.mail.PasswordAuthentication;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JLabel;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFrame;

// 
// Decompiled by Procyon v0.5.36
// 

public class Mailsend extends JFrame
{
    private JTextField server;
    private JTextField username;
    private JTextField password;
    private JTextField from;
    private JTextField to;
    private JTextField subject;
    private JTextArea msg;
    
    public static void main(final String[] array) {
        new Mailsend();
    }
    
    public Mailsend() {
        this.server = new JTextField();
        this.username = new JTextField();
        this.password = new JTextField();
        this.from = new JTextField();
        this.to = new JTextField();
        this.subject = new JTextField();
        this.msg = new JTextArea();
        this.setDefaultCloseOperation(3);
        final JPanel comp = new JPanel();
        comp.setLayout(new GridLayout(6, 2));
        comp.add(new JLabel("Server:"));
        comp.add(this.server);
        comp.add(new JLabel("Username:"));
        comp.add(this.username);
        comp.add(new JLabel("Password:"));
        comp.add(this.password);
        comp.add(new JLabel("From:"));
        comp.add(this.from);
        comp.add(new JLabel("To:"));
        comp.add(this.to);
        comp.add(new JLabel("Subject:"));
        comp.add(this.subject);
        final JButton comp2 = new JButton("Send");
        comp2.addActionListener(new L());
        this.getContentPane().add("North", comp);
        this.getContentPane().add("South", comp2);
        this.getContentPane().add("Center", new JScrollPane(this.msg));
        this.setSize(640, 400);
        this.setVisible(true);
    }
    

    class L implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            Mailsend.this.send();
        }
    }
    public void send() {
        final Properties prop = new Properties();
        prop.put("mail.transport.protocol", "smtp");
        prop.put("mail.smtp.host", this.server.getText());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");
        final Session defaultInstance = Session.getDefaultInstance(prop, new SMTPAuthenticator());
        try {
            defaultInstance.getTransport();
            final MimeMessage msg = new MimeMessage(defaultInstance);
            msg.setFrom(new InternetAddress(this.from.getText()));
            msg.setRecipients(Message.RecipientType.TO, new InternetAddress[] { new InternetAddress(this.to.getText()) });
            msg.setSubject(this.subject.getText());
            msg.setText(this.msg.getText());
            Transport.send(msg);
        }
        catch (MessagingException ex) {
            ex.printStackTrace();

            // System.out.println(invokedynamic(makeConcatWithConstants:(Ljavax/mail/MessagingException;)Ljava/lang/String;, ex));
        }
    }
    
    class SMTPAuthenticator extends javax.mail.Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(Mailsend.this.username.getText(), Mailsend.this.password.getText());
        }
    }
    

}