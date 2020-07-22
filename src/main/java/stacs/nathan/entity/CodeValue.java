package stacs.nathan.entity;

import org.hibernate.envers.Audited;
import stacs.nathan.utils.enums.CodeType;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Audited
@Table(name = "code_value")
public class CodeValue {

  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "id", length = 20)
  private long id;

  @Column(length = 100, updatable = false)
  protected String value;

  @Column(length = 100)
  protected String label;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(length = 50)
  protected CodeType type;

  public CodeValue(){

  }

  public CodeValue(String value, String label, CodeType type) {
    this.value = value;
    this.label = label;
    this.type = type;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public CodeType getType() {
    return type;
  }

  public void setType(CodeType type) {
    this.type = type;
  }

}
