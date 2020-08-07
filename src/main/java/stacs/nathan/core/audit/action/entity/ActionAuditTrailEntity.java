package stacs.nathan.core.audit.action.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import stacs.nathan.entity.BaseEntity;
import javax.persistence.*;

@Entity
@Table(name = "action_audit_trail")
public class ActionAuditTrailEntity extends BaseEntity {

  @Column(length = 50)
  protected String module;

  @Column(length = 255)
  protected String action;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @JsonIgnore
  protected String serializedJson;

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getSerializedJson() {
    return serializedJson;
  }

  public void setSerializedJson(String serializedJson) {
    this.serializedJson = serializedJson;
  }
}
