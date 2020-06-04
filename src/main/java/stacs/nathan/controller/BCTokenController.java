package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.service.BCTokenService;
import java.util.List;

@RestController
@RequestMapping("/bctoken")
//@PreAuthorize("hasAuthority('OPS')")
public class BCTokenController {

  @Autowired
  private BCTokenService bcTokenService;

  @GetMapping("/create")
  public void createBCToken(@RequestBody BCTokenRequestDto token) throws ServerErrorException {
    bcTokenService.createBCToken(token);
  }

  @GetMapping("/fetch-all")
  public List<BCTokenResponseDto> fetchAllBCTokens() throws ServerErrorException {
    return bcTokenService.fetchAllBCTokens();
  }

}
