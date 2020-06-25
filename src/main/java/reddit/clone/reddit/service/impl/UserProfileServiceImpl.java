package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.UserProfile;
import reddit.clone.reddit.exception.BadRequestException;
import reddit.clone.reddit.exception.NotFoundException;
import reddit.clone.reddit.exception.RedditException;
import reddit.clone.reddit.mapper.UserProfileMapper;
import reddit.clone.reddit.repository.UserProfileRepository;
import reddit.clone.reddit.repository.UserRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.StorageService;
import reddit.clone.reddit.service.UserProfileService;
import reddit.clone.reddit.vm.user.UploadUserImagesVM;
import reddit.clone.reddit.vm.user.UserProfileResponseVM;
import reddit.clone.reddit.vm.user.UserProfileUpdateVM;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final AuthService authService;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserRepository userRepository;
    private final StorageService storageService;

    @Override
    public void updateProfileInfo(UserProfileUpdateVM userProfileUpdateVM) {
        User currentUser = authService.getCurrentUser();
        try {
            userProfileRepository.save(
                    userProfileMapper.profileUpdateRequestVmToEntity(userProfileUpdateVM, currentUser)
            );
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfileResponseVM getUserProfileInfo(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("No user found with username: " + username));
        final UserProfile currentUserProfile = userProfileRepository.findUserProfileByUser_Username(user.getUsername());
        return userProfileMapper.entityToProfileResponseVM(currentUserProfile);
    }

    @Transactional(readOnly = true)
    @Override
    public UserProfileResponseVM getMyProfileInfo() {
        final User currentUser = authService.getCurrentUser();
        UserProfile currentUserProfile = userProfileRepository.findUserProfileByUser_Username(currentUser.getUsername());
        return userProfileMapper.entityToProfileResponseVM(currentUserProfile);
    }

    @Override
    public void uploadUserImages(UploadUserImagesVM uploadUserImagesVM) {
        UserProfile currentUserProfile = authService.getCurrentUser().getUserProfile();

        MultipartFile avatarImage = uploadUserImagesVM.getAvatarImage();
        MultipartFile bannerImage = uploadUserImagesVM.getBannerImage();

        try {
            String avatarFullPath = null;
            if (avatarImage != null) {
                avatarFullPath = storageService.upload(avatarImage);
            }
            userProfileRepository.changeUserAvatarImage(avatarFullPath, currentUserProfile.getId());

            String currentAvatarImageFullPath = currentUserProfile.getAvatarImage();
            if (currentAvatarImageFullPath != null) {
                storageService.delete(currentAvatarImageFullPath);
            }


            String bannerFullPath = null;
            if (bannerImage != null) {
                bannerFullPath = storageService.upload(bannerImage);
            }
            userProfileRepository.changeUserBannerImage(bannerFullPath, currentUserProfile.getId());

            String currentBannerImageFullPath = currentUserProfile.getBannerImage();
            if (currentBannerImageFullPath != null) {
                storageService.delete(currentBannerImageFullPath);
            }
        } catch (Exception ex) {
            throw new RedditException("Exception occurred when uploading images");
        }
    }

}