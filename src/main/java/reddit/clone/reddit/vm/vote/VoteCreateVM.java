package reddit.clone.reddit.vm.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reddit.clone.reddit.constants.VoteType;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteCreateVM {

    @NotNull
    private VoteType voteType;

    @NotNull
    private Long postId;

}
