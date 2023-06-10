package licenta.backend.service;

import licenta.backend.model.User;
import licenta.backend.model.exception.UserException;
import licenta.backend.model.validator.UserValidator;
import licenta.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService implements UserServiceInterface, UserDetailsService {
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
        user.setRole("USER");
        return userRepository.save(user);
    }

    public void deleteUser(Long id) throws UserException {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserException("Utilizatorul cu id=" + id + " nu exista");
        }
        Optional<User> userFromRepository = userRepository.findById(id);
        userFromRepository.ifPresent(userRepository::delete);
    }

    public User updateUser(Long id, User user) throws UserException {

        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isEmpty()) {
            throw new UserException("Utilizatorul cu id=" + id + " nu exista");
        }

        userValidator.validateEmail(user.getEmail());
        userValidator.validatePhoneNumber(user.getPhoneNumber());

        var userWithPhone = userRepository.findUserByPhoneNumber(user.getPhoneNumber());
        if (userWithPhone.isPresent() && userWithPhone.get().getId() != id) {
            throw new UserException("Acest numar de telefon aparține deja unui utilizator inregistrat");
        }

        user.setId(id);
        user.setPassword(optUser.get().getPassword());
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserException {
        if (userRepository.findById(id).isEmpty()) {
            throw new UserException("Utilizatorul cu id" + id + " nu exista");
        }

        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        }
        return null;
    }

    public User getUserByEmail(String email) throws UserException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UserException("Utilizatorul cu acest e-mail nu exista");
        } else {
            return user.get();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }

        User user = userOptional.get();
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), new ArrayList<>());
    }
}
