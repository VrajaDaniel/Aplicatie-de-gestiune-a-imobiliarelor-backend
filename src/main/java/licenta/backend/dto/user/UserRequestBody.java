package licenta.backend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRequestBody {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
}
