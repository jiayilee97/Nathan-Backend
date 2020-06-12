package stacs.nathan.dto;

import java.util.ArrayList;

public class Select extends ArrayList<Option> {

  private static final long serialVersionUID = 1L;

  public void addOption(String label, String value) {
    super.add(new Option(label, value));
  }

  public void addOption(String label, String value, boolean disabled, boolean checked) {
    super.add(new Option(label, value, disabled, checked));
  }

}
