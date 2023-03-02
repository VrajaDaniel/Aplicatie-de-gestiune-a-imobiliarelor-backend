package model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;

@Entity(name = "utilizator")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Utilizator {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
        @GenericGenerator(name = "native", strategy = "native")
        private Long id;
        private String prenume;
        private String nume;
        private String telefon;
        private String email;
        private String parola;
        private String rol;

        @OneToMany(mappedBy = "utilizator")
        private List<Anunt> anuntSet;

}
