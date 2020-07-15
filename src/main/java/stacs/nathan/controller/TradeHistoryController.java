package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
