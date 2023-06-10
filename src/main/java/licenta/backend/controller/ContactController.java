package licenta.backend.controller;

import licenta.backend.dto.contact.ContactRequestBody;
import licenta.backend.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/contact")
@AllArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> contactPropertyOwner(@RequestBody ContactRequestBody contactRequestBody) throws MessagingException {
        System.out.println(contactRequestBody);
        emailService.sendEmailToOwner(contactRequestBody);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }
}
