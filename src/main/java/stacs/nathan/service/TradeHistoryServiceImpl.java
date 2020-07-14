package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.repository.TradeHistoryRepository;

@Service
public class TradeHistoryServiceImpl implements TradeHistoryService{

    @Autowired
    TradeHistoryRepository tradeHistoryRepository;

    @Autowired
    UserService userService;
}
