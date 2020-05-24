package stacs.nathan.utils.enums;

public enum SPTokenStatus {
    ACTIVE("Active"), KO("Knock Out"), CM("Contract Maturity");

    private String value;

    SPTokenStatus(String value) {
        this.value = value;
    }

    public static String resolveValue(String value) {
        for (SPTokenStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status.name();
            }
        }
        return value;
    }

    public static SPTokenStatus resolveCode(String code) {
        for (SPTokenStatus status : values()) {
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
