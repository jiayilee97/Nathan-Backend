package stacs.nathan.core.audit.action;

import java.math.BigDecimal;

public interface AudibleActionInterface {

  String getTokenCode();

  BigDecimal getAmount();

  String toJsonString();

}
