package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity(name = "post")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    private String title;
    private String description;
    private LocalDate date;
    private boolean open;
    private String city;
    private double price;
    private double usefulSurface;
    private int floor;


    @Enumerated(EnumType.STRING)
    private Category category;

    @Embedded
    private Location location;

    @ManyToOne
    private User user;
}
