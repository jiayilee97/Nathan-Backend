package stacs.nathan.dto.response;

import stacs.nathan.dto.Select;
import stacs.nathan.entity.CodeValue;
import java.util.List;

public class CreateClientInitDto {

  Select accreditedStatus;

  List<CodeValue> nationalities;

  public Select getAccreditedStatus() {
    return accreditedStatus;
  }

  public void setAccreditedStatus(Select accreditedStatus) {
    this.accreditedStatus = accreditedStatus;
  }

  public List<CodeValue> getNationalities() {
    return nationalities;
  }

  public void setNationalities(List<CodeValue> nationalities) {
    this.nationalities = nationalities;
  }
}
