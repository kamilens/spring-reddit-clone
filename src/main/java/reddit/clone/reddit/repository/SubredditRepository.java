package reddit.clone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reddit.clone.reddit.domain.Subreddit;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {
}
