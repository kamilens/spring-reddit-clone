package reddit.clone.reddit.service;

import reddit.clone.reddit.vm.user.UploadUserImagesVM;
import reddit.clone.reddit.vm.user.UserProfileResponseVM;
import reddit.clone.reddit.vm.user.UserProfileUpdateVM;

public interface UserProfileService {

    void updateProfileInfo(UserProfileUpdateVM userProfileUpdateVM);

    UserProfileResponseVM getUserProfileInfo(String username);

    UserProfileResponseVM getMyProfileInfo();

    void uploadUserImages(UploadUserImagesVM uploadUserImagesVM);

}
