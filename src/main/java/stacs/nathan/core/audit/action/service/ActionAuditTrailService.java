package stacs.nathan.core.audit.action.service;

import stacs.nathan.core.audit.action.entity.ActionAuditTrailEntity;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.AuditLogResponseDto;

import java.util.List;

public interface ActionAuditTrailService {

  void create(ActionAuditTrailEntity auditTrailEntity) throws ServerErrorException;

  List<ActionAuditTrailEntity> findAll() throws ServerErrorException;

}
