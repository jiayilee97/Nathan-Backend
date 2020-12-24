package stacs.nathan.entity;

import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Audited
@Table(name = "role_pages")
public class RolePages extends BaseEntity {
  private static final long serialVersionUID = 1L;

  @Column(name = "role_name", length = 20)
  private String roleName;

  @Column(name = "page_name", length = 200)
  private String pageName;

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public String getPageName() {
    return pageName;
  }

  public void setPageName(String pageName) {
    this.pageName = pageName;
  }

}
