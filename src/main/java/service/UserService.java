package service;

import lombok.AllArgsConstructor;
import model.User;
import model.exception.UserException;
import model.validator.UserValidator;
import org.springframework.stereotype.Service;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements UserServiceInterface{
    private final UserRepository userRepository;

    private final UserValidator userValidator;

    public User saveUser(User user) throws UserException {
        userValidator.validateEmail(user.getEmail());
        userValidator.validatePhoneNumber(user.getPhoneNumber());

        if (userRepository.findUserByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            throw new UserException("Acest numar de telefon apartine deja unui utilizator inregistrat!");
        }
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserException("Acest e-mail aparține deja unui utilizator înregistrat!");
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) throws UserException {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserException("Utilizatorul cu id="+id+" nu exista");
        }
        Optional<User> userFromRepository = userRepository.findById(id);
        userFromRepository.ifPresent(userRepository::delete);
    }

    public User updateUser(Long id, User user) throws UserException {

        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new UserException("Utilizatorul cu id="+id+" nu exista");
        }

        userValidator.validateEmail(user.getEmail());
        userValidator.validatePhoneNumber(user.getPhoneNumber());

        var userWithPhone=userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if (userWithPhone.isPresent()&& userWithPhone.get().getId()!=id) {
            throw new UserException("Acest numar de telefon aparține deja unui utilizator inregistrat");
        }

        user.setId(id);
        user.setPassword(optUser.get().getPassword());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserException{
        if (userRepository.findById(id).isEmpty()) {
            throw new UserException("Utilizatorul cu id"+id+" nu exista");
        }

        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    public User getUserByEmail(String email) throws UserException {
        if (userRepository.findUserByEmail(email).isEmpty()) {
            throw new UserException("Utilizatorul cu acest e-mail nu exista");
        }
        return userRepository.findUserByEmail(email).get();
    }
}
