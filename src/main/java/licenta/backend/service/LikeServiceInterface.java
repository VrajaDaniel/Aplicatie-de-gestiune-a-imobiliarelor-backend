package licenta.backend.service;

import licenta.backend.dto.like.LikeInfoRequest;
import licenta.backend.model.Like;
import java.util.List;

public interface LikeServiceInterface {
    Like addLike(LikeInfoRequest likeInfoRequest);

    void deleteLike(Long postId,Long userId);

    List<Long> getUserLikedPosts(Long userId);
}
