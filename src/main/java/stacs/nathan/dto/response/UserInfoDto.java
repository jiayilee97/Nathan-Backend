package stacs.nathan.dto.response;

import java.util.List;
import java.util.StringJoiner;

public class UserInfoDto {

  private String username;

  private String name;

  private String pageNames;

  private String roleName;

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

  public void setPageNames(List<String> rolePages) {
    StringJoiner pageNames = new StringJoiner(",");
    for (String pageName : rolePages) {
      pageNames.add(pageName);
    }
    this.pageNames = pageNames.toString();
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(List<String> roles) {
    StringJoiner strRole = new StringJoiner(",");
    for (String role : roles) {
      strRole.add(role);
    }
    this.roleName = strRole.toString();
  }

}
