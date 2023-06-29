package licenta.backend.service;

import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Image;
import licenta.backend.model.Post;
import licenta.backend.model.User;
import licenta.backend.repository.ImageRepository;
import licenta.backend.repository.LikeRepository;
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
    private final LikeRepository likeRepository;
    private final PostMapper postMapper;

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

    private PostResponseBody getPostResponseBody(List<byte[]> imageByteList, List<Image> imageList, Post savedPost) {
        for (byte[] image : imageByteList) {
            Image image1 = new Image();
            image1.setFile(image);
            image1.setPost(savedPost);
            imageList.add(imageRepository.save(image1));
        }
        savedPost.setImagesList(imageList);
        return postMapper.mapPostToPostResponseBody(savedPost);
    }

    @Override
    @Transactional
    public PostResponseBody addPost(List<MultipartFile> multipartFileList, Post post) {
        String userId = getUserIdFromSecurityContext();
        List<byte[]> imageByteList = convertToByteList(multipartFileList);
        post.setUser(userRepository.findById(Long.parseLong(userId)).get());
        List<Image> imageList = new ArrayList<>();
        Post savedPost = postRepository.save(post);

        return getPostResponseBody(imageByteList, imageList, savedPost);
    }

    @Override
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

    @Override
    @Transactional
    public PostResponseBody editPost(List<MultipartFile> multipartFileList, Long postId, Post edited) {

        Optional<Post> optPost = postRepository.findById(postId);
        if (optPost.isEmpty()) {
            return null;
        }
        Post toUpdate = optPost.get();
        toUpdate.setTitle(edited.getTitle());
        toUpdate.setDescription(edited.getDescription());
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
        List<Image> imageList = new ArrayList<>();

        return getPostResponseBody(imageByteList, imageList, savedPost);
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
    public PostResponseBody getPostById(Long postId) {

        Optional<Post> post = postRepository.findById(postId);
        return post.map(postMapper::mapPostToPostResponseBody).orElse(null);
    }

    @Override
    public List<PostResponseBody> getPosts() {
        List<PostResponseBody> postResponseBodyList = new ArrayList<>();
        List<Post> postList = postRepository.findAll();

        for (Post post : postList) {
            PostResponseBody postResponseBody = postMapper.mapPostToPostResponseBody(post);
            postResponseBody.setLikeNumber(likeRepository.countByPostId(post.getId()));
            postResponseBodyList.add(postResponseBody);
        }

        return postResponseBodyList;
    }

}
