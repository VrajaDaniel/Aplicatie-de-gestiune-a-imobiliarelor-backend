package licenta.backend.service;

import licenta.backend.model.Location;
import licenta.backend.model.Post;
import licenta.backend.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService implements PostServiceInterface {
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Post addPost(List<MultipartFile> multipartFileList, Post post) {
        List<byte[]> fileByteList = convertToByteList(multipartFileList);
        post.setFiles(fileByteList);
        if (postRepository.findByTitle(post.getTitle()).isPresent()) {
            return null;
        }

        return postRepository.save(post);
    }

    @Transactional
    public List<Post> findAll() {

        return postRepository.findAll();
    }

    @Override
    @Transactional
    public Post editPost(Long postId, Post edited) {
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
        postRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public Post deletePost(Long postId) {
        Optional<Post> optPost = postRepository.findById(postId);
        if (optPost.isEmpty()) {
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
        if (post.isPresent()) {
            location = post.get().getLocation();
        }
        return location;
    }

    @Override
    public Post getPostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
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

    private List<InputStreamResource> convertByteToInputStream(List<byte[]> filesByteList) {
        List<InputStreamResource> fileResources = new ArrayList<>();
        for (byte[] fileData : filesByteList) {
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileData));
            fileResources.add(resource);
        }
        return fileResources;
    }
}
