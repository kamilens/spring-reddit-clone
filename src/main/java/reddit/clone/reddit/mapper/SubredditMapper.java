package reddit.clone.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.Subreddit;
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
    Subreddit createRequestVmToEntity(SubredditCreateRequestVM subredditCreateRequestVM);
    Subreddit updateRequestVmToEntity(SubredditUpdateRequestVM subredditUpdateRequestVM);

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    @Mapping(target = "authorId", source = "user.id")
    @Mapping(target = "authorUsername", source = "user.username")
    SubredditResponseVM entityToResponseVM(Subreddit subreddit);


    // Util
    default Integer mapPosts(Set<Post> posts) {
        return posts.size();
    }

}
