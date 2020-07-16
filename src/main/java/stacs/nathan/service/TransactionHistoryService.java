package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import java.text.ParseException;
import java.util.List;

public interface TransactionHistoryService {

    List<TransactionHistoryResponseDto> fetchAllTransactionHistory(String startDate, String endDate) throws ServerErrorException, ParseException;
}
