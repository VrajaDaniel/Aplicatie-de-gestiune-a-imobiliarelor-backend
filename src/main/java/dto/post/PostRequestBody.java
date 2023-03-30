package dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PostRequestBody {

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
