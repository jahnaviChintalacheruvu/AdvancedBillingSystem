package com.abs.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.abs.app.model.Email;


@Service
public class MessageServiceImpl implements MessageService{

	@Autowired 
	private JavaMailSender javaMailSender;
	
    @Value("${spring.mail.username}") private String sender;
	
	public int sendSimpleMail(Email details)
    {
 
        // Try block to check for exceptions
        try {
 
            // Creating a simple mail message
        	SimpleMailMessage mailMessage
            = new SimpleMailMessage();
            
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(new String[]{details.getRecipient()});
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            System.out.println("------------------body"+ mailMessage.getFrom()+"recep============"+mailMessage.getTo()+"recc--++"+details.getRecipient());
            // Sending the mail
            javaMailSender.send(mailMessage);
            return 1;
        }
 
        // Catch block to handle the exceptions
        catch (Exception e) {
            return 0;
        }
    }
}
