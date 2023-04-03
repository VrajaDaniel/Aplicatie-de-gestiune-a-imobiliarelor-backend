package licenta.backend.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostResponseBody {
    private Long id;
    private String title;
    private String description;
    private String date;
    private String city;
    private double price;
    private double usefulSurface;
    private int floor;
    private int numberRooms;
    private int constructionYear;
    private String category;
    private String type;
    private double latitude;
    private double longitude;
    private long userId;
}
