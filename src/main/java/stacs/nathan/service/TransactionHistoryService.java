package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import stacs.nathan.entity.TransactionHistory;
import java.util.List;

public interface TransactionHistoryService {

    void save(TransactionHistory transactionHistory);

    List<TransactionHistoryResponseDto> fetchAllTransactionHistory(String startDate, String endDate) throws ServerErrorException;
}
