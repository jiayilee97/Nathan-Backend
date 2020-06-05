package stacs.nathan.dto.response;

import stacs.nathan.utils.enums.AccreditedStatus;

public class ClientResponseDto {

  private String clientId;
  private String displayName;
  private String nationality;
  private AccreditedStatus accreditedStatus;
  private int riskToleranceRating;
  private String walletAddress;

  public ClientResponseDto(String clientId, String displayName, String nationality, AccreditedStatus accreditedStatus, int riskToleranceRating, String walletAddress){
    setClientId(clientId);
    setDisplayName(displayName);
    setNationality(nationality);
    setAccreditedStatus(accreditedStatus);
    setRiskToleranceRating(riskToleranceRating);
    setWalletAddress(walletAddress);
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  public AccreditedStatus getAccreditedStatus() {
    return accreditedStatus;
  }

  public void setAccreditedStatus(AccreditedStatus accreditedStatus) {
    this.accreditedStatus = accreditedStatus;
  }

  public int getRiskToleranceRating() {
    return riskToleranceRating;
  }

  public void setRiskToleranceRating(int riskToleranceRating) {
    this.riskToleranceRating = riskToleranceRating;
  }

  public String getWalletAddress() {
    return walletAddress;
  }

  public void setWalletAddress(String walletAddress) {
    this.walletAddress = walletAddress;
  }
}
