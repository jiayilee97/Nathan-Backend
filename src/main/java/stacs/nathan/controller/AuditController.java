package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.core.audit.action.entity.ActionAuditTrailEntity;
import stacs.nathan.core.audit.action.service.ActionAuditTrailService;
import stacs.nathan.core.exception.ServerErrorException;

import java.util.List;

@RestController
@RequestMapping("/audit-action")
public class AuditController {

    @Autowired
    private ActionAuditTrailService actionAuditTrailService;

    @PreAuthorize("hasAuthority('OPS') or hasAuthority('RISK')")
    @GetMapping("/fetch-all")
    public List<ActionAuditTrailEntity> fetchAllAuditActions() throws ServerErrorException { return actionAuditTrailService.findAll(); }
}
