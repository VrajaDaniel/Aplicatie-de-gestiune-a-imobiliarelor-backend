package licenta.backend.service;

import licenta.backend.dto.like.LikeInfoRequest;
import licenta.backend.model.Like;
import licenta.backend.model.Post;
import licenta.backend.model.User;
import licenta.backend.repository.LikeRepository;
import licenta.backend.repository.PostRepository;
import licenta.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikeService implements LikeServiceInterface {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    @Override
    public Like addLike(LikeInfoRequest likeInfoRequest) {
        Optional<Post> post = postRepository.findById(likeInfoRequest.getPostId());
        Optional<User> user = userRepository.findById(likeInfoRequest.getUserId());
        if (user.isPresent() && post.isPresent()) {
            Like like = new Like(user.get(), post.get());

            return likeRepository.save(like);
        }

        return null;
    }

    @Override
    public void deleteLike(Long postId, Long userId) {

        if (userId != null && postId != null) {
            likeRepository.deleteByUserIdAndPostId(userId, postId);
        }
    }

    @Override
    public List<Long> getUserLikedPosts(Long userId) {

        return likeRepository.findPostListByUserId(userId);
    }

}
