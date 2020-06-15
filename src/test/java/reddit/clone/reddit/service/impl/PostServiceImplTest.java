package reddit.clone.reddit.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.Subreddit;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.mapper.PostMapper;
import reddit.clone.reddit.repository.PostRepository;
import reddit.clone.reddit.repository.SubredditRepository;
import reddit.clone.reddit.repository.UserRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.vm.post.PostCreateRequestVM;
import reddit.clone.reddit.vm.post.PostResponseVM;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock private PostRepository postRepository;
    @Mock private SubredditRepository subredditRepository;
    @Mock private UserRepository userRepository;
    @Mock private PostMapper postMapper;
    @Mock private AuthService authService;

    @InjectMocks private PostServiceImpl postService;

    @Test
    void createPost() {
        // Prepare
        final PostCreateRequestVM mockPostVM = PostCreateRequestVM
                .builder()
                .title("Test title")
                .url("/test/url")
                .description("Test description")
                .subredditId(1L)
                .build();

        final Post mockPostEntity = Post
                .builder()
                .title(mockPostVM.getTitle())
                .url(mockPostVM.getUrl())
                .description(mockPostVM.getDescription())
                .subreddit(Subreddit.builder().id(mockPostVM.getSubredditId()).build())
                .build();

        final User mockUser = User.builder().id(1L).build();

        given(subredditRepository.findById(mockPostVM.getSubredditId()))
                .willReturn(Optional.of(new Subreddit()));
        given(authService.getCurrentUser())
                .willReturn(mockUser);
        given(postMapper.createRequestVmToEntity(mockPostVM, mockUser))
                .willReturn(mockPostEntity);
        given(postRepository.save(mockPostEntity))
                .willReturn(mockPostEntity);

        // Testing
        postService.createPost(mockPostVM);

        // Validate
        Assert.assertNotNull(mockUser.getId());
        Mockito.verify(subredditRepository).findById(mockPostVM.getSubredditId());
        Mockito.verify(authService).getCurrentUser();
        Mockito.verify(postMapper).createRequestVmToEntity(mockPostVM, mockUser);
        Mockito.verify(postRepository).save(mockPostEntity);
    }

    @Test
    void getAllPosts() {
        final int testPageSize = 15;
        PageRequest pageRequest = PageRequest.of(0, testPageSize);

        given(postRepository.findAll(pageRequest))
                .willReturn(new PageImpl<>(
                        new ArrayList<Post>() {{ Post.builder().build(); }}
                ));

        Set<PostResponseVM> posts = postService.getAllPosts(pageRequest);
        Assert.assertTrue(posts.size() <= testPageSize);
    }

    @Test
    void getPostById() {
    }

    @Test
    void getPostsBySubredditId() {
    }

    @Test
    void getPostsByUsername() {
    }

}