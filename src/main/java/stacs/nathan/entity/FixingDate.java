package stacs.nathan.entity;

import org.hibernate.envers.Audited;
import javax.persistence.*;
import java.util.Date;

@Entity
@Audited
@Table(name = "fixing_dates")
public class FixingDate extends BaseEntity {
  private static final long serialVersionUID = 1L;

  @ManyToOne
  @JoinColumn(name = "sp_token_id")
  private SPToken spToken;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fixing_date")
  private Date fixingDate;

  public SPToken getSpToken() {
    return spToken;
  }

  public void setSpToken(SPToken spToken) {
    this.spToken = spToken;
  }

  public Date getFixingDate() {
    return fixingDate;
  }

  public void setFixingDate(Date fixingDate) {
    this.fixingDate = fixingDate;
  }
}
