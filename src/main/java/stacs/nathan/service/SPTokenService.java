package stacs.nathan.service;

import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import java.util.List;

public interface SPTokenService {

  List<SPToken> fetchAllOpenPositions(User user);

  List<SPToken> fetchAllClosedPositions(User user);

}
