package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.RefreshTokenService;
import reddit.clone.reddit.vm.auth.AuthenticationResponseVM;
import reddit.clone.reddit.vm.auth.LoginVM;
import reddit.clone.reddit.vm.auth.RefreshTokenVM;
import reddit.clone.reddit.vm.auth.RegisterVM;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterVM registerVM) {
        authService.register(registerVM);
        return ResponseEntity.ok("User registration successful!");
    }

    @PostMapping("/verify/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok("Account activated successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseVM> login(@RequestBody @Valid LoginVM loginVM) {
        return ResponseEntity.ok(authService.login(loginVM));
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponseVM refreshTokens(@RequestBody @Valid RefreshTokenVM refreshTokenVM) {
        return authService.refreshToken(refreshTokenVM);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody @Valid RefreshTokenVM refreshTokenVM) {
        refreshTokenService.deleteRefreshToken(refreshTokenVM.getRefreshToken());
        return ResponseEntity.ok().body("Refresh token deleted successfully!!");
    }

}
