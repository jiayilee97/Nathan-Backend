package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Audited
@Table(name = "investor_risk")
public class InvestorRisk extends BaseEntity {

  @Column(name = "client_id", length = 50)
  private String clientId;

  @Column(name = "nav_sp_token", precision = 15, scale = 2)
  @ColumnDefault("0.0")
  private BigDecimal navSPToken = BigDecimal.ZERO;

  @Column(name = "invested_amount", precision = 15, scale = 2)
  @ColumnDefault("0.0")
  private BigDecimal investedAmount = BigDecimal.ZERO;

  @Column(name = "bc_token_balance", precision = 15, scale = 2)
  @ColumnDefault("0.0")
  private BigDecimal bcTokenBalance = BigDecimal.ZERO;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public BigDecimal getNavSPToken() {
    return navSPToken;
  }

  public void setNavSPToken(BigDecimal navSPToken) {
    this.navSPToken = navSPToken;
  }

  public BigDecimal getInvestedAmount() {
    return investedAmount;
  }

  public void setInvestedAmount(BigDecimal investedAmount) {
    this.investedAmount = investedAmount;
  }

  public BigDecimal getBcTokenBalance() {
    return bcTokenBalance;
  }

  public void setBcTokenBalance(BigDecimal bcTokenBalance) {
    this.bcTokenBalance = bcTokenBalance;
  }
}
