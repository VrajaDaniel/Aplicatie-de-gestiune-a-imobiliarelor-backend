package licenta.backend.service;

import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Image;
import licenta.backend.model.Location;
import licenta.backend.model.Post;
import licenta.backend.model.User;
import licenta.backend.repository.ImageRepository;
import licenta.backend.repository.PostRepository;
import licenta.backend.repository.UserRepository;
import licenta.backend.service.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    private final PostMapper postMapper;

    @Override
    @Transactional
    public Post addPost(List<MultipartFile> multipartFileList, Post post) throws IOException {
        String userId = getUserIdFromSecurityContext();

        List<byte[]> imageByteList = convertToByteList(multipartFileList);
        post.setUser(userRepository.findById(Long.parseLong(userId)).get());

        Post savedPost = postRepository.save(post);
        for (byte[] image : imageByteList) {
            Image image1 = new Image();
            image1.setFile(image);
            image1.setPost(savedPost);
            imageRepository.save(image1);
        }
        savedPost.setImagesList(imageRepository.findAll());
        return savedPost;
    }

    @Transactional
    public List<PostResponseBody> getPostListByUserId() {
        String userId = getUserIdFromSecurityContext();
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        List<PostResponseBody> postResponseBodyList = new ArrayList<>();

        if (user.isPresent()) {
            List<Post> postList = postRepository.findAllByUser(user.get());

            for (Post post : postList) {
                PostResponseBody postResponseBody = postMapper.mapPostToPostResponseBody(post);
                postResponseBodyList.add(postResponseBody);
            }
        }

        return postResponseBodyList;
    }

    @Transactional
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post editPost(List<MultipartFile> multipartFileList,Long postId, Post edited) {

        Optional<Post> optPost = postRepository.findById(postId);
        if (optPost.isEmpty()) {
            return null;
        }
        Post toUpdate = optPost.get();
        toUpdate.setTitle(edited.getTitle());
        toUpdate.setDescription(edited.getDescription());
        toUpdate.setDate(edited.getDate());
        toUpdate.setCity(edited.getCity());
        toUpdate.setPrice(edited.getPrice());
        toUpdate.setUsefulSurface(edited.getUsefulSurface());
        toUpdate.setFloor(edited.getFloor());
        toUpdate.setLocation(edited.getLocation());
        toUpdate.setCategory(edited.getCategory());
        toUpdate.setType(edited.getType());
        toUpdate.setNumberRooms(edited.getNumberRooms());
        toUpdate.setConstructionYear(edited.getConstructionYear());

        imageRepository.deleteAll(toUpdate.getImagesList());
        Post savedPost = postRepository.save(toUpdate);
        List<byte[]> imageByteList = convertToByteList(multipartFileList);
        for (byte[] image : imageByteList) {
            Image image1 = new Image();
            image1.setFile(image);
            image1.setPost(savedPost);
            imageRepository.save(image1);
        }
        savedPost.setImagesList(imageRepository.findAll());
        return savedPost;
    }

    @Override
    public Post deletePost(Long postId) {
        Optional<Post> optPost = postRepository.findById(postId);
        if (optPost.isEmpty()) {
            return null;
        }
        Post toDelete = optPost.get();
        imageRepository.deleteAll(toDelete.getImagesList());
        postRepository.delete(toDelete);
        return toDelete;
    }

    @Override
    public Location getPostLocation(Long postId) {
        Optional<Post> post = postRepository.findById(postId);
        Location location = null;
        if (post.isPresent()) {
            location = post.get().getLocation();
        }
        return location;
    }

    @Override
    public PostResponseBody getPostById(Long postId) {

        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()) {
            return postMapper.mapPostToPostResponseBody(post.get());
        }
        return null;
}

    private List<byte[]> convertToByteList(List<MultipartFile> multipartFileList) {
        List<byte[]> fileDataList = new ArrayList<>();
        for (MultipartFile file : multipartFileList) {
            try {
                byte[] fileData = file.getBytes();
                fileDataList.add(fileData);
            } catch (IOException e) {
                fileDataList.add(new byte[0]);
            }
        }

        return fileDataList;
    }

    private String getUserIdFromSecurityContext() {
        String userId = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return userId;
    }
}
