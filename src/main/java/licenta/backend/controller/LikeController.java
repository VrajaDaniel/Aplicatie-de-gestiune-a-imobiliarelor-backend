package licenta.backend.controller;

import licenta.backend.dto.like.LikeInfoRequest;
import licenta.backend.service.LikeServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/like")
@AllArgsConstructor
public class LikeController {

    private final LikeServiceInterface likeServiceInterface;

    @PostMapping
    public ResponseEntity<String> saveLike(@RequestBody LikeInfoRequest likeInfoRequest) {
        if (likeServiceInterface.addLike(likeInfoRequest) != null) {
            return new ResponseEntity<>("Like-ul adaugat cu succes", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Like-ul nu a putut fi adaugat", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Long>> getUserLikedPosts(@PathVariable Long userId) {
        List<Long> likedPostIdList = likeServiceInterface.getUserLikedPosts(userId);

        if (likedPostIdList != null) {

            return new ResponseEntity<>(likedPostIdList, HttpStatus.OK);
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{userId}/{postId}")
    public ResponseEntity<String> deleteLike(@PathVariable Long userId, @PathVariable Long postId) {
        likeServiceInterface.deleteLike(userId, postId);

        return new ResponseEntity<>("Like-ul a fost șters cu succes", HttpStatus.OK);
    }
}
