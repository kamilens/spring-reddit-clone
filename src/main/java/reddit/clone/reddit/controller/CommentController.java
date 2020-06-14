package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reddit.clone.reddit.service.CommentService;
import reddit.clone.reddit.util.PageableUtil;
import reddit.clone.reddit.vm.comment.CommentCreateRequestVM;
import reddit.clone.reddit.vm.comment.CommentResponseVM;

import javax.validation.Valid;
import java.util.Set;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    private ResponseEntity<Void> createComment(@RequestBody @Valid CommentCreateRequestVM commentCreateRequestVM) {
        commentService.createComment(commentCreateRequestVM);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/by-post/{postId}")
    private ResponseEntity<Set<CommentResponseVM>> getAllCommentsForPost(Pageable pageable, @PathVariable Long postId) {
        Set<CommentResponseVM> comments = commentService.getAllCommentsForPost(pageable, postId);
        return PageableUtil.getPageResponse(comments);
    }

    @GetMapping("/by-user/{username}")
    private ResponseEntity<Set<CommentResponseVM>> getCommentsByUsername(Pageable pageable, @PathVariable String username) {
        Set<CommentResponseVM> comments = commentService.getCommentsByUsername(pageable, username);
        return PageableUtil.getPageResponse(comments);
    }

}
