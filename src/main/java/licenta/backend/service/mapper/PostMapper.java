package licenta.backend.service.mapper;

import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Image;
import licenta.backend.model.Post;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class PostMapper {

    public PostResponseBody mapPostResponseBodyToPost(Post post) {
        List<String> imageList = convertByteToBase64(post.getImagesList());
        PostResponseBody postResponseBody=new PostResponseBody();
        postResponseBody.setId(post.getId());
        postResponseBody.setTitle(post.getTitle());
        postResponseBody.setDescription(post.getDescription());
        postResponseBody.setCity(post.getCity());
        postResponseBody.setImages(imageList);

        return postResponseBody;
    }

    private List<String> convertByteToBase64(List<Image> filesByteList) {
        List<String> base64StringList=new ArrayList<>();
        for (Image fileData : filesByteList) {

            base64StringList.add(Base64.getEncoder().encodeToString(fileData.getFile()));
        }
        return base64StringList;
    }

}
