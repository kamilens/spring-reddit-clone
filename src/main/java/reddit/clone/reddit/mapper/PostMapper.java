package reddit.clone.reddit.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import reddit.clone.reddit.constants.VoteType;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.Vote;
import reddit.clone.reddit.repository.CommentRepository;
import reddit.clone.reddit.repository.VoteRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.vm.post.PostCreateRequestVM;
import reddit.clone.reddit.vm.post.PostResponseVM;
import reddit.clone.reddit.vm.post.PostUpdateRequestVM;

import java.util.Optional;

import static reddit.clone.reddit.constants.VoteType.DOWN_VOTE;
import static reddit.clone.reddit.constants.VoteType.UP_VOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private VoteRepository voteRepository;

    @Mappings({
            @Mapping(target = "subreddit", ignore = true),
            @Mapping(target = "subreddit.id", source = "postCreateRequestVM.subredditId"),
            @Mapping(target = "user.id", source = "user.id"),
            @Mapping(target = "voteCount", constant = "0"),
            @Mapping(target = "description", source = "postCreateRequestVM.description"),
            @Mapping(target = "title", source = "postCreateRequestVM.title"),
            @Mapping(target = "url", source = "postCreateRequestVM.url"),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "modificationDate", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    public abstract Post createRequestVmToEntity(PostCreateRequestVM postCreateRequestVM, User user);

    @Mappings({
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "modificationDate", ignore = true),
            @Mapping(target = "subreddit", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "voteCount", ignore = true)
    })
    public abstract Post updateRequestVmToEntity(PostUpdateRequestVM postUpdateRequestVM);

    @Mappings({
            @Mapping(target = "author", source = "user.username"),
            @Mapping(target = "subredditId", source = "subreddit.id"),
            @Mapping(target = "subredditName", source = "subreddit.name"),
            @Mapping(target = "commentCount", expression = "java(commentCount(post))"),
            @Mapping(target = "duration", expression = "java(getDuration(post))"),
            @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))"),
            @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))"),
    })
    public abstract PostResponseVM entityToResponseVM(Post post);


    // Util
    protected Integer commentCount(Post post) {
        return commentRepository.countAllByPostId(post.getId());
    }

    protected String getDuration(Post post) {
        return TimeAgo.using(post.getCreationDate().getTime());
    }

    protected boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UP_VOTE);
    }

    protected boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWN_VOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedId()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByIdDesc(
                    post,
                    authService.getCurrentUser()
            );
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }

}
