package perfume.webservice.user.oauth.token;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import perfume.webservice.user.oauth.entity.AuthExceptionType;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
public class AuthToken {

    @Getter
    private final String token;
    private final Key key;

    private static final String AUTHORITIES_KEY = "role";

    AuthToken(String id, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, expiry);
    }

    AuthToken(String id, String role, Date expiry, Key key) {
        this.key = key;
        this.token = createAuthToken(id, role, expiry);
    }

    private String createAuthToken(String id, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    private String createAuthToken(String id, String role, Date expiry) {
        return Jwts.builder()
                .setSubject(id)
                .claim(AUTHORITIES_KEY, role)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiry)
                .compact();
    }

    public boolean validate(HttpServletRequest request) {
        return this.getTokenClaims(request) != null;
    }

    public Claims getTokenClaims(HttpServletRequest request) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            request.setAttribute("exception", AuthExceptionType.SECURITY_EXCEPTION);
            log.info("Invalid JWT signature.");
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", AuthExceptionType.MALFORMED_JWT);
            log.info("Invalid JWT token.");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", AuthExceptionType.EXPIRED_JWT);
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", AuthExceptionType.UNSUPPORTED_JWT);
            log.info("Unsupported JWT token.");
        } catch (SignatureException e){
            request.setAttribute("exception", AuthExceptionType.JWT_SIGNATURE_EXCEPTION);
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", AuthExceptionType.JWT_HANDLER_EXCEPTION);
            log.info("JWT token compact of handler are invalid.");
        }
        return null;
    }

    public Claims getExpiredTokenClaims() {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            return e.getClaims();
        }
        return null;
    }
}
