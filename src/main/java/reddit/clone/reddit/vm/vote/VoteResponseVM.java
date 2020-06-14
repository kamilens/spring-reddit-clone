package reddit.clone.reddit.vm.vote;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reddit.clone.reddit.constants.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteResponseVM {

    private Long id;
    private VoteType voteType;
    private Long postId;
    private Long userId;

}
