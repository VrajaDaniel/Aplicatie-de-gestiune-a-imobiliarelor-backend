package controller;

import dto.user.UserResponseBody;
import lombok.AllArgsConstructor;
import model.User;
import model.exception.UserException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userServiceImpl;
    private final ModelMapper modelMapper;

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

    @PutMapping
    public ResponseEntity<UserResponseBody> updateUser(@RequestBody UserResponseBody userResponseBody) throws UserException {
        String userId = (String) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Long id = Long.parseLong(userId);
        User user = modelMapper.map(userResponseBody, User.class);
        user.setId(id);
        User updatedUser = userServiceImpl.updateUser(id, user);
        return new ResponseEntity<>(modelMapper.map(updatedUser, UserResponseBody.class), HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<UserResponseBody> saveUser(@RequestBody UserResponseBody userInfoRequest) throws UserException, MessagingException {
//        User user = modelMapper.map(userInfoRequest, User.class);
//        User savedUser = userServiceImpl.saveUser(user);
//        emailService.sendEmail(user.getEmail());
//        return new ResponseEntity<>(modelMapper.map(savedUser, UserResponseBody.class), HttpStatus.OK);
//    }

//    @DeleteMapping
//    public ResponseEntity<Object> deleteUser() throws UserException {
//        Long userId = (Long)SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        userServiceImpl.deleteUser(userId);
//        return new ResponseEntity<>(CustomResponseEntity.getMessage("\n" + "Utilizatorul a fost șters cu succes"), HttpStatus.OK);
//    }

}
