package perfume.webservice.common.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perfume.webservice.auth.api.entity.user.User;
import perfume.webservice.auth.oauth.entity.UserPrincipal;

import java.util.Optional;

@Component
public class AuditorProvider implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return null;
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        return Optional.of(principal.getUsername());
    }
}


