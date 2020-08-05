package stacs.nathan.utils.enums;

public enum FxCurrency {
    EUR_USD("EUR"),
    GBP_USD("GBP"),
    AUD_USD("AUD"),
    USD_JPY("JPY"),
    USD_CHF("CHF");

    private String value;

    FxCurrency(String value) {
        this.value = value;
    }

    public static String resolveValue(String value) {
        for (FxCurrency currency : values()) {
            if (currency.getValue().equalsIgnoreCase(value)) {
                return currency.name();
            }
        }
        return value;
    }

    public static FxCurrency resolveCode(String code) {
        for (FxCurrency currency : values()) {
            if (currency.getCode().equals(code)) {
                return currency;
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
