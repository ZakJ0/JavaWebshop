package se.jaouhari.shop.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import se.jaouhari.shop.entities.Orderline;
import se.jaouhari.shop.entities.User;
import se.jaouhari.shop.repositories.UserRepo;
import java.util.Optional;

@Service
public class EmailSender {

    @Autowired
    public JavaMailSender emailSender;

    @Autowired
    UserRepo repo;

    public void sendOrderConfirmationEmail(String username) {
        String userEmail = getUserEmail(username);
        if (userEmail != null) {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String senderEmail = System.getenv("SENDER_EMAIL");
                helper.setFrom(senderEmail);
                helper.setTo(userEmail);
                helper.setSubject("Order Confirmation");
                helper.setText("Dear " + username + ",\n\nYour order has been successfully placed. Thank you for shopping with us!");
                emailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to send email: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("User with username " + username + " not found.");
        }
    }

    public void sendRegistrationConfirmationEmail(String username, String userEmail) {
        if (userEmail != null) {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                String senderEmail = System.getenv("SENDER_EMAIL");
                helper.setFrom(senderEmail);
                helper.setTo(userEmail);
                helper.setSubject("Registration Confirmation");
                helper.setText("Dear " + username + ",\n\nThank you for registering with us!");
                emailSender.send(message);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to send email: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("User email not provided for registration confirmation.");
        }
    }

    private String getUserEmail(String username) {
        Optional<User> user = repo.findByUsername(username);
        return user.get().getEmail();
    }
}



