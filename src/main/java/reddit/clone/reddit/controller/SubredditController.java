package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reddit.clone.reddit.service.SubredditService;
import reddit.clone.reddit.vm.subreddit.SubredditCreateRequestVM;
import reddit.clone.reddit.vm.subreddit.SubredditResponseVM;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<Void> createSubreddit(@RequestBody SubredditCreateRequestVM subredditCreateRequestVM) {
        subredditService.createSubreddit(subredditCreateRequestVM);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<Set<SubredditResponseVM>> getAllSubreddits(Pageable pageable) {
        Set<SubredditResponseVM> subreddits = subredditService.getAllSubreddits(pageable);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(subreddits.size()));

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(subreddits);
    }

   @GetMapping("/{subredditId}")
   public ResponseEntity<SubredditResponseVM> getSubredditById(@PathVariable Long subredditId) {
        return ResponseEntity.ok(subredditService.getSubredditById(subredditId));
   }

}
