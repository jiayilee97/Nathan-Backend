package stacs.nathan.dto.response;

import stacs.nathan.utils.enums.TokenType;

import java.math.BigDecimal;
import java.util.Date;

public class AuditLogResponseDto {
    private String user;
    private Date timestamp;
    private String action;
    private TokenType tokenType;
    private String tickerCode;
    private BigDecimal amount;

    public AuditLogResponseDto(String user, Date timestamp, String action, TokenType tokenType, String tickerCode, BigDecimal amount) {
        this.user = user;
        this.timestamp = timestamp;
        this.action = action;
        this.tokenType = tokenType;
        this.tickerCode = tickerCode;
        this.amount = amount;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getTickerCode() {
        return tickerCode;
    }

    public void setTickerCode(String tickerCode) {
        this.tickerCode = tickerCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
