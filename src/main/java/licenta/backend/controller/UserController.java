package licenta.backend.controller;

import licenta.backend.dto.user.UserResponseBody;
import licenta.backend.service.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserServiceInterface userServiceImpl;

    @GetMapping("/postUserDetails/{postId}")
    public ResponseEntity<UserResponseBody> getUserByPostId(@PathVariable Long postId) {
        UserResponseBody userResponseBody = userServiceImpl.getUserByPostId(postId);

        return new ResponseEntity<>(userResponseBody, HttpStatus.OK);
    }

}
