package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.BCTokenRequestDto;
import stacs.nathan.dto.request.TransferBCTokenRequestDto;
import stacs.nathan.dto.request.TransferBCTokenToOpsRequestDto;
import stacs.nathan.dto.response.BCTokenResponseDto;
import stacs.nathan.dto.response.CreateBCTokenInitDto;
import stacs.nathan.service.BCTokenService;
import java.util.List;

@RestController
@RequestMapping("/bctoken")
public class BCTokenController {

  @Autowired
  private BCTokenService bcTokenService;

  @PreAuthorize("hasAnyAuthority('OPS')")
  @GetMapping("/init")
  public CreateBCTokenInitDto initForm(){
    return bcTokenService.fetchInitForm();
  }

  @PreAuthorize("hasAnyAuthority('OPS')")
  @PostMapping("/create")
  public void createBCToken(@RequestBody BCTokenRequestDto token) throws ServerErrorException, BadRequestException {
    bcTokenService.createBCToken(token);
  }

  @PreAuthorize("hasAnyAuthority('OPS')")
  @GetMapping("/fetch-all/{issuerAddress}")
  public List<BCTokenResponseDto> fetchAllByIssuerAddress(@PathVariable String issuerAddress) throws ServerErrorException {
    return bcTokenService.fetchAllByIssuerAddress(issuerAddress);
  }

  @PreAuthorize("hasAnyAuthority('OPS')")
  @GetMapping("/fetch/{tokenCode}")
  public BCTokenResponseDto fetchTokenByTokenCode(@PathVariable String tokenCode) throws ServerErrorException {
    return bcTokenService.fetchTokenByTokenCode(tokenCode);
  }

  @PreAuthorize("hasAnyAuthority('OPS')")
  @GetMapping("/executeUnavailableChain")
  public void executeUnavailableChain() {
    bcTokenService.executeUnavailableChain();
  }

  @PreAuthorize("hasAnyAuthority('OPS')")
  @GetMapping("/executeUnconfirmedChain")
  public void executeUnconfirmedChain() {
    bcTokenService.executeUnconfirmedChain();
  }

  @PreAuthorize("hasAnyAuthority('OPS')")
  @PostMapping("/ops-transfer")
  public void transferBCToken(@RequestBody TransferBCTokenRequestDto dto) throws ServerErrorException {
    bcTokenService.opsTransfer(dto);
  }

  @PreAuthorize("hasAnyAuthority('OPS')")
  @PostMapping("/ops-trade")
  public void opsTrade(@RequestBody TransferBCTokenRequestDto dto) throws ServerErrorException {
    bcTokenService.opsTrade(dto);
  }

  @PreAuthorize("hasAnyAuthority('CRO')")
  @PostMapping("/cro-trade")
  public void croTrade(@RequestBody TransferBCTokenToOpsRequestDto dto) throws ServerErrorException {
    bcTokenService.croTrade(dto);
  }

  @PreAuthorize("hasAnyAuthority('CRO')")
  @PostMapping("/cro-transfer")
  public void transferBCTokenToOps(@RequestBody TransferBCTokenToOpsRequestDto dto) throws ServerErrorException {
    bcTokenService.croTransfer(dto);
  }

}
