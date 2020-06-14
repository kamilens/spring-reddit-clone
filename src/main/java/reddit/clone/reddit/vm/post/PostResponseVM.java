package reddit.clone.reddit.vm.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseVM {

    private Long id;
    private String title;
    private String url;
    private String description;
    private Integer voteCount;
    private String author;
    private Date creationDate;
    private Date modificationDate;
    private Long subredditId;
    private String subredditName;
    private Integer commentCount;
    private String duration;

}
