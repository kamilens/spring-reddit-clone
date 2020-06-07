package reddit.clone.reddit.service;

import reddit.clone.reddit.vm.auth.AuthenticationResponseVM;
import reddit.clone.reddit.vm.auth.LoginVM;
import reddit.clone.reddit.vm.auth.RegisterVM;

public interface AuthService {

    void register(RegisterVM registerVM);

    void verifyAccount(String token);

    AuthenticationResponseVM login(LoginVM loginVM);

}
