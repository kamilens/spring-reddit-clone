package reddit.clone.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.vm.post.PostCreateRequestVM;
import reddit.clone.reddit.vm.post.PostResponseVM;
import reddit.clone.reddit.vm.post.PostUpdateRequestVM;

@Mapper(componentModel = "spring")
public interface PostMapper {
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
    Post createRequestVmToEntity(PostCreateRequestVM postCreateRequestVM, User user);

    // VM

    @Mappings({
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "modificationDate", ignore = true),
            @Mapping(target = "subreddit", ignore = true),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "voteCount", ignore = true)
    })
    Post updateRequestVmToEntity(PostUpdateRequestVM postUpdateRequestVM);

    @Mappings({
            @Mapping(target = "author", source = "user.username"),
            @Mapping(target = "subredditId", source = "subreddit.id"),
            @Mapping(target = "subredditName", source = "subreddit.name")
    })
    PostResponseVM entityToResponseVM(Post post);

}
