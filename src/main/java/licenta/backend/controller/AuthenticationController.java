package licenta.backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import licenta.backend.dto.authentication.AuthenticationRequest;
import licenta.backend.dto.user.UserRequestBody;
import licenta.backend.dto.user.UserResponseBody;
import licenta.backend.model.User;
import licenta.backend.model.exception.UserException;
import licenta.backend.service.EmailService;
import licenta.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthenticationController {
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private ModelMapper modelMapper;
    private EmailService emailService;

    @Autowired
    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder, ModelMapper modelMapper,EmailService emailService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.emailService=emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getUserName();
        String password = authenticationRequest.getPassword();
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (UserException e) {
            return new ResponseEntity<>("Invalid email!",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>("Invalid password!",
                    HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(getUserDTOWithToken(user), HttpStatus.OK);
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity isLoggedIn() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logout/{email}")
    public ResponseEntity<String> logout(@PathVariable String email) {
        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserRequestBody userCredentials) {
        User user = modelMapper.map(userCredentials, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userService.saveUser(user);
        } catch (UserException e) {
            return new ResponseEntity<>("",
                    HttpStatus.BAD_REQUEST);
        }
        try {
            emailService.sendEmail(user.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Signup successful", HttpStatus.OK);
    }

    private UserResponseBody getUserDTOWithToken(User user) {
        String token = getJWTToken(user.getId());
        UserResponseBody userInfo = modelMapper.map(user, UserResponseBody.class);
        userInfo.setToken(token);
        return userInfo;
    }

    private String getJWTToken(Long id) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        return Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(id.toString())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (864000 * 1000)))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }
}
