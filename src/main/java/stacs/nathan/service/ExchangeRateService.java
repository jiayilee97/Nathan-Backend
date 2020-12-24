package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ExchangeRateEntryRequestDto;
import stacs.nathan.entity.ExchangeRate;
import java.util.List;

public interface ExchangeRateService {

    void enterExchangeRate(List<ExchangeRateEntryRequestDto> dto) throws ServerErrorException;

    List<ExchangeRate> fetchExchangeRate(String startDate, String endDate) throws ServerErrorException;

    List<ExchangeRate> fetchLatestExchangeRate() throws ServerErrorException;

}
