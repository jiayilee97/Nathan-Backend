package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import stacs.nathan.entity.User;
import stacs.nathan.repository.TransactionRepository;

import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserService userService;

    @Override
    public List<TransactionHistoryResponseDto> fetchAllTransactionHistory() throws ServerErrorException {
        String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User loggedInUser = userService.fetchByUsername(username);
        return transactionRepository.findAllByCreatedBy();
    }
}
