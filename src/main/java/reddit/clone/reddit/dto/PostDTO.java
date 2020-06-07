package reddit.clone.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    private Long id;
    private String title;
    private String url;
    private String description;
    private Integer voteCount;
    private UserDTO user;
    private Date creationDate;
    private Date modificationDate;
    private SubredditDTO subreddit;

}
