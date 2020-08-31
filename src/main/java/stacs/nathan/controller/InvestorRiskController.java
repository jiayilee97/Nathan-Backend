package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.InvestorRiskResponseDto;
import stacs.nathan.entity.NAV;
import stacs.nathan.service.InvestorRiskService;
import stacs.nathan.service.NAVService;
import java.util.List;

@RestController
@RequestMapping("/risk")
public class InvestorRiskController {

  @Autowired
  InvestorRiskService investorRiskService;

  @Autowired
  NAVService navService;

  @PreAuthorize("hasAuthority('OPS') or hasAuthority('CRO') or hasAuthority('RISK')")
  @GetMapping("/fetch-all")
  public InvestorRiskResponseDto fetchAll() throws ServerErrorException {
    return investorRiskService.fetchAllInvestorRisk();
  }

  @PreAuthorize("hasAuthority('OPS') or hasAuthority('CRO')  or hasAuthority('RISK')")
  @GetMapping("/nav/fetch-all")
  public List<NAV> fetchNAVHistory(@RequestParam String startDate, @RequestParam String endDate) throws ServerErrorException {
    return navService.fetchAllNAV(startDate, endDate);
  }
}
