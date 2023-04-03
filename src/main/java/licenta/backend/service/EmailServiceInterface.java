package licenta.backend.service;
import javax.mail.MessagingException;

public interface EmailServiceInterface {
    void sendEmail(String userEmailAccount) throws MessagingException;
}
