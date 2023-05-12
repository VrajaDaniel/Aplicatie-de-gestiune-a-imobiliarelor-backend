package licenta.backend.controller;

import licenta.backend.dto.user.UserRequestBody;
import licenta.backend.dto.user.UserResponseBody;
import licenta.backend.model.User;
import licenta.backend.model.exception.UserException;
import licenta.backend.service.EmailService;
import licenta.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final EmailService emailService;
    private final UserService userServiceImpl;
    private final ModelMapper modelMapper = new ModelMapper();


    @GetMapping("/{id}")
    public ResponseEntity<UserResponseBody> getUserById(@PathVariable Long id) throws UserException {
        User user = userServiceImpl.getUserById(id);
        UserResponseBody userResponseBody = modelMapper.map(user, UserResponseBody.class);

        return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<UserResponseBody>> getAllUsers() {
        List<UserResponseBody> userInfoResponseList = userServiceImpl.getAllUsers().stream()
                .map(user -> modelMapper.map(user, UserResponseBody.class))
                .collect(Collectors.toList());

        return new ResponseEntity<>(userInfoResponseList, HttpStatus.OK);
    }

    @GetMapping("/email")
    public ResponseEntity<UserResponseBody> getUserByEmail(@RequestParam("email") String email) throws UserException {
        User user = userServiceImpl.getUserByEmail(email);
        UserResponseBody userInfoResponse = modelMapper.map(user, UserResponseBody.class);

        return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseBody> updateUser(@RequestBody UserResponseBody userResponseBody, @PathVariable Long id) throws UserException {

        User user = modelMapper.map(userResponseBody, User.class);
        User updatedUser = userServiceImpl.updateUser(id, user);

        return new ResponseEntity<>(modelMapper.map(updatedUser, UserResponseBody.class), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserResponseBody> saveUser(@RequestBody UserRequestBody userRequestBody) throws UserException, MessagingException {
        User user = modelMapper.map(userRequestBody, User.class);
        User savedUser = userServiceImpl.saveUser(user);
        //emailService.sendEmail(user.getEmail());
        return new ResponseEntity<>(modelMapper.map(savedUser, UserResponseBody.class), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) throws UserException {

        userServiceImpl.deleteUser(id);
        return new ResponseEntity<>("Utilizatorul a fost șters cu succes", HttpStatus.OK);
    }

}
