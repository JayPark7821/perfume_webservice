package perfume.webservice.auth.oauth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionType {

    SECURITY_EXCEPTION("SECURITY_EXCEPTION", "Invalid JWT signature."),
    JWT_HANDLER_EXCEPTION("JWT_HANDLER_EXCEPTION", "JWT token compact of handler are invalid."),
    MALFORMED_JWT("MALFORMED_JWT",  "Invalid JWT token."),
    EXPIRED_JWT("EXPIRED_JWT", "Expired JWT token."),
    UNSUPPORTED_JWT("UNSUPPORTED_JWT", "Unsupported JWT token."),
    JWT_SIGNATURE_EXCEPTION("JWT_SIGNATURE_EXCEPTION","JWT signature does not match locally computed signature.");


    private final String code;
    private final String displayName;
}
