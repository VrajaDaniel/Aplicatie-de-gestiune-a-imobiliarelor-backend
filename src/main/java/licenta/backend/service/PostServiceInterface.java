package licenta.backend.service;

import licenta.backend.model.Location;
import licenta.backend.model.Post;

public interface PostServiceInterface {
    Post addPost(Post post);

    Post editPost(Long postId, Post edited);

    Post deletePost(Long postId);

    Location getPostLocation(Long postId);

    Post getPostById(Long postId);
}
