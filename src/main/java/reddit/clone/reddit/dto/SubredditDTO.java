package reddit.clone.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubredditDTO {

    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
    private Set<PostDTO> posts = new HashSet<>();
    private Date creationDate;
    private UserDTO user;

}
