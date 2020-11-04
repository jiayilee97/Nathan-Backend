package stacs.nathan.dto.response;

import stacs.nathan.entity.CodeValue;
import java.util.List;

public class CreateFXTokenInitDto {

  List<String> availableSPToken;

  String appWalletAddress;

  List<CodeValue> currency;

  public List<String> getAvailableSPToken() {
    return availableSPToken;
  }

  public void setAvailableSPToken(List<String> availableSPToken) {
    this.availableSPToken = availableSPToken;
  }

  public String getAppWalletAddress() {
    return appWalletAddress;
  }

  public void setAppWalletAddress(String appWalletAddress) {
    this.appWalletAddress = appWalletAddress;
  }

  public List<CodeValue> getCurrency() {
    return currency;
  }

  public void setCurrency(List<CodeValue> currency) {
    this.currency = currency;
  }
}
