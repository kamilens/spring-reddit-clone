package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import reddit.clone.reddit.service.RefreshTokenService;
import reddit.clone.reddit.vm.auth.*;

import java.time.Instant;
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
    private final RefreshTokenService refreshTokenService;

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
                NotificationEmail.builder()
                        .subject("Please activate your account")
                        .recipient(user.getEmail())
                        .body("Thank you for singing up to RedditClone, " +
                                "please click on the below url to activate your account: " +
                                "http://localhost:8080/api/auth/verification/" + token)
                        .build()
                );
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
        return AuthenticationResponseVM
                .builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginVM.getUsername())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Override
    public AuthenticationResponseVM refreshToken(RefreshTokenVM refreshTokenVM) {
        refreshTokenService.validateRefreshToken(refreshTokenVM.getRefreshToken());

        String token = jwtProvider.generateTokenWithUsername(refreshTokenVM.getUsername());
        return AuthenticationResponseVM
                .builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenVM.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenVM.getUsername())
                .build();
    }

    @Override
    public boolean isLoggedId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (!(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated());
    }

    @Override
    public void changePassword(ChangePasswordVM changePasswordVM) {
        final User currentUser = getCurrentUser();
        final String newPasswordFromRequestEncoded = passwordEncoder.encode(changePasswordVM.getNewPassword());

        validatePassword(changePasswordVM.getCurrentPassword());

        try {
            userRepository.changePassword(currentUser.getUsername(), newPasswordFromRequestEncoded);
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

    @Override
    public void changeEmail(ChangeEmailVM changeEmailVM) {
        final User user = getCurrentUser();
        validatePassword(changeEmailVM.getCurrentPassword());

        try {
            userRepository.changeEmail(user.getUsername(), changeEmailVM.getNewEmail());
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }

/*
    @Override
    public void changeUsername(ChangeUsernameVM changeUsernameVM) {
        final User user = getCurrentUser();
        validatePassword(changeUsernameVM.getCurrentPassword());

        try {
            userRepository.changeUsername(user.getUsername(), changeUsernameVM.getNewUsername());
        } catch (Exception ex) {
            throw new BadRequestException();
        }
    }
*/

    private void validatePassword(String passedAsCurrentPassword) {
        final User currentUser = getCurrentUser();

        if (!passwordEncoder.matches(passedAsCurrentPassword, currentUser.getPassword())) {
            throw new RedditException("Current password isn't valid!!");
        }
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
