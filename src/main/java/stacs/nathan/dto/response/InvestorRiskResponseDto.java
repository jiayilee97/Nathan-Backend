package stacs.nathan.dto.response;

import stacs.nathan.entity.InvestorRisk;
import java.math.BigDecimal;
import java.util.List;

public class InvestorRiskResponseDto {

  private List<InvestorRisk> investorRisks;

  private BigDecimal totalCurrentNAV;

  public List<InvestorRisk> getInvestorRisks() {
    return investorRisks;
  }

  public void setInvestorRisks(List<InvestorRisk> investorRisks) {
    this.investorRisks = investorRisks;
  }

  public BigDecimal getTotalCurrentNAV() {
    return totalCurrentNAV;
  }

  public void setTotalCurrentNAV(BigDecimal totalCurrentNAV) {
    this.totalCurrentNAV = totalCurrentNAV;
  }
}
