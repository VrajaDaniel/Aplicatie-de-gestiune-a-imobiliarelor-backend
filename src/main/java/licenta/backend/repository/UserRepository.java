package licenta.backend.repository;

import licenta.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT * FROM users u where u.id in (Select user_id from posts where id=:postId)", nativeQuery = true)
    Optional<User> findUserByPostId(@Param("postId") Long postId);

}
