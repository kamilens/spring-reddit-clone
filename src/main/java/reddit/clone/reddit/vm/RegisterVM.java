package reddit.clone.reddit.vm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVM {

    private String email;
    private String username;
    private String password;

}
