package reddit.clone.reddit.service;

import org.springframework.data.domain.Pageable;
import reddit.clone.reddit.vm.comment.CommentCreateRequestVM;
import reddit.clone.reddit.vm.comment.CommentResponseVM;

import java.util.Set;

public interface CommentService {

    void createComment(CommentCreateRequestVM commentCreateRequestVM);

    Set<CommentResponseVM> getAllCommentsForPost(Pageable pageable, Long postId);

    Set<CommentResponseVM> getCommentsByUsername(Pageable pageable, String username);

}
