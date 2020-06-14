package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reddit.clone.reddit.service.SubredditService;
import reddit.clone.reddit.util.PageableUtil;
import reddit.clone.reddit.vm.subreddit.SubredditCreateRequestVM;
import reddit.clone.reddit.vm.subreddit.SubredditResponseVM;

import javax.validation.Valid;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/subreddits")
public class SubredditController {

    private final SubredditService subredditService;

    @PostMapping
    public ResponseEntity<Void> createSubreddit(@RequestBody @Valid SubredditCreateRequestVM subredditCreateRequestVM) {
        subredditService.createSubreddit(subredditCreateRequestVM);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<Set<SubredditResponseVM>> getAllSubreddits(Pageable pageable) {
        Set<SubredditResponseVM> subreddits = subredditService.getAllSubreddits(pageable);
        return PageableUtil.getPageResponse(subreddits);
    }

   @GetMapping("/{subredditId}")
   public ResponseEntity<SubredditResponseVM> getSubredditById(@PathVariable Long subredditId) {
        return ResponseEntity.ok(subredditService.getSubredditById(subredditId));
   }

}
