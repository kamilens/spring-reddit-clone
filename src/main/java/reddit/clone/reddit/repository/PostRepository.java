package reddit.clone.reddit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reddit.clone.reddit.domain.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByUserUsername(String username, Pageable pageable);

    Page<Post> findAllBySubredditId(Long subredditId, Pageable pageable);

}
