package stacs.nathan.core.audit.action.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stacs.nathan.core.audit.action.entity.ActionAuditTrailEntity;
import java.util.List;

public interface ActionAuditTrailRepository extends JpaRepository<ActionAuditTrailEntity, Long> {

  List<ActionAuditTrailEntity> findAllByOrderByCreatedDateDesc();

}
