package reddit.clone.reddit.service;

import reddit.clone.reddit.vm.RegisterVM;

public interface AuthService {

    void register(RegisterVM registerVM);

    void verifyAccount(String token);

}
