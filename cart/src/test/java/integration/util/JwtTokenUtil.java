package integration.util;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import java.time.Instant;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JwtTokenUtil {
    
    private static final String ISSUER = "https://test-issuer.com";
    private static final String AUDIENCE = "cart-service";
    
    public String generateUserToken(String userId) {
        return generateToken(userId, Set.of("user"));
    }
    
    public String generateAdminToken(String userId) {
        return generateToken(userId, Set.of("admin", "user"));
    }
    
    private String generateToken(String userId, Set<String> roles) {
        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        
        return claimsBuilder
            .issuer(ISSUER)
            .audience(AUDIENCE)
            .subject(userId)
            .claim("preferred_username", userId)
            .claim("given_name", "Test")
            .claim("family_name", "User")
            .claim("email", userId + "@test.com")
            .groups(roles)
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600)) // 1 hour
            .sign();
    }
}