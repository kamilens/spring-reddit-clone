package reddit.clone.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reddit.clone.reddit.constants.VoteType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    private Long id;
    private VoteType voteType;
    private PostDTO post;
    private UserDTO user;

}
