package reddit.clone.reddit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    UserProfile findUserProfileByUser_Username(String username);

    @Modifying
    @Query("update UserProfile u set u.avatarImage=:avatarImagePath where u.id=:userProfileId")
    void changeUserAvatarImage(@Param("avatarImagePath") String avatarImagePath, @Param("userProfileId") Long userProfileId);

    @Modifying
    @Query("update UserProfile u set u.bannerImage=:bannerImagePath where u.id=:userProfileId")
    void changeUserBannerImage(@Param("bannerImagePath") String bannerImagePath, @Param("userProfileId") Long userProfileId);

}
