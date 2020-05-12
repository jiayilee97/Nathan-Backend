package stacs.nathan.Utils.enums;

public enum TokenType {
    SP_TOKEN("SP Token"), FX_TOKEN("FX Token"), BC_TOKEN("Base Cash Token");

    private String value;

    TokenType(String value) {
        this.value = value;
    }

    public static String resolveValue(String value) {
        for (TokenType type : values()) {
            if (type.getValue().equalsIgnoreCase(value)) {
                return type.name();
            }
        }
        return value;
    }

    public static TokenType resolveCode(String code) {
        for (TokenType type : values()) {
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
