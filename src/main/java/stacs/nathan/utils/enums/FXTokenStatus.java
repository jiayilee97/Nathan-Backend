package stacs.nathan.utils.enums;

public enum FXTokenStatus {
    OPEN("Open"),
    CLOSED("Closed"),
    UNCONFIRMED_IN_CHAIN("Unconfirmed in chain"),
    CHAIN_UNAVAILABLE("Chain unavailable");

    private String value;

    FXTokenStatus(String value) {
        this.value = value;
    }

    public static String resolveValue(String value) {
        for (FXTokenStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status.name();
            }
        }
        return value;
    }

    public static FXTokenStatus resolveCode(String code) {
        for (FXTokenStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return name();
    }
}
