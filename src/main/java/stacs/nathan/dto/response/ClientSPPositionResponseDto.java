package stacs.nathan.dto.response;

public class ClientSPPositionResponseDto {

  private String clientId;
  private int openPositions;
  private int closePositions;
  private String investorRisk;

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public void setOpenPositions(int openPositions) {
    this.openPositions = openPositions;
  }

  public void setClosePositions(int closePositions) {
    this.closePositions = closePositions;
  }

  public void setInvestorRisk(String investorRisk) {
    this.investorRisk = investorRisk;
  }

}
