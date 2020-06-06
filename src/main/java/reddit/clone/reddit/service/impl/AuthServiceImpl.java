package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.NotificationEmail;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.VerificationToken;
import reddit.clone.reddit.exception.RedditException;
import reddit.clone.reddit.repository.UserRepository;
import reddit.clone.reddit.repository.VerificationTokenRepository;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.MailService;
import reddit.clone.reddit.vm.RegisterVM;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    @Override
    public void register(RegisterVM registerVM) {
        User user = User.builder()
                .username(registerVM.getUsername())
                .email(registerVM.getEmail())
                .password(passwordEncoder.encode(registerVM.getPassword()))
                .build();

        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(
                new NotificationEmail(
                        "Please activate your account",
                        user.getEmail(), "Thank you for singing up to RedditClone, " +
                        "please click on the below url to activate your account: " +
                        "http://localhost:8080/api/auth/verification/" + token));
    }

    @Override
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new RedditException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RedditException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = VerificationToken.builder()
                .token(token)
                .user(user)
                .build();

        // TODO: impl. expire date

        verificationTokenRepository.save(verificationToken);
        return token;
    }

}
