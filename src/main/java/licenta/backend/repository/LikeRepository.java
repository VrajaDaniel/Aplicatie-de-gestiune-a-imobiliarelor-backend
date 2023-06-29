package licenta.backend.repository;

import jakarta.transaction.Transactional;
import licenta.backend.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value = "SELECT post_id FROM likes WHERE user_id = :userId", nativeQuery = true)
    List<Long> findPostListByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    void deleteByUserIdAndPostId(Long userId, Long postId);

    int countByPostId(Long postId);

}
