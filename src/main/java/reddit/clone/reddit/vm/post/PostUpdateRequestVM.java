package reddit.clone.reddit.vm.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestVM {

    @NotBlank
    @NotNull
    @NotEmpty
    private Long id;

    @NotBlank
    @NotNull
    @NotEmpty
    private String title;

    @NotBlank
    @NotNull
    @NotEmpty
    private String url;

    @NotBlank
    @NotNull
    @NotEmpty
    private String description;

}
