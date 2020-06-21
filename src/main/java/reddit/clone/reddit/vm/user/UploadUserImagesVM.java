package reddit.clone.reddit.vm.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadUserImagesVM {

    @Nullable private MultipartFile avatarImage;
    @Nullable private MultipartFile bannerImage;

}
