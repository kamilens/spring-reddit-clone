package reddit.clone.reddit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @NotNull
    @NotEmpty
    @Column(name = "username", unique = true)
    private String username;

    @Size(min = 7)
    @NotBlank
    @NotNull
    @NotEmpty
    @Column(name = "password")
    private String password;

    @NotBlank
    @NotNull
    @NotEmpty
    @Email
    @Column(name = "email", unique = true)
    private String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "enabled")
    private boolean enabled;

}
