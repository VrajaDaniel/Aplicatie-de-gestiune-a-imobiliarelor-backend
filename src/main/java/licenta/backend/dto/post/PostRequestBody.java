package licenta.backend.dto.post;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PostRequestBody {

    private String title;
    private String description;
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
