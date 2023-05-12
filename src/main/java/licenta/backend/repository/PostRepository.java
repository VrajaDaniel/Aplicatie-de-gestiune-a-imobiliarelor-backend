package licenta.backend.repository;

import licenta.backend.model.Post;
import licenta.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findByTitle(String title);
    Page<Post> findAll(Pageable pageable);
    List<Post> findAllByUser(User user);
}
