package reddit.clone.reddit.vm.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenVM {

    @NotBlank
    @NotNull
    @NotEmpty
    private String refreshToken;

    @NotBlank
    @NotNull
    @NotEmpty
    private String username;

}
