package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ExchangeRateEntryRequestDto;

public interface ExchangeRateService {
    void enterExchangeRate(ExchangeRateEntryRequestDto dto) throws ServerErrorException;
}
