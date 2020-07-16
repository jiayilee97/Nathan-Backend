package stacs.nathan.dto.response;

import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;

import java.math.BigDecimal;
import java.util.Date;

public class TradeHistoryResponseDto {
    private Date date;
    private String side;
    private String underlying;
    private BigDecimal quantity;
    private String clientId;
    private Long spTokenId;

    public TradeHistoryResponseDto(Date date, String side, String underlying, BigDecimal quantity, User client, SPToken spToken) {
        this.date = date;
        this.side = side;
        this.underlying = underlying;
        this.quantity = quantity;
        this.clientId = client.getClientId();
        this.spTokenId = spToken.getId();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getUnderlying() {
        return underlying;
    }

    public void setUnderlying(String underlying) {
        this.underlying = underlying;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getSpTokenId() {
        return spTokenId;
    }

    public void setSpTokenId(Long spTokenId) {
        this.spTokenId = spTokenId;
    }
}
