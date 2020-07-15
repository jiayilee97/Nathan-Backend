package stacs.nathan.dto.response;

import java.math.BigDecimal;
import java.util.Date;

public class TradeHistoryResponseDto {
    private Date date;
    private String side;
    private String underlying;
    private BigDecimal quantity;

    public TradeHistoryResponseDto(Date date, String side, String underlying, BigDecimal quantity) {
        this.date = date;
        this.side = side;
        this.underlying = underlying;
        this.quantity = quantity;
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
}
