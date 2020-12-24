package stacs.nathan.core.audit.action.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stacs.nathan.core.audit.action.entity.ActionAuditTrailEntity;
import stacs.nathan.dto.response.AuditLogResponseDto;

import java.util.List;

public interface ActionAuditTrailRepository extends JpaRepository<ActionAuditTrailEntity, Long> {

  List<ActionAuditTrailEntity> findAllByOrderByCreatedDateDesc();

//  @Query("SELECT NEW stacs.nathan.dto.response.AuditLogResponseDto(ar.updatedBy, ar.updatedDate, ar.action, ar.module, ar.tokenCode, ar.amount) FROM ActionAuditTrailEntity ar ")
//  List<AuditLogResponseDto> fetchAllOrderByDate();

}
