package reddit.clone.reddit.service;

import org.springframework.data.domain.Pageable;
import reddit.clone.reddit.vm.subreddit.SubredditCreateRequestVM;
import reddit.clone.reddit.vm.subreddit.SubredditResponseVM;

import java.util.Set;

public interface SubredditService {

    void createSubreddit(SubredditCreateRequestVM subredditCreateRequestVM);

    Set<SubredditResponseVM> getAllSubreddits(Pageable pageable);

    SubredditResponseVM getSubredditById(Long subredditId);

}
