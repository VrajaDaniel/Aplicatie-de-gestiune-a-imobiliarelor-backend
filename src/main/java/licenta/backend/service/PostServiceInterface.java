package licenta.backend.service;

import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Location;
import licenta.backend.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostServiceInterface {
    Post addPost(List<MultipartFile> file, Post post) throws IOException;

    Post editPost(List<MultipartFile> file,Long postId, Post edited);

    Post deletePost(Long postId);

    Location getPostLocation(Long postId);

    PostResponseBody getPostById(Long postId);
}
