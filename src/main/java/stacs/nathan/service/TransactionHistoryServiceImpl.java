package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import stacs.nathan.entity.TransactionHistory;
import stacs.nathan.repository.TransactionRepository;
import stacs.nathan.utils.CommonUtils;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHistoryServiceImpl.class);

    @Autowired
    TransactionRepository repository;

    public void save(TransactionHistory transactionHistory){
        repository.save(transactionHistory);
    }

    @Override
    public List<TransactionHistoryResponseDto> fetchAllTransactionHistory(String startDate, String endDate) throws ServerErrorException, ParseException {
        LOGGER.debug("Entering fetchAllTransactionHistory().");
        try {
            Date start = CommonUtils.formatDate(startDate, -1);
            Date end = CommonUtils.formatDate(endDate, 1);
            return repository.findAllByCreatedBy(start, end);
        } catch (Exception e){
            LOGGER.error("Exception in fetchAllTransactionHistory().", e);
            throw new ServerErrorException("Exception in fetchAllTransactionHistory().", e);
        }

    }
}
