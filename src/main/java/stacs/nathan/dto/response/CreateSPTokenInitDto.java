package stacs.nathan.dto.response;

import stacs.nathan.dto.Select;
import stacs.nathan.entity.CodeValue;
import java.util.List;

public class CreateSPTokenInitDto {

  List<String> clientIds;

  Select productType;

  List<CodeValue> underlying;

  String issuingAddress; //login ops wallet address

  public List<String> getClientIds() {
    return clientIds;
  }

  public void setClientIds(List<String> clientIds) {
    this.clientIds = clientIds;
  }

  public Select getProductType() {
    return productType;
  }

  public void setProductType(Select productType) {
    this.productType = productType;
  }

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
