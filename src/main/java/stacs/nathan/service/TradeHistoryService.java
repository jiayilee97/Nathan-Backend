package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TradeHistoryResponseDto;
import java.text.ParseException;
import java.util.List;

public interface TradeHistoryService {

    List<TradeHistoryResponseDto> fetchAllTradeHistory(String startDate, String endDate) throws ServerErrorException, ParseException;

}
