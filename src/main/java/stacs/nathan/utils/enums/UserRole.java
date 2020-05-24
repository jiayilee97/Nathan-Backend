package stacs.nathan.utils.enums;

public enum UserRole {
    //code(value)
    CLI("Client"), CRO("Client Relationship Optimization"), MKT("Marketing"), OPS("Operation"), CP("Counter Party");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    public static String resolveValue(String value) {
        for (UserRole role : values()) {
            if (role.getValue().equalsIgnoreCase(value)) {
                return role.name();
            }
        }
        return value;
    }

    public static UserRole resolveCode(String code) {
        for (UserRole role : values()) {
            if (role.getCode().equals(code)) {
                return role;
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
