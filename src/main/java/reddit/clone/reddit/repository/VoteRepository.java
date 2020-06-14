package reddit.clone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.Vote;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByIdDesc(Post post, User user);

}
