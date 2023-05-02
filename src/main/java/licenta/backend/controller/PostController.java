package licenta.backend.controller;

import licenta.backend.dto.post.PostRequestBody;
import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Post;
import licenta.backend.service.PostService;
import licenta.backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    public PostService postService;
    public UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    ResponseEntity<Object> getPost(@RequestParam("files") List<MultipartFile> files, @RequestBody PostRequestBody postRequestBody) {
        Post post = modelMapper.map(postRequestBody, Post.class);

        postService.addPost(files, post);

        return new ResponseEntity<>(postService.addPost(files, post),HttpStatus.OK);
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
