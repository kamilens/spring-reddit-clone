package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.exception.BadRequestException;
import reddit.clone.reddit.exception.NotFoundException;
import reddit.clone.reddit.mapper.PostMapper;
import reddit.clone.reddit.repository.PostRepository;
import reddit.clone.reddit.repository.SubredditRepository;
import reddit.clone.reddit.repository.UserRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.PostService;
import reddit.clone.reddit.vm.post.PostCreateRequestVM;
import reddit.clone.reddit.vm.post.PostResponseVM;

import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AuthService authService;

    @Override
    public void createPost(PostCreateRequestVM postCreateRequestVM) {
        subredditRepository.findById(postCreateRequestVM.getSubredditId())
                .orElseThrow(() -> new NotFoundException("No subreddit found with id: " + postCreateRequestVM.getSubredditId()));

        try {
            postRepository.save(
                    postMapper.createRequestVmToEntity(postCreateRequestVM, authService.getCurrentUser())
            );
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Set<PostResponseVM> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .stream()
                .map(postMapper::entityToResponseVM)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponseVM getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("No post found with id: " + postId));
        return postMapper.entityToResponseVM(post);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<PostResponseVM> getPostsBySubredditId(Long subredditId, Pageable pageable) {
        subredditRepository.findById(subredditId)
                .orElseThrow(() -> new NotFoundException("No subreddit found with id: " + subredditId));
        return postRepository.findAllBySubredditId(subredditId, pageable)
                .stream()
                .map(postMapper::entityToResponseVM)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    @Override
    public Set<PostResponseVM> getPostsByUsername(String username, Pageable pageable) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("No user found with username: " + username));

        return postRepository.findAllByUserUsername(username, pageable)
                .stream()
                .map(postMapper::entityToResponseVM)
                .collect(Collectors.toSet());
    }

}
