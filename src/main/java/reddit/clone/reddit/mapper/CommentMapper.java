package reddit.clone.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import reddit.clone.reddit.domain.Comment;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.vm.comment.CommentCreateRequestVM;
import reddit.clone.reddit.vm.comment.CommentResponseVM;
import reddit.clone.reddit.vm.comment.CommentUpdateRequestVM;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "post", ignore = true),
            @Mapping(target = "post.id", source = "commentCreateRequestVM.postId"),
            @Mapping(target = "text", source = "commentCreateRequestVM.text"),
            @Mapping(target = "user.id", source = "user.id")
    })
    Comment createRequestVmToEntity(CommentCreateRequestVM commentCreateRequestVM, User user);

    @Mappings({
            @Mapping(target = "creationDate", ignore = true),
            @Mapping(target = "post", ignore = true),
            @Mapping(target = "post.id", source = "postId"),
            @Mapping(target = "text", source = "text"),
            @Mapping(target = "user", ignore = true)
    })
    Comment updateRequestVmToEntity(CommentUpdateRequestVM commentUpdateRequestVM);

    @Mappings({
            @Mapping(target = "postId", source = "post.id"),
            @Mapping(target = "userId", source = "user.id")
    })
    CommentResponseVM entityToResponseVM(Comment comment);

}
