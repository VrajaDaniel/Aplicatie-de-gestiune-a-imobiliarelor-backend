package licenta.backend.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import licenta.backend.dto.authentication.AuthenticationRequest;
import licenta.backend.dto.user.UserRequestBody;
import licenta.backend.dto.user.UserResponseBody;
import licenta.backend.model.User;
import licenta.backend.model.exception.UserException;
import licenta.backend.service.UserServiceInterface;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final UserServiceInterface userService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest authenticationRequest) {
        String email = authenticationRequest.getUserName();
        String password = authenticationRequest.getPassword();
        User user;
        try {
            user = userService.getUserByEmail(email);
        } catch (UserException e) {
            return new ResponseEntity<>("Email invalid!",
                    HttpStatus.UNAUTHORIZED);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>("Parola invalida!",
                    HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(getUserDTOWithToken(user), HttpStatus.OK);
    }

    @GetMapping("/isLoggedIn")
    public ResponseEntity<String> isLoggedIn() {

        return new ResponseEntity<>("Userul e logat", HttpStatus.OK);
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
        return new ResponseEntity<>("Contul a fost creeat cu succes", HttpStatus.OK);
    }

    private UserResponseBody getUserDTOWithToken(User user) {
        String token = getJWTToken(user.getId());
        UserResponseBody userInfo = modelMapper.map(user, UserResponseBody.class);
        userInfo.setToken(token);
        return userInfo;
    }

    private String getJWTToken(Long id) {
        String secretKey = "mySecretKey";
        User user;
        try {
            user = userService.getUserById(id);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        List<GrantedAuthority> grantedAuthorities;

        if (user.getRole() != null && user.getRole().equals("ADMIN")) {
            grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
        } else {
            grantedAuthorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_USER");
        }
        return Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(id.toString())
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .claim("userId", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (864000 * 1000)))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
    }
}
