package stacs.nathan.dto.response;

import java.util.Date;

public class ClientOpenPositionResponseDto {
  private String fxTokenCode;
  private String spTokenCode;
  private Date contractInceptionDate;
  private Date maturityDate;

  public ClientOpenPositionResponseDto(String fxTokenCode, String spTokenCode, Date contractInceptionDate, Date maturityDate){
    setFxTokenCode(fxTokenCode);
    setSpTokenCode(spTokenCode);
    setContractInceptionDate(contractInceptionDate);
    setMaturityDate(maturityDate);
  }

  public String getFxTokenCode() {
    return fxTokenCode;
  }

  public void setFxTokenCode(String fxTokenCode) {
    this.fxTokenCode = fxTokenCode;
  }

  public String getSpTokenCode() {
    return spTokenCode;
  }

  public void setSpTokenCode(String spTokenCode) {
    this.spTokenCode = spTokenCode;
  }

  public Date getContractInceptionDate() {
    return contractInceptionDate;
  }

  public void setContractInceptionDate(Date contractInceptionDate) {
    this.contractInceptionDate = contractInceptionDate;
  }

  public Date getMaturityDate() {
    return maturityDate;
  }

  public void setMaturityDate(Date maturityDate) {
    this.maturityDate = maturityDate;
  }
}
