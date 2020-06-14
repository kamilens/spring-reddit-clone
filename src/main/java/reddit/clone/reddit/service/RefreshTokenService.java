package reddit.clone.reddit.service;

import reddit.clone.reddit.domain.RefreshToken;

public interface RefreshTokenService {

    RefreshToken generateRefreshToken();

    void validateRefreshToken(String token);

    void deleteRefreshToken(String token);

}
