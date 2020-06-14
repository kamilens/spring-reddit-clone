package reddit.clone.reddit.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reddit.clone.reddit.domain.RefreshToken;
import reddit.clone.reddit.exception.RedditException;
import reddit.clone.reddit.repository.RefreshTokenRepository;
import reddit.clone.reddit.service.RefreshTokenService;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Transactional
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = RefreshToken
                .builder()
                .token(UUID.randomUUID().toString())
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RedditException("Invalid refresh token"));
    }

    @Override
    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}
