package stacs.nathan.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import stacs.nathan.entity.TransactionHistory;
import stacs.nathan.repository.TransactionRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Autowired
    TransactionRepository repository;

    @Autowired
    UserService userService;

    public void save(TransactionHistory transactionHistory){
        repository.save(transactionHistory);
    }

    @Override
    public List<TransactionHistoryResponseDto> fetchAllTransactionHistory(String startDate, String endDate) throws ServerErrorException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date start = DateUtils.addDays(formatter.parse(startDate), -1);
        Date end = DateUtils.addDays(formatter.parse(endDate), 1);
        return repository.findAllByCreatedBy(start, end);
    }
}
