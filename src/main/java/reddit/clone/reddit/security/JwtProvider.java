package reddit.clone.reddit.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.Key;

@Service
public class JwtProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(getMd5Secret().getBytes());
    }

    private String getMd5Secret() {
        return DigestUtils.md5DigestAsHex(secret.getBytes());
    }

    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getSigningKey()).parseClaimsJws(jwt);
        return true;
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
