package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import stacs.nathan.service.TransactionHistoryService;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionHistoryController {

    @Autowired
    TransactionHistoryService transactionHistoryService;

    @GetMapping("/fetch-all")
    public List<TransactionHistoryResponseDto> fetchTransactionHistory() throws ServerErrorException {
        return transactionHistoryService.fetchAllTransactionHistory();
    }
}
