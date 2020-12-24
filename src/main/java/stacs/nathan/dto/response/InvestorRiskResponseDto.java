package stacs.nathan.dto.response;

import stacs.nathan.entity.InvestorRisk;
import stacs.nathan.entity.NAV;

import java.util.List;

public class InvestorRiskResponseDto {

  private List<InvestorRisk> investorRisks;

  private NAV totalCurrentNAV;

  public List<InvestorRisk> getInvestorRisks() {
    return investorRisks;
  }

  public void setInvestorRisks(List<InvestorRisk> investorRisks) {
    this.investorRisks = investorRisks;
  }

  public NAV getTotalCurrentNAV() {
    return totalCurrentNAV;
  }

  public void setTotalCurrentNAV(NAV totalCurrentNAV) {
    this.totalCurrentNAV = totalCurrentNAV;
  }
}
