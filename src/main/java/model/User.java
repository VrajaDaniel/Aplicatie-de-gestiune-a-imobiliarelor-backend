package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
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
