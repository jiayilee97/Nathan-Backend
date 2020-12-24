package stacs.nathan.dto.response;

import stacs.nathan.dto.Select;
import stacs.nathan.entity.CodeValue;
import java.util.List;

public class CreateSPTokenInitDto {

  List<String> clientIds;

  Select productType;

  List<CodeValue> underlying;

  Select fixingType;

  Select tenorType;

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

  public Select getFixingType() {
    return fixingType;
  }

  public void setFixingType(Select fixingType) {
    this.fixingType = fixingType;
  }

  public Select getTenorType() {
    return tenorType;
  }

  public void setTenorType(Select tenorType) {
    this.tenorType = tenorType;
  }

  public String getIssuingAddress() {
    return issuingAddress;
  }

  public void setIssuingAddress(String issuingAddress) {
    this.issuingAddress = issuingAddress;
  }
}
