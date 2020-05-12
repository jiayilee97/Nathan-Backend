package stacs.nathan.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import stacs.nathan.dto.DecodedJwtDTO;

@Component
@RequestScope
public class RequestContext {
    private String username;
    private String role;

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public void store(DecodedJwtDTO decodedJwtDTO) {
        if (decodedJwtDTO != null) {
            this.username = decodedJwtDTO.getUsername();
            this.role = decodedJwtDTO.getRole();
        }
    }
}
