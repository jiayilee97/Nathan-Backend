package stacs.nathan.dto.response;

import stacs.nathan.entity.CodeValue;
import java.util.List;

public class FxSpotPriceInitDto {

  List<String> openFXTokens;

  List<CodeValue> currency;

  public List<String> getOpenFXTokens() {
    return openFXTokens;
  }

  public void setOpenFXTokens(List<String> openFXTokens) {
    this.openFXTokens = openFXTokens;
  }

  public List<CodeValue> getCurrency() {
    return currency;
  }

  public void setCurrency(List<CodeValue> currency) {
    this.currency = currency;
  }
}
