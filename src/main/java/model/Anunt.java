package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity(name = "anunt")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Anunt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String titlu;
    private String descriere;
    private LocalDate data;
    private boolean deschis;
    private String oras;
    private double pret;
    private double suprafataUtila;
    private int etaj;


    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    @Embedded
    private Locatie locatie;

    @ManyToOne
    private Utilizator utilizator;
}
