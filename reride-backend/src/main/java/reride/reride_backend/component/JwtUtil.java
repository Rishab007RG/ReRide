package reride.reride_backend.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reride.reride_backend.enums.Role;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    //    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private final Key secretKey;

    private final long jwtExpiration;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long jwtExpiration
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.jwtExpiration = jwtExpiration;
    }
    public String generateToken(String email, Long userId, Role role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId",userId)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration)) //session time
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    public String extractUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();  // this is the email/username
    }

    public Long extractUserId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Long.class);  // this is your custom claim
    }

    public String extractUserRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role", String.class);
    }



    public boolean validateToken(String token) {
        Date expirationDate = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return !expirationDate.before(new Date());
    }
}
