package logic;


import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

import entity.Test;
import entity.User;




public class SendMailLogic {

    final String username = "sebbejava@gmail.com";
    final String password = "hejsan1234";
    User user;

    public void sendmail(User user, Test test){

        String receive = user.getEmail();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receive));
            message.setSubject("New test available");
            message.setText("Hey "+ user.getFirstName()+" "+ user.getLastName() +","
                    + "\n\n You can now take the "+ test.gettTitle() +" test!"+
                    "\n\n Your login name is " + user.getUserName()+
                    "\n Your password is " + user.getPassword());

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
