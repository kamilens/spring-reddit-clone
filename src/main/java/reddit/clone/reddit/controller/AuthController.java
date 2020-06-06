package reddit.clone.reddit.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.vm.RegisterVM;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterVM registerVM) {
        authService.register(registerVM);
        return ResponseEntity.ok("User registration successful!");
    }

    @PostMapping("/verify/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return ResponseEntity.ok("Account activated successfully!");
    }

}
