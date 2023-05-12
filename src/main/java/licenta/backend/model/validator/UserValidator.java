package licenta.backend.model.validator;

import licenta.backend.model.exception.UserException;
import org.springframework.stereotype.Component;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");
    public void validateEmail(String email) throws UserException {
        if(email==null){
            throw new UserException("Adresa de email invalida");
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new UserException("Adresa de email invalida");
        }
    }

    public void validatePhoneNumber(String phoneNumber) throws UserException {
        if(phoneNumber==null){
            System.out.println("Numarul de telefon este nul");
            throw new UserException("Numar de telefon invalid");
        }
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        if (!matcher.matches()) {
            System.out.println("numarul de telefon nu se potrivește");
            throw new UserException("Numar de telefon invalid");
        }
    }

}
