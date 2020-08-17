package stacs.nathan.core.audit.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

public class AudibleActionImplementation<T> implements AudibleActionInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(AudibleActionImplementation.class);

  protected T audited;

  private String tokenCode;

  private BigDecimal amount;

  public AudibleActionImplementation(T audited) {
    setAudited(audited);
  }

  public AudibleActionImplementation(T audited, String tokenCode, BigDecimal amount) {
    setAudited(audited);
    setTokenCode(tokenCode);
    setAmount(amount);
  }

  public String toJsonString() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new Hibernate5Module());
    try {
      return mapper.writeValueAsString(audited);
    } catch (Exception e) {
      LOGGER.error("Error when converting audited to Json null will be returned instead", e);
    }
    return null;
  }

  public T getAudited() {
    return audited;
  }

  public void setAudited(T newAudited) {
    audited = newAudited;
  }

  @Override
  public String getTokenCode() {
    return tokenCode;
  }

  public void setTokenCode(String tokenCode) {
    this.tokenCode = tokenCode;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
