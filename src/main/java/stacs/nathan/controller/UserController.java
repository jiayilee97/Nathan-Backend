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

  @PreAuthorize("hasAuthority('CRO') or hasAuthority('OPS') or hasAuthority('MKT') or hasAuthority('CP')")
  @GetMapping("/fetch/clientSPPositions")
  public List<ClientSPPositionResponseDto> fetchClientSPPositions() {
    return userService.fetchClientSPPositions();
  }

  @GetMapping("/fetch/loginuser")
  public User fetchLoginUser(){
    return userService.fetchLoginUser();
  }

  @PreAuthorize("hasAuthority('CRO')")
  @PostMapping("/create/client")
  public void createClient(@RequestBody CreateClientRequestDto dto) throws ServerErrorException {
    userService.createClient(dto);
  }

  @PreAuthorize("hasAuthority('CRO') or hasAuthority('OPS')")
  @GetMapping("/fetch/clients")
  public List<ClientResponseDto> fetchAllClients(){
    return userService.fetchAllClients();
  }

  @PreAuthorize("hasAuthority('CRO')")
  @GetMapping("/fetch/client/{id}")
  public ClientResponseDto fetchByClientId(@PathVariable("id") String clientId){
    return userService.fetchByClientId(clientId);
  }

  @PreAuthorize("hasAuthority('CRO')")
  @GetMapping("/init/client")
  public CreateClientInitDto initForm(){
    return userService.fetchInitForm();
  }

  @PreAuthorize("hasAuthority('CRO')")
  @GetMapping("/fetch/ops-wallet")
  public String fetchOpsWalletAddress() { return userService.fetchOpsWalletAddress(); }

}
