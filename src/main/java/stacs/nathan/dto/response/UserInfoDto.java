package stacs.nathan.dto.response;

import stacs.nathan.entity.RolePages;
import java.util.List;
import java.util.StringJoiner;

public class UserInfoDto {

  private String username;

  private String name;

  private String pageNames;

  private String role;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPageNames() {
    return pageNames;
  }

  public void setPageNames(List<RolePages> rolePages) {
    StringJoiner pageNames = new StringJoiner(",");
    for (RolePages rolePage : rolePages) {
      pageNames.add(rolePage.getPageName());
    }
    this.pageNames = pageNames.toString();
  }

  public String getRole() {
    return role;
  }

  public void setRole(List<String> roles) {
    StringJoiner strRole = new StringJoiner(",");
    for (String role : roles) {
      strRole.add(role);
    }
    this.role = strRole.toString();
  }

}
