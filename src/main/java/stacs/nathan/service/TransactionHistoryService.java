package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;

import java.util.List;

public interface TransactionHistoryService {

    List<TransactionHistoryResponseDto> fetchAllTransactionHistory() throws ServerErrorException;
}
