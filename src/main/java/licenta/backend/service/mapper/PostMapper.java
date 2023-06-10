package licenta.backend.service.mapper;

import licenta.backend.dto.post.PostResponseBody;
import licenta.backend.model.Image;
import licenta.backend.model.Post;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Component
public class PostMapper {

    public PostResponseBody mapPostToPostResponseBody(Post post) {
        List<String> imageList = convertByteToBase64(post.getImagesList());
        PostResponseBody postResponseBody=new PostResponseBody();
        postResponseBody.setId(post.getId());
        postResponseBody.setTitle(post.getTitle());
        postResponseBody.setDescription(post.getDescription());
        postResponseBody.setCity(post.getCity());
        postResponseBody.setCategory(post.getCategory().toString());
        postResponseBody.setType(post.getType().toString());
        postResponseBody.setFloor(post.getFloor());
        postResponseBody.setUsefulSurface(post.getUsefulSurface());
        postResponseBody.setConstructionYear(post.getConstructionYear());
        postResponseBody.setNumberRooms(post.getNumberRooms());
        postResponseBody.setLatitude(post.getLocation().getLatitude());
        postResponseBody.setLongitude(post.getLocation().getLongitude());
        postResponseBody.setPrice(post.getPrice());
        postResponseBody.setImages(imageList);
        postResponseBody.setDate(post.getDate().toString());

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
