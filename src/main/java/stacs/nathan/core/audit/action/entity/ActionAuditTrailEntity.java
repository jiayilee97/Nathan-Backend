package stacs.nathan.core.audit.action.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import stacs.nathan.entity.BaseEntity;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "action_audit_trail")
public class ActionAuditTrailEntity extends BaseEntity {

  @Column(length = 50)
  protected String module;

  @Column(length = 255)
  protected String action;

  @Column(name = "token_code", length = 50)
  private String tokenCode;

  @Column(precision = 15, scale = 2)
  @ColumnDefault("0.0")
  private BigDecimal amount = BigDecimal.ZERO;

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

  public String getTokenCode() {
    return tokenCode;
  }

  public void setTokenCode(String tokenCode) {
    this.tokenCode = tokenCode;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getSerializedJson() {
    return serializedJson;
  }

  public void setSerializedJson(String serializedJson) {
    this.serializedJson = serializedJson;
  }
}
