package reddit.clone.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.Subreddit;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.vm.subreddit.SubredditCreateRequestVM;
import reddit.clone.reddit.vm.subreddit.SubredditResponseVM;
import reddit.clone.reddit.vm.subreddit.SubredditUpdateRequestVM;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    // DTO
    /*@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(target = "posts", ignore = true)
    SubredditDTO dtoToEntity(Subreddit subreddit);*/

    /*@InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit entityToDto(SubredditDTO subredditDTO);*/


    // VM
    @Mappings({
            @Mapping(target = "user.id", source = "user.id"),
            @Mapping(target = "description", source = "subredditCreateRequestVM.description"),
            @Mapping(target = "name", source = "subredditCreateRequestVM.name"),
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "posts", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Subreddit createRequestVmToEntity(SubredditCreateRequestVM subredditCreateRequestVM, User user);

    @Mappings({
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "posts", ignore = true),
            @Mapping(target = "user", ignore = true)
    })
    Subreddit updateRequestVmToEntity(SubredditUpdateRequestVM subredditUpdateRequestVM);

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(target = "author", source = "user.username")
    SubredditResponseVM entityToResponseVM(Subreddit subreddit);


    // Util
    default Integer mapPosts(Set<Post> posts) {
        return posts.size();
    }

}
