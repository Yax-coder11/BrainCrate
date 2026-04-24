package email;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class WelcomeMailSender {

    public static void sendWelcomeMail(String toEmail, String userName) throws MessagingException {
        final String fromEmail = "MailGatewaySolution@gmail.com"; // your Gmail
        final String password = "aous cree xdrn qmmt";            // app password from Google

        // SMTP server configuration
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Authenticate
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        // Create the message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Welcome to BrainCrate!");

        // ✅ Plain text content (no HTML)
        String textContent = "Hello " + userName + ",\n\n" +
                "🎉 Welcome to BrainCrate!\n" +
                "We're excited to have you on board.\n\n" +
                "Happy Coding!\n" +
                "- Team BrainCrate";

        message.setText(textContent);

        // Send the message
        Transport.send(message);
        System.out.println("✅ Plain text email sent successfully!");
    }
}
