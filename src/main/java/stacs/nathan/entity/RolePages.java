package stacs.nathan.entity;

import javax.persistence.*;

@Entity
@Table(name = "role_pages")
public class RolePages extends BaseEntity {
  private static final long serialVersionUID = 1L;

  @Column(length = 10)
  private String role;

  @Column(name = "page_name", length = 200)
  private String pageName;

  @Column(name = "is_enabled", nullable = false)
  private boolean isVisible = true;

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

  public boolean isVisible() {
    return isVisible;
  }

  public void setVisible(boolean visible) {
    isVisible = visible;
  }
}
