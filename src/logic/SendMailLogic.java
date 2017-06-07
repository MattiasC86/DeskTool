package logic;


import java.util.*;
import java.util.stream.Collectors;
import javax.mail.*;
import javax.mail.internet.*;
import javax.print.DocFlavor;

import entity.Test;
import entity.User;




public class SendMailLogic {

    final String username = "desktesttool@gmail.com";
    final String password = "hejsan1234";
    User user;
    String tempList;

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
            //The mail it sends from.
            message.setFrom(new InternetAddress("from-email@gmail.com"));
            //The mail sends to.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(receive));
            //The Titel of the mail.
            message.setSubject("New test available");
            //The content of the mail.
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

    public void sendMulti(List<User> userList, Test test){

        List<String> userEmailList = new ArrayList<>();

        for (User user: userList){
            userEmailList.add(user.getEmail());
        }

        tempList = userEmailList.stream()
                .map(String::toString)
                .collect(Collectors.joining(", "));

        System.out.println(userEmailList+" tempList: "+tempList);

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
            //The mail it sends from.
            message.setFrom(new InternetAddress("from-email@gmail.com"));

            //The mail sends to.
            //String tempList = String.join(userEmailList);
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(tempList));

            //The Titel of the mail.
            message.setSubject("New test available");

            //The content of the mail.
            message.setText("Hej nu kan du göra "+test.gettTitle());

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
