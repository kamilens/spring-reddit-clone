package reddit.clone.reddit.vm.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reddit.clone.reddit.domain.Post;
import reddit.clone.reddit.domain.User;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseVM {

    private Long id;
    private String text;
    private Long postId;
    private Date creationDate;
    private Long userId;

}
