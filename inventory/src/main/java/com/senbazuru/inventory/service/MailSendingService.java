/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.senbazuru.inventory.service;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Binh
 */
@Service
public class MailSendingService {

    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;

    public void sendMail(String productname, Integer quantity) throws AddressException, MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(username, false));

        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("info@senbazuru.hu"));
        msg.setSubject("Out of Stock warning");

        msg.setSentDate(new Date());

        StringBuffer emailMessage = createMessageBody(productname, quantity);

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Dear Elly" + emailMessage, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);
        Transport.send(msg);

    }

    private StringBuffer createMessageBody(String productname, Integer quantity) {
        StringBuffer emailMessage = new StringBuffer();
        emailMessage.append("<br/>");
        emailMessage.append("<br/>");
        emailMessage.append("The Following product will be out of stock soon!");
        emailMessage.append("<br/>");
        emailMessage.append("<br/>");
        emailMessage.append(productname);
        emailMessage.append("<br/>");
        emailMessage.append("<br/>");
        emailMessage.append("Total stock: " + quantity);
        emailMessage.append("<br/>");
        emailMessage.append("<br/>");
        emailMessage.append("Best Regards");
        emailMessage.append("<br/>");
        emailMessage.append("<br/>");
        emailMessage.append("Your genius brother");
        return emailMessage;
    }
}
