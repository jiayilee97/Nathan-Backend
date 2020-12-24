package stacs.nathan.core.audit.action.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.audit.action.entity.ActionAuditTrailEntity;
import stacs.nathan.core.audit.action.repository.ActionAuditTrailRepository;
import stacs.nathan.core.exception.ServerErrorException;
import java.util.List;

@Service
public class ActionAuditTrailServiceImpl implements ActionAuditTrailService {
  private static final Logger LOGGER = LoggerFactory.getLogger(ActionAuditTrailServiceImpl.class);

  @Autowired
  ActionAuditTrailRepository repository;

  public void create(ActionAuditTrailEntity auditTrailEntity) throws ServerErrorException {
    LOGGER.debug("Entering create().");
    try{
      repository.save(auditTrailEntity);
    }catch (Exception e){
      LOGGER.error("Exception in create().", e);
      throw new ServerErrorException("Exception in create().", e);
    }
  }

  public List<ActionAuditTrailEntity> findAll() throws ServerErrorException {
    LOGGER.debug("Entering findAll().");
    try{
      return repository.findAllByOrderByCreatedDateDesc();
    }catch (Exception e){
      LOGGER.error("Exception in findAll().", e);
      throw new ServerErrorException("Exception in findAll().", e);
    }
  }

}
