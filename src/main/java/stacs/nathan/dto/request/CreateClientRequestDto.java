package stacs.nathan.dto.request;

import javax.validation.constraints.NotEmpty;

public class CreateClientRequestDto {

  @NotEmpty(message = "Client ID cannot be empty")
  private String clientId;

  private String displayName;

  private String nationality;

  private String accreditedStatus;

  private int riskToleranceRating;

  public String getClientId() {
    return clientId;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getNationality() {
    return nationality;
  }

  public String getAccreditedStatus() {
    return accreditedStatus;
  }

  public int getRiskToleranceRating() {
    return riskToleranceRating;
  }


}
