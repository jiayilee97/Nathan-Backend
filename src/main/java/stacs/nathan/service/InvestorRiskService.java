package stacs.nathan.service;

import stacs.nathan.core.audit.action.AudibleActionImplementation;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.InvestorRiskResponseDto;
import stacs.nathan.entity.InvestorRisk;
import java.util.List;

public interface InvestorRiskService {

  InvestorRiskResponseDto fetchAllInvestorRisk() throws ServerErrorException;

  void save(InvestorRisk investorRisks) throws ServerErrorException;

  AudibleActionImplementation<List<InvestorRisk>> calculateInvestorRisk() throws ServerErrorException;

}
