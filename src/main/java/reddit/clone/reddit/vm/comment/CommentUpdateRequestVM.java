package reddit.clone.reddit.vm.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reddit.clone.reddit.dto.PostDTO;
import reddit.clone.reddit.dto.UserDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateRequestVM {

    @NotBlank
    @NotNull
    @NotEmpty
    private Long id;

    @NotBlank
    @NotNull
    @NotEmpty
    private String text;

    @NotNull
    private Long postId;

}
