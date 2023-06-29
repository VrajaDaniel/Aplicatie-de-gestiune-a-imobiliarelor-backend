package licenta.backend.service;

import licenta.backend.dto.contact.ContactRequestBody;
import javax.mail.MessagingException;

public interface EmailServiceInterface {

    void sendEmailToOwner(ContactRequestBody contactRequestBody) throws MessagingException;
}
