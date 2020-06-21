package reddit.clone.reddit.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.UserProfile;
import reddit.clone.reddit.vm.user.UserProfileResponseVM;
import reddit.clone.reddit.vm.user.UserProfileUpdateVM;

@Mapper(componentModel = "spring")
public interface UserProfileMapper {

    @Mappings({
            @Mapping(target = "id", source = "user.userProfile.id"),
            @Mapping(target = "displayName", source = "userProfileUpdateVM.displayName"),
            @Mapping(target = "about", source = "userProfileUpdateVM.about"),
            @Mapping(target = "user", ignore = true),
            @Mapping(target = "user.id", source = "user.id"),
            // TODO: fix bug
            @Mapping(target = "avatarImage", ignore = true),
            @Mapping(target = "bannerImage", ignore = true)
    })
    UserProfile profileUpdateRequestVmToEntity(UserProfileUpdateVM userProfileUpdateVM, User user);

    UserProfileResponseVM entityToProfileResponseVM(UserProfile userProfile);

}
