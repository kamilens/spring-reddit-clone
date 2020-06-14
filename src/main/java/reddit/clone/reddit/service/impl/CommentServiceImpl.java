package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.NotificationEmail;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.exception.BadRequestException;
import reddit.clone.reddit.exception.NotFoundException;
import reddit.clone.reddit.mapper.CommentMapper;
import reddit.clone.reddit.repository.CommentRepository;
import reddit.clone.reddit.repository.PostRepository;
import reddit.clone.reddit.repository.UserRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.CommentService;
import reddit.clone.reddit.service.MailContentBuilder;
import reddit.clone.reddit.service.MailService;
import reddit.clone.reddit.vm.comment.CommentCreateRequestVM;
import reddit.clone.reddit.vm.comment.CommentResponseVM;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final String POST_URL;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final UserRepository userRepository;

    @Override
    public void createComment(CommentCreateRequestVM commentCreateRequestVM) {
        if (!postRepository.findById(commentCreateRequestVM.getPostId()).isPresent()) {
            throw new NotFoundException("No post found with id: " + commentCreateRequestVM.getPostId());
        }

        User currentUser = authService.getCurrentUser();

        try {
            commentRepository.save(
                    commentMapper.createRequestVmToEntity(commentCreateRequestVM, currentUser)
            );
        } catch (Exception ex) {
            throw new BadRequestException();
        }

        String message = mailContentBuilder.build(
                currentUser.getUsername() + " posted a comment on your post." + POST_URL
        );

        sendCommentNotification(message, currentUser);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<CommentResponseVM> getAllCommentsForPost(Pageable pageable, Long postId) {
        if (!postRepository.findById(postId).isPresent()) {
            throw new NotFoundException("No post found with id: " + postId);
        }

        return commentRepository.findAllByPostId(pageable, postId)
                .stream()
                .map(commentMapper::entityToResponseVM)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<CommentResponseVM> getCommentsByUsername(Pageable pageable, String username) {
        if (!userRepository.findByUsername(username).isPresent()) {
            throw new NotFoundException("No user found with username: " + username);
        }

        return commentRepository.findAllByUserUsername(pageable, username)
                .stream()
                .map(commentMapper::entityToResponseVM)
                .collect(Collectors.toSet());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(
                NotificationEmail.builder()
                        .subject(user.getUsername() + " Commented on your post")
                        .recipient(user.getEmail())
                        .body(message)
                        .build()
                );
    }

}
