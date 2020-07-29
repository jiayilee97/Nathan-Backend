package stacs.nathan.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.envers.Audited;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Audited
@Table(name = "exchange_rate")
public class ExchangeRate extends BaseEntity {

  // USD/JPY, EUR/USD
  @Column(name = "currency_pair", length = 10)
  private String currencyPair;

  // JPY, EUR
  @Column(name = "currency", length = 10)
  private String currency;

  @Column(name = "price", precision = 10, scale = 4)
  @ColumnDefault("0.0")
  private BigDecimal price = BigDecimal.ZERO;

  public String getCurrencyPair() {
    return currencyPair;
  }

  public void setCurrencyPair(String currencyPair) {
    this.currencyPair = currencyPair;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
