package com;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Mail {

    private Mail(){}
    private Session newSession;

    private static String to;           //Receiver's Email
    private static String body;         //Email Body
    private static String subject;      // Email Subject

    private final String from = "";        //Sender's Email
    private final String password = "";         //Sender's App Password (Google)

    public static void dispatchEmail(String email, String emailBody,String emailSubject) {
        to = email;
        body = emailBody;
        subject = emailSubject;
        Mail mail = new Mail();
        mail.setupServerProperties();
        mail.sendEmail();

    }

    private void sendEmail() {
        try {

            Message message = new MimeMessage(newSession);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            message.setContent(body, "text/html");

            Transport.send(message);


        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupServerProperties() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        newSession = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

    }
}
