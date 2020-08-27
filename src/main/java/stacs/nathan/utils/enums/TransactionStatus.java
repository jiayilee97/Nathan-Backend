package stacs.nathan.utils.enums;

public enum  TransactionStatus {

    DEPOSIT("Deposit"), WITHDRAW("Withdraw"), KNOCK_OUT("Knock Out"), CONTRACT_MATURITY("Contract Maturity"), FUND_TRANSFER("Fund Transfer"), REDEMPTION("Redemption"), CLOSED("Closed");

    private String value;

    TransactionStatus(String value) {
        this.value = value;
    }

    public static String resolveValue(String value) {
        for (TransactionStatus status : values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status.name();
            }
        }
        return value;
    }

    public static TransactionStatus resolveCode(String code) {
        for (TransactionStatus status : values()) {
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
