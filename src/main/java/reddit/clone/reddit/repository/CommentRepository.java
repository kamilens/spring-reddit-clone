package reddit.clone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reddit.clone.reddit.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}