package stacs.nathan.core.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import stacs.nathan.dto.request.LoggedInUser;
import java.util.Optional;

public class AuditAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.of("SCHEDULAR");
    }
    return Optional.of(((LoggedInUser) authentication.getPrincipal()).getUsername());
  }
}
