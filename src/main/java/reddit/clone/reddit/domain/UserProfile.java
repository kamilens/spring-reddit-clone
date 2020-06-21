package reddit.clone.reddit.domain;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "user_profiles")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "display_name")
    private String displayName;

    @Nullable
    @Lob
    @Column(name = "about")
    private String about;

    @Column(name = "avatar_image")
    private String avatarImage;

    @Column(name = "banner_image")
    private String bannerImage;

    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
