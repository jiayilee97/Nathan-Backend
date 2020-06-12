package stacs.nathan.dto;

public class Option {
  private String label;
  private String value;
  private boolean disabled = false;
  private boolean checked = false;

  public Option(String label, String value) {
    this.label = label;
    this.value = value;
  }

  public Option(String label, String value, boolean disabled, boolean checked) {
    this.label = label;
    this.value = value;
    this.disabled = disabled;
    this.checked = checked;
  }

  public Option() {
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public boolean isDisabled() {
    return disabled;
  }

  public void setDisabled(boolean disabled) {
    this.disabled = disabled;
  }

  public boolean isChecked() {
    return checked;
  }

  public void setChecked(boolean checked) {
    this.checked = checked;
  }
}
