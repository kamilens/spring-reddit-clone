package reddit.clone.reddit.vm.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseVM {

    private String authenticationToken;
    private String username;

}
