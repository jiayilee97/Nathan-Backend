package stacs.nathan.service;

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
import stacs.nathan.utils.CommonUtils;
import java.util.Date;
import java.util.List;

@Service
public class TradeHistoryServiceImpl implements TradeHistoryService{
    private static final Logger LOGGER = LoggerFactory.getLogger(TradeHistoryServiceImpl.class);

    @Autowired
    private TradeHistoryRepository repository;

    @Autowired
    private UserService userService;

    @Autowired
    private SPTokenService spTokenService;

    public void save(TradeHistory tradeHistory) {
        repository.save(tradeHistory);
    }

    public List<TradeHistoryResponseDto> fetchAllTradeHistory(String startDate, String endDate) throws ServerErrorException {
        LOGGER.debug("Entering fetchAllTradeHistory().");
        try{
            String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
            Date start = CommonUtils.formatDate(startDate, -1);
            Date end = CommonUtils.formatDate(endDate, 1);
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
