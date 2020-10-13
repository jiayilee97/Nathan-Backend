package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.CreateClientRequestDto;
import stacs.nathan.dto.response.ClientResponseDto;
import stacs.nathan.dto.response.ClientSPPositionResponseDto;
import stacs.nathan.dto.response.CreateClientInitDto;
import stacs.nathan.entity.User;
import stacs.nathan.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private UserService userService;

  @PreAuthorize("hasAnyAuthority('CRO', 'OPS', 'MKT', 'CP')")
  @GetMapping("/fetch/clientSPPositions")
  public List<ClientSPPositionResponseDto> fetchClientSPPositions() {
    return userService.fetchClientSPPositions();
  }

  @PreAuthorize("hasAnyAuthority('CRO', 'OPS', 'MKT', 'CP')")
  @GetMapping("/fetch/loginuser")
  public User fetchLoginUser(){
    return userService.fetchLoginUser();
  }

  @PreAuthorize("hasAnyAuthority('CRO')")
  @PostMapping("/create/client")
  public void createClient(@RequestBody CreateClientRequestDto dto) throws ServerErrorException {
    userService.createClient(dto);
  }

  @PreAuthorize("hasAnyAuthority('CRO', 'OPS', 'RISK')")
  @GetMapping("/fetch/clients")
  public List<ClientResponseDto> fetchAllClients(){
    return userService.fetchAllClients();
  }

  @PreAuthorize("hasAnyAuthority('CRO', 'RISK')")
  @GetMapping("/fetch/client/{id}")
  public ClientResponseDto fetchByClientId(@PathVariable("id") String clientId){
    return userService.fetchByClientId(clientId);
  }

  @PreAuthorize("hasAnyAuthority('CRO')")
  @GetMapping("/init/client")
  public CreateClientInitDto initForm(){
    return userService.fetchInitForm();
  }

  @PreAuthorize("hasAnyAuthority('CRO')")
  @GetMapping("/fetch/ops-wallet")
  public String fetchOpsWalletAddress() { return userService.fetchOpsWalletAddress(); }

}
