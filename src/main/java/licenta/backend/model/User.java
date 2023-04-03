package licenta.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Entity(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;
        private String password;
        private String role;

        @OneToMany(mappedBy = "user")
        private List<Post>postList ;

}
