package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TradeHistoryResponseDto;
import stacs.nathan.entity.TradeHistory;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface TradeHistoryService {

    void save(TradeHistory tradeHistory);

    List<TradeHistoryResponseDto> fetchAllTradeHistory(String startDate, String endDate) throws ServerErrorException, ParseException;

    List<TradeHistoryResponseDto> fetchByClientAndContract(String clientId, String contract) throws ServerErrorException;

}
