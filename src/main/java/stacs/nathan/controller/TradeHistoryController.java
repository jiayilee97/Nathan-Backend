package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TradeHistoryResponseDto;
import stacs.nathan.service.TradeHistoryService;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/trade")
public class TradeHistoryController {

    @Autowired
    TradeHistoryService tradeHistoryService;

    @GetMapping("/fetch-all")
    public List<TradeHistoryResponseDto> fetchTradeHistory(@RequestParam String startDate, @RequestParam String endDate) throws ServerErrorException, ParseException {
        return tradeHistoryService.fetchAllTradeHistory(startDate, endDate);
    }

    @GetMapping("/fetch/{clientId}/{contract}")
    public List<TradeHistoryResponseDto> fetchByClientAndContract(@PathVariable("clientId") String clientId, @PathVariable("contract") String contract) throws ServerErrorException {
        return tradeHistoryService.fetchByClientAndContract(clientId, contract);
    }
}
