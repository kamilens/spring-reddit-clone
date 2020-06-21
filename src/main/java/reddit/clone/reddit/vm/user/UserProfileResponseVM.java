package reddit.clone.reddit.vm.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseVM {

    private Long id;
    private String displayName;
    private String about;
    private String avatarImage;
    private String bannerImage;

}
