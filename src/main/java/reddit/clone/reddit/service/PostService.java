package reddit.clone.reddit.service;

import org.springframework.data.domain.Pageable;
import reddit.clone.reddit.vm.post.PostCreateRequestVM;
import reddit.clone.reddit.vm.post.PostResponseVM;

import java.util.Set;

public interface PostService {

    void createPost(PostCreateRequestVM postCreateRequestVM);

    Set<PostResponseVM> getAllPosts(Pageable pageable);

    PostResponseVM getPostById(Long postId);

    Set<PostResponseVM> getPostsBySubredditId(Long subredditId, Pageable pageable);

    Set<PostResponseVM> getPostsByUsername(String username, Pageable pageable);

}
