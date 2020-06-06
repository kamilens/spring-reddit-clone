package reddit.clone.reddit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "verification_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String token;

    @OneToOne(fetch = LAZY)
    private User user;

    @Column(name = "expiry_date")
    private Instant expiryDate;

}
