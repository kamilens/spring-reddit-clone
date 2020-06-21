package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reddit.clone.reddit.service.UserProfileService;
import reddit.clone.reddit.vm.user.UploadUserImagesVM;
import reddit.clone.reddit.vm.user.UserProfileResponseVM;
import reddit.clone.reddit.vm.user.UserProfileUpdateVM;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PutMapping
    public ResponseEntity<Void> updateProfileInfo(@RequestBody @Valid UserProfileUpdateVM userProfileUpdateVM) {
        userProfileService.updateProfileInfo(userProfileUpdateVM);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponseVM> getUserProfileInfo(@PathVariable String username) {
        return ResponseEntity.ok(userProfileService.getUserProfileInfo(username));
    }

    @GetMapping
    public ResponseEntity<UserProfileResponseVM> getMyProfileInfo() {
        return ResponseEntity.ok(userProfileService.getMyProfileInfo());
    }

    @PostMapping("/upload-images")
    public ResponseEntity<Void> uploadUserImages(@RequestPart(value = "avatarImage", required = false) MultipartFile avatarImage,
                                                 @RequestPart(value = "bannerImage", required = false) MultipartFile bannerImage) {
        UploadUserImagesVM uploadUserImagesVM = UploadUserImagesVM
                .builder()
                .avatarImage(avatarImage)
                .bannerImage(bannerImage)
                .build();
        userProfileService.uploadUserImages(uploadUserImagesVM);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
