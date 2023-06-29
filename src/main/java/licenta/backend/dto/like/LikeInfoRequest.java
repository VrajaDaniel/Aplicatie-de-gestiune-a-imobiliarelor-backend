package licenta.backend.dto.like;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LikeInfoRequest {
    private long postId;
    private long userId;
}
