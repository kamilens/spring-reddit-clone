package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reddit.clone.reddit.service.VoteService;
import reddit.clone.reddit.vm.vote.VoteCreateVM;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/comments")
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity<Void> vote(@RequestBody VoteCreateVM voteCreateVM) {
        voteService.vote(voteCreateVM);
        return ResponseEntity.ok().body(null);
    }

}
