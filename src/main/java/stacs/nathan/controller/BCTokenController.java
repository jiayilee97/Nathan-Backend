package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.service.BCTokenService;

@RestController
@RequestMapping("/bctoken")
//@PreAuthorize("hasAuthority('OPS')")
public class BCTokenController {

  @Autowired
  private BCTokenService bcTokenService;

  @PostMapping("/create")
  public void createBCToken(@RequestBody BCTokenRequestDto token) throws ServerErrorException {
    bcTokenService.createBCToken(token);
  }


}
