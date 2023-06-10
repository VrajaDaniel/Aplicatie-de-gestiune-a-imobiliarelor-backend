package licenta.backend.service;

import licenta.backend.dto.contact.ContactRequestBody;
import licenta.backend.model.User;
import licenta.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Optional;
import java.util.Properties;

@Component
@AllArgsConstructor
public class EmailService implements EmailServiceInterface {

    private final UserRepository userRepository;

    @Override
    public void sendEmailToOwner(ContactRequestBody contactRequestBody) throws MessagingException {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "smtp-relay.sendinblue.com");
        prop.put("mail.smtp.port", "587");

        Optional<User> userOwner = userRepository.findUserByPostId(contactRequestBody.getPostId());
        if (userOwner.isEmpty()) {
            return;
        }
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("vrajadaniel7@gmail.com", "gRwmsNMk45tLJ1nG");
            }
        });
        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("AnunturiImobiliare.org@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(userOwner.get().getEmail()));
        message.setSubject("O noua persoana este interesata de proprietatea dumneavoastra");

        String msg =
                "Datele de contact: <br/>" +
                "Nume: " + contactRequestBody.getName() + "<br/>" +
                "Email: " + contactRequestBody.getEmail() + "<br/>" +
                "Telefon: " + contactRequestBody.getPhone() + "<br/>" +
                "Mesaj din partea persoanei interesate: " + contactRequestBody.getMessage() + "<br/>";


        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

}
