package reddit.clone.reddit.vm.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequestVM {

    @NotBlank
    @NotNull
    @NotEmpty
    private String text;

    @NotNull
    private Long postId;

}
