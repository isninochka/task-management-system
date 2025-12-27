package isaeva.userservice.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;



@Component
public class JwtProvider {
    @Value("${jwt.secret:change_me}")
    private String secret;

    public String generateToken(String username, String role) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        long now = System.currentTimeMillis();
        return Jwts.builder().setSubject(username).claim("roles", role).setIssuedAt(new Date(now))
                .setExpiration(new Date(now + 1000L * 60 * 60 * 24)).signWith(key, SignatureAlgorithm.HS256).compact();
    }
}
