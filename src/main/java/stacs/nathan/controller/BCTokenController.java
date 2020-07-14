package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.TransferBCTokenRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.dto.response.CreateBCTokenInitDto;
import stacs.nathan.service.BCTokenService;
import java.util.List;

@RestController
@RequestMapping("/bctoken")
public class BCTokenController {

  @Autowired
  private BCTokenService bcTokenService;

  @PreAuthorize("hasAuthority('OPS')")
  @GetMapping("/init")
  public CreateBCTokenInitDto initForm(){
    return bcTokenService.fetchInitForm();
  }

  @PreAuthorize("hasAuthority('OPS')")
  @PostMapping("/create")
  public void createBCToken(@RequestBody BCTokenRequestDto token) throws ServerErrorException, BadRequestException {
    bcTokenService.createBCToken(token);
  }

  @GetMapping("/fetch-all/{issuerAddress}")
  public List<BCTokenResponseDto> fetchAllByIssuerAddress(@PathVariable String issuerAddress) throws ServerErrorException {
    return bcTokenService.fetchAllByIssuerAddress(issuerAddress);
  }

  @GetMapping("/fetch/{tokenCode}")
  public BCTokenResponseDto fetchTokenByTokenCode(@PathVariable String tokenCode) throws ServerErrorException {
    return bcTokenService.fetchTokenByTokenCode(tokenCode);
  }

  @GetMapping("/executeUnavailableChain")
  public void executeUnavailableChain() {
    bcTokenService.executeUnavailableChain();
  }

  @PostMapping("/transfer")
  public void transferBCToken(@RequestBody TransferBCTokenRequestDto dto) throws ServerErrorException {
    bcTokenService.transferBCToken(dto);
  }

}
