package stacs.nathan.Utils.enums;

public enum NAVType {
    SP("SP Token"), FX("FX Token"), ALL("All Clients"), CLI("Client");

    private String value;

    NAVType(String value) {
        this.value = value;
    }

    public static String resolveValue(String value) {
        for (NAVType type : values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type.name();
            }
        }
        return value;
    }

    public static NAVType resolveCode(String code) {
        for (NAVType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
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
