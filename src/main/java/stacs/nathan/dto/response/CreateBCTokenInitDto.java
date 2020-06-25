package stacs.nathan.dto.response;

import stacs.nathan.entity.CodeValue;
import java.util.List;

public class CreateBCTokenInitDto {

  List<CodeValue> underlying;

  String issuingAddress; //login ops wallet address

  public List<CodeValue> getUnderlying() {
    return underlying;
  }

  public void setUnderlying(List<CodeValue> underlying) {
    this.underlying = underlying;
  }

  public String getIssuingAddress() {
    return issuingAddress;
  }

  public void setIssuingAddress(String issuingAddress) {
    this.issuingAddress = issuingAddress;
  }
}
