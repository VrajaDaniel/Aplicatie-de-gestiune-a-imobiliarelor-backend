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

    @PostMapping(
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    ResponseEntity<Object> savePost(@RequestPart("files") List<MultipartFile> files, @RequestPart("postRequestBody") String postRequestBody) throws IOException {
        System.out.println("Controller body:" + postRequestBody);
        ObjectMapper objectMapper = new ObjectMapper();
        PostRequestBody postRequestBody1 = objectMapper.readValue(postRequestBody, PostRequestBody.class);
        System.out.println("another test" + postRequestBody1.getType() + postRequestBody1.getConstructionYear());
        Post post = modelMapper.map(postRequestBody1, Post.class);
        post.setLocation(new Location(postRequestBody1.getLatitude(), postRequestBody1.getLongitude()));

        postService.addPost(files, post);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/userPosts")
    ResponseEntity<List<PostResponseBody>> getUserPosts() {
        return new ResponseEntity<>(postService.getPostListByUserId(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseBody> getById(@PathVariable("id") Long id) {
        PostResponseBody postResponseBody = postService.getPostById(id);
        if (postResponseBody == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(postResponseBody);
    }


//    @GetMapping("/getAll")
//    ResponseEntity<List<PostRequestBody>> getPosts() {
//        return postService.getAll(requestParams)
//                .stream()
//                .map(post -> PostDTO.convertToDTO(post, modelMapper))
//                .collect(Collectors.toList());
//    }
//
//    @GetMapping("/getMyPosts")
//    List<PostResponseBody> getUserPosts() {
//        String userId = (String) SecurityContextHolder.getContext().getAuthentication()
//                .getPrincipal();
//        return postService.getUserPosts(Long.parseLong(userId))
//                .stream()
//                .map(post -> PostDTO.convertToDTO(post, modelMapper))
//                .collect(Collectors.toList());
//    }


}
