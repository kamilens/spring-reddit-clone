package reddit.clone.reddit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reddit.clone.reddit.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByPostId(Pageable pageable, Long postId);

    Integer countAllByPostId(Long postId);

    Page<Comment> findAllByUserUsername(Pageable pageable, String username);

}
