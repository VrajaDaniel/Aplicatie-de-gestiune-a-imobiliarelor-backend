package controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userServiceImpl;


}
