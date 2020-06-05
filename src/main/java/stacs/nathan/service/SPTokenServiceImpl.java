package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import stacs.nathan.repository.SPTokenRepository;
import stacs.nathan.utils.enums.SPTokenStatus;
import java.util.List;

@Service
public class SPTokenServiceImpl implements SPTokenService {
  private static final Logger LOGGER = LoggerFactory.getLogger(SPTokenServiceImpl.class);

  @Autowired
  SPTokenRepository repository;

  public List<SPToken> fetchAllOpenPositions(User user){
    return repository.fetchAllOpenPositions(user, SPTokenStatus.ACTIVE);
  }

  public List<SPToken> fetchAllClosedPositions(User user){
    return repository.fetchAllClosedPositions(user, SPTokenStatus.ACTIVE);
  }

}
