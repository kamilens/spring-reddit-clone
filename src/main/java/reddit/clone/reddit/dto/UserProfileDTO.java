package reddit.clone.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {

    private Long id;
    private String displayName;
    private String about;
    private String avatarImage;
    private String bannerImage;
    private UserDTO user;

}
