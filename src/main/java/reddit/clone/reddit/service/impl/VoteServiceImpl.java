package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.Vote;
import reddit.clone.reddit.exception.NotFoundException;
import reddit.clone.reddit.exception.RedditException;
import reddit.clone.reddit.mapper.VoteMapper;
import reddit.clone.reddit.repository.PostRepository;
import reddit.clone.reddit.repository.VoteRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.VoteService;
import reddit.clone.reddit.vm.vote.VoteCreateVM;

import java.util.Optional;

import static reddit.clone.reddit.constants.VoteType.UP_VOTE;

@RequiredArgsConstructor
@Slf4j
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;
    private final VoteMapper voteMapper;

    @Transactional
    @Override
    public void vote(VoteCreateVM voteCreateVM) {
        Post post = postRepository.findById(voteCreateVM.getPostId()).orElseThrow(() ->
            new NotFoundException("No post found with id: " + voteCreateVM.getPostId())
        );

        User currentUser = authService.getCurrentUser();

        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByIdDesc(
                post,
                currentUser
        );

        if (voteByPostAndUser.isPresent()) {
            throw new RedditException("You have already " + voteCreateVM.getVoteType() + "'d for this post");
        }

        if (UP_VOTE.equals(voteCreateVM.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }

        voteRepository.save(voteMapper.createRequestVmToEntity(voteCreateVM, currentUser));
        postRepository.save(post);
    }

}
