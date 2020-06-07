package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.NotificationEmail;
import reddit.clone.reddit.domain.User;
import reddit.clone.reddit.domain.VerificationToken;
import reddit.clone.reddit.exception.BadRequestException;
import reddit.clone.reddit.exception.RedditException;
import reddit.clone.reddit.repository.UserRepository;
import reddit.clone.reddit.repository.VerificationTokenRepository;
import reddit.clone.reddit.security.JwtProvider;
import reddit.clone.reddit.service.AuthService;
import reddit.clone.reddit.service.MailService;
import reddit.clone.reddit.vm.auth.AuthenticationResponseVM;
import reddit.clone.reddit.vm.auth.LoginVM;
import reddit.clone.reddit.vm.auth.RegisterVM;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Override
    public void register(RegisterVM registerVM) {
        User user = User.builder()
                .username(registerVM.getUsername())
                .email(registerVM.getEmail())
                .password(passwordEncoder.encode(registerVM.getPassword()))
                .build();

        try {
            userRepository.save(user);
        } catch (Exception ex) {
            throw new BadRequestException();
        }

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

    @Override
    public AuthenticationResponseVM login(LoginVM loginVM) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginVM.getUsername(), loginVM.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponseVM(token, loginVM.getUsername());
    }

    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
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
