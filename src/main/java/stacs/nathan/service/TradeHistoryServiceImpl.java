package stacs.nathan.service;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.TradeHistoryResponseDto;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.TradeHistory;
import stacs.nathan.entity.User;
import stacs.nathan.repository.TradeHistoryRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TradeHistoryServiceImpl implements TradeHistoryService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeHistoryServiceImpl.class);

    @Autowired
    TradeHistoryRepository repository;

    @Autowired
    UserService userService;

    @Autowired
    SPTokenService spTokenService;

    public void save(TradeHistory tradeHistory) {
        repository.save(tradeHistory);
    }

    public List<TradeHistoryResponseDto> fetchAllTradeHistory(String startDate, String endDate) throws ServerErrorException, ParseException {
        LOGGER.debug("Entering fetchAllTradeHistory().");
        try{
            String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            User loggedInUser = userService.fetchByUsername(username);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date start = DateUtils.addDays(formatter.parse(startDate), -1);
            Date end = DateUtils.addDays(formatter.parse(endDate), 1);
            return repository.findAllByDateRange(start, end);
        } catch (Exception e){
            LOGGER.error("Exception in fetchAllTradeHistory().", e);
            throw new ServerErrorException("Exception in fetchAllTradeHistory().", e);
        }
    }

    public List<TradeHistoryResponseDto> fetchByClientAndContract(String clientId, String contract) throws ServerErrorException {
        LOGGER.debug("Entering fetchByClientAndContract().");
        try {
            User user = userService.fetchUserByClientId(clientId);
            SPToken spToken = spTokenService.fetchSPTokenByTokenCode(contract);
            return repository.findAllUserAndSPToken(user, spToken);
        } catch (Exception e){
            LOGGER.error("Exception in fetchByClientAndContract().", e);
            throw new ServerErrorException("Exception in fetchByClientAndContract().", e);
        }

    }
}
