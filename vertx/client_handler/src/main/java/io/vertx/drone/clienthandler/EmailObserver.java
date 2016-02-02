package io.vertx.drone.clienthandler;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailObserver implements  Observer {
    // Recipient's email ID needs to be mentioned.
    private String destination;

    // Sender's email ID needs to be mentioned
    private static final String FROM = "drone.delivery.alv4@gmail.com";
    //!!!!!!!!This is the credential for jangosmtp//
    private static final String USERNAME = "Drone.Delivery";
    private static final String PASSWORD = "jesuisdrone";

    // Assuming you are sending email through relay.jangosmtp.net
    String host = "relay.jangosmtp.net";
    @Override
    public void notify(String msg) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "25");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(FROM));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(this.destination));

            // Set Subject: header field
            message.setSubject("Drone Delivery notification");

            // Now set the actual message
            message.setText(msg);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
    }

    }

    public EmailObserver(String destination) {
        this.destination = destination;
    }
}
