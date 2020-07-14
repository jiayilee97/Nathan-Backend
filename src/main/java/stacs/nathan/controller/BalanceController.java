package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.dto.response.BalanceResponseDto;
import stacs.nathan.service.BalanceService;
import java.util.List;

@RestController
@RequestMapping("/balance")
public class BalanceController {

  @Autowired
  BalanceService balanceService;

  @GetMapping("/fetch/{clientId}")
  public List<BalanceResponseDto> fetchBalanceByClient(@PathVariable String clientId){
    return balanceService.fetchBalanceByClient(clientId);
  }

}
