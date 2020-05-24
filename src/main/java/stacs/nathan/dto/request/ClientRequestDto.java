package stacs.nathan.dto.request;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class ClientRequestDto {

  @NotEmpty(message = "Username cannot be empty")
  private String username;

  private String displayName;

  private String email;

  private List<String> roles;

  public String getUsername() {
    return username;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getEmail() {
    return email;
  }

  public List<String> getRoles() {
    return roles;
  }
}
