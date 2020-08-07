package stacs.nathan.core.audit.action.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import stacs.nathan.core.audit.action.annotation.AudibleActionTrail;
import stacs.nathan.core.audit.action.entity.ActionAuditTrailEntity;
import stacs.nathan.core.audit.action.AudibleActionInterface;
import stacs.nathan.core.audit.action.service.ActionAuditTrailService;
import stacs.nathan.core.exception.ServerErrorException;

@Aspect
@Component
public class AudibleActionTrailAspect {

  @Autowired
  protected ActionAuditTrailService actionAuditTrailService;

  @AfterReturning(pointcut = "@annotation(trail)", returning = "action")
  public void afterSingleAction(AudibleActionTrail trail, AudibleActionInterface action) throws ServerErrorException {
    ActionAuditTrailEntity entity = new ActionAuditTrailEntity();
    entity.setModule(trail.module());
    entity.setAction(trail.action());
    entity.setSerializedJson(action.toJsonString());
    actionAuditTrailService.create(entity);
  }
}
