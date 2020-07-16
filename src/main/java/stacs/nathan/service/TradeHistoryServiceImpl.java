package stacs.nathan.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.TradeHistoryResponseDto;
import stacs.nathan.entity.User;
import stacs.nathan.repository.TradeHistoryRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TradeHistoryServiceImpl implements TradeHistoryService{

    @Autowired
    TradeHistoryRepository tradeHistoryRepository;

    @Autowired
    UserService userService;

    public List<TradeHistoryResponseDto> fetchAllTradeHistory(String startDate, String endDate) throws ServerErrorException, ParseException {
        String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User loggedInUser = userService.fetchByUsername(username);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date start = DateUtils.addDays(formatter.parse(startDate), -1);
        Date end = DateUtils.addDays(formatter.parse(endDate), 1);
        return tradeHistoryRepository.findAllByDateRange(start, end);
    }
}
