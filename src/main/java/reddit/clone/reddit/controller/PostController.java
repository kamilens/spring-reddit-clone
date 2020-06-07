package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reddit.clone.reddit.service.PostService;
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
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(posts.size()));

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseVM> getPostById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @GetMapping("/by-subreddit/{subredditId}")
    public ResponseEntity<Set<PostResponseVM>> getPostsBySubredditId(@PathVariable Long subredditId, Pageable pageable) {
        return ResponseEntity.ok(postService.getPostsBySubredditId(subredditId, pageable));
    }

    @GetMapping("/by-user/{username}")
    public ResponseEntity<Set<PostResponseVM>> getPostsByUsername(@PathVariable String username, Pageable pageable) {
        return ResponseEntity.ok(postService.getPostsByUsername(username, pageable));
    }

}
