package reddit.clone.reddit.service;

import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.vm.auth.*;

public interface AuthService {

    void register(RegisterVM registerVM);

    void verifyAccount(String token);

    AuthenticationResponseVM login(LoginVM loginVM);

    User getCurrentUser();

    AuthenticationResponseVM refreshToken(RefreshTokenVM refreshTokenVM);

    boolean isLoggedId();

    void changePassword(ChangePasswordVM changePasswordVM);

    void changeEmail(ChangeEmailVM changeEmailVM);

//    void changeUsername(ChangeUsernameVM changeUsernameVM);

}
