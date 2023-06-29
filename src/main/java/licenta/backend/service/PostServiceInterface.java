package licenta.backend.service;

import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Post;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PostServiceInterface {
    PostResponseBody addPost(List<MultipartFile> file, Post post) throws IOException;

    PostResponseBody editPost(List<MultipartFile> file, Long postId, Post edited);

    Post deletePost(Long postId);

    PostResponseBody getPostById(Long postId);

    List<PostResponseBody> getPostListByUserId();

    List<PostResponseBody> getPosts();
}
