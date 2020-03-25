package Epost;

import javax.mail.*;
import javax.mail.Message;
// import javax.mail.MessagingException;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import javax.swing.*;
import java.awt.event.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EpostSend extends JFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JTextField server;
    private JTextField username;
    private JTextField password;
    private JTextField from;
    private JTextField to;
    private JTextField subject;
    private JTextArea msg;

    public static void main(final String[] array) {
        new EpostSend();
    }

    public EpostSend() {
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

        final JButton sendB = new JButton("Send");
        sendB.addActionListener(new Listener());

        this.getContentPane().add("North", textFields);
        this.getContentPane().add("Center", msgScroll);

        this.getContentPane().add("South", sendB);
        this.setSize(700, 700);
        this.setVisible(true);
    }

    class Listener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent actionEvent) {
            EpostSend.this.sendMail();
        }
    }

    public void sendMail() {
        System.out.println("sendMail is preparing");
        Properties prop = new Properties();
        prop.put("mail.transport.protocol", "smtp");

        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", true);
        prop.put("mail.smtp.host", this.server.getText());
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.socketFactory.port", "465");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.put("mail.smtp.socketFactory.fallback", "false");


       System.out.println("perp session");
        Session session = Session.getInstance(prop, new SMTPAuthenticator());
        System.out.println("prep message");
        Message message = prepMsg(session);
        try {
           Transport.send(message);
           System.out.println("message sent");
    //    } catch (MessagingException e) {
    //        // TODO Auto-generated catch block
    //        e.printStackTrace();
    //    }
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    
    }   

    private Message prepMsg(Session session) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EpostSend.this.username.getText()));
            InternetAddress[] to = { new InternetAddress(EpostSend.this.to.getText()) };

            message.setRecipients(Message.RecipientType.TO, to);
            message.setSubject(EpostSend.this.subject.getText());
            message.setText(EpostSend.this.msg.getText());

            return message;

        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        // } catch (MessagingException me) {
        //     // TODO Auto-generated catch block
        //     me.printStackTrace();
        } catch(Exception ex){
            Logger.getLogger(EpostSend.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    class SMTPAuthenticator extends javax.mail.Authenticator
    {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(EpostSend.this.username.getText(), EpostSend.this.password.getText());
        }
    }

}