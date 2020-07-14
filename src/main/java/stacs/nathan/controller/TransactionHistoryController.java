package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import stacs.nathan.service.TransactionHistoryService;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionHistoryController {

    @Autowired
    TransactionHistoryService transactionHistoryService;

    @GetMapping("/fetch-all")
    public List<TransactionHistoryResponseDto> fetchTransactionHistory(@RequestParam String startDate, @RequestParam String endDate) throws ServerErrorException, ParseException {
        return transactionHistoryService.fetchAllTransactionHistory(startDate, endDate);
    }
}
