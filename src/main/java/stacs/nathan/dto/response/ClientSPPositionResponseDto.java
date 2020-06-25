package stacs.nathan.dto.response;

public class ClientSPPositionResponseDto {

  private String clientId;
  private int openPositions;
  private int closePositions;
  private String investorRisk;

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public int getOpenPositions() {
    return openPositions;
  }

  public void setOpenPositions(int openPositions) {
    this.openPositions = openPositions;
  }

  public int getClosePositions() {
    return closePositions;
  }

  public void setClosePositions(int closePositions) {
    this.closePositions = closePositions;
  }

  public String getInvestorRisk() {
    return investorRisk;
  }

  public void setInvestorRisk(String investorRisk) {
    this.investorRisk = investorRisk;
  }
}
