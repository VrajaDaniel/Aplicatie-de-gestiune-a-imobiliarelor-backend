package licenta.backend.service;
import licenta.backend.dto.contact.ContactRequestBody;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface EmailServiceInterface {

    void sendEmailToOwner(ContactRequestBody contactRequestBody) throws MessagingException;
}
