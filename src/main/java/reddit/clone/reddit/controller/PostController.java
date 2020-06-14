package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reddit.clone.reddit.service.PostService;
import reddit.clone.reddit.util.PageableUtil;
import reddit.clone.reddit.vm.post.PostCreateRequestVM;
import reddit.clone.reddit.vm.post.PostResponseVM;

import javax.validation.Valid;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody @Valid PostCreateRequestVM postCreateRequestVM) {
        postService.createPost(postCreateRequestVM);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<Set<PostResponseVM>> getAllPosts(Pageable pageable) {
        Set<PostResponseVM> posts = postService.getAllPosts(pageable);
        return PageableUtil.getPageResponse(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseVM> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/by-subreddit/{subredditId}")
    public ResponseEntity<Set<PostResponseVM>> getPostsBySubredditId(Pageable pageable, @PathVariable Long subredditId) {
        Set<PostResponseVM> posts = postService.getPostsBySubredditId(pageable, subredditId);
        return PageableUtil.getPageResponse(posts);
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<Set<PostResponseVM>> getPostsByUsername(Pageable pageable, @PathVariable String username) {
        Set<PostResponseVM> posts = postService.getPostsByUsername(pageable, username);
        return PageableUtil.getPageResponse(posts);
    }

}
