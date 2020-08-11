package stacs.nathan.core.audit.action;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AudibleActionImplementation<T> implements AudibleActionInterface {

  private static final Logger LOGGER = LoggerFactory.getLogger(AudibleActionImplementation.class);

  protected T audited;

  public AudibleActionImplementation(T audited) {
    setAudited(audited);
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

}
