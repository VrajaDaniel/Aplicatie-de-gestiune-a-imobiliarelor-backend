package licenta.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import licenta.backend.dto.post.PostRequestBody;
import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Location;
import licenta.backend.model.Post;
import licenta.backend.service.PostService;
import licenta.backend.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {
    public final PostService postService;
    public final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/userPosts")
    ResponseEntity<List<PostResponseBody>> getUserPosts() {
        return new ResponseEntity<>(postService.getPostListByUserId(), HttpStatus.OK);
    }

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    ResponseEntity<Object> savePost(@RequestPart("files") List<MultipartFile> files, @RequestPart("postRequestBody") String postRequestBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PostRequestBody postRequestBody1 = objectMapper.readValue(postRequestBody, PostRequestBody.class);
        Post post = modelMapper.map(postRequestBody1, Post.class);
        post.setLocation(new Location(postRequestBody1.getLatitude(), postRequestBody1.getLongitude()));

        return new ResponseEntity<>(postService.addPost(files, post), HttpStatus.OK);
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    ResponseEntity<Object> updatePost(@PathVariable Long id,
                                      @RequestPart("files") List<MultipartFile> files,
                                      @RequestPart("postRequestBody") String postRequestBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PostRequestBody postRequestBody1 = objectMapper.readValue(postRequestBody, PostRequestBody.class);
        Post post = modelMapper.map(postRequestBody1, Post.class);
        post.setLocation(new Location(postRequestBody1.getLatitude(), postRequestBody1.getLongitude()));
        post.setId(id);

        return new ResponseEntity<>(postService.editPost(files,id,post),HttpStatus.OK);
    }

    @DeleteMapping("/{id}" )
    ResponseEntity<Object> deletePost(@PathVariable Long id){

        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseBody> getById(@PathVariable("id") Long id) {
        PostResponseBody postResponseBody = postService.getPostById(id);
        if (postResponseBody == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(postResponseBody);
    }

}
