package reddit.clone.reddit.vm.subreddit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubredditResponseVM {

    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private Date creationDate;
    private Long authorId;
    private String authorUsername;

}
