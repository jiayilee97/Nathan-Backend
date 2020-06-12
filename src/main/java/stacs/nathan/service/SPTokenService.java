package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.SPTokenRequestDto;
import stacs.nathan.dto.response.CreateSPTokenInitDto;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.User;
import java.util.List;

public interface SPTokenService {

  void createSPToken(SPTokenRequestDto token) throws ServerErrorException;

  List<SPTokenResponseDto> fetchAllOpenPositions(User user);

  List<SPTokenResponseDto> fetchAllClosedPositions(User user);

  CreateSPTokenInitDto fetchInitForm();

}
