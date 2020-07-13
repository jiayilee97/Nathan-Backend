package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.entity.Balance;
import stacs.nathan.service.BalanceService;
import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {

  @Autowired
  BalanceService balanceService;

  public List<Balance> fetchBalanceByClient(String clientId){
    return balanceService.fetchBalanceByClient(clientId);
  }

}
