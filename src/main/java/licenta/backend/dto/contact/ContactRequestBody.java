package licenta.backend.dto.contact;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ContactRequestBody {
    private String name;
    private String email;
    private String phone;
    private String message;
    private long postId;
}
