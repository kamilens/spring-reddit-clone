package reddit.clone.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.Vote;
import reddit.clone.reddit.vm.vote.VoteCreateVM;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mappings({
            @Mapping(target = "voteType", source = "voteCreateVM.voteType"),
            @Mapping(target = "post", ignore = true),
            @Mapping(target = "post.id", source = "voteCreateVM.postId"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "user.id", source = "user.id")
    })
    Vote createRequestVmToEntity(VoteCreateVM voteCreateVM, User user);


}
