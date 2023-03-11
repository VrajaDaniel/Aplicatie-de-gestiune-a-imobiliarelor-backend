package service;

import model.Location;
import model.Post;
import org.springframework.transaction.annotation.Transactional;
import repository.PostRepository;

import java.util.Optional;

public class PostService implements PostServiceInterface{
    private PostRepository postRepository;

    @Override
    @Transactional
    public Post addPost(Post post) {
        if(postRepository.findByTitle(post.getTitle()).isPresent()){
            return null;
        }
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public Post editPost(Long postId, Post edited) {
        Optional<Post> optPost = postRepository.findById(postId);
        if(optPost.isEmpty()){
            return null;
        }
        Post toUpdate = optPost.get();
        toUpdate.setTitle(edited.getTitle());
        toUpdate.setDescription(edited.getDescription());
        toUpdate.setDate(edited.getDate());
        toUpdate.setOpen(edited.isOpen());
        toUpdate.setCity(edited.getCity());
        toUpdate.setPrice(edited.getPrice());
        toUpdate.setUsefulSurface(edited.getUsefulSurface());
        toUpdate.setFloor(edited.getFloor());
        toUpdate.setLocation(edited.getLocation());
        toUpdate.setCategory(edited.getCategory());
        toUpdate.setType(edited.getType());
        toUpdate.setNumberRooms(edited.getNumberRooms());
        toUpdate.setConstructionYear(edited.getConstructionYear());
        postRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public Post deletePost(Long postId) {
        Optional<Post> optPost = postRepository.findById(postId);
        if(optPost.isEmpty()){
            return null;
        }
        Post toDelete = optPost.get();
        postRepository.delete(toDelete);
        return toDelete;
    }

    @Override
    public Location getPostLocation(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        Location location = null;
        if(post.isPresent()){
            location = post.get().getLocation();
        }
        return location;
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
