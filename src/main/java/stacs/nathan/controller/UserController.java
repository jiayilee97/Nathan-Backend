package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.CreateClientRequestDto;
import stacs.nathan.dto.response.ClientSPPositionResponseDto;
import stacs.nathan.entity.User;
import stacs.nathan.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
//@PreAuthorize("hasAuthority('CRO')")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/fetch/clientSPPositions")
  public List<ClientSPPositionResponseDto> fetchClientSPPositions() {
    return userService.fetchClientSPPositions();
  }

  @GetMapping("/fetch/loginuser")
  public User fetchLoginUser(){
    return userService.fetchLoginUser();
  }

  @PostMapping("/create/client")
  public void createClient(@RequestBody CreateClientRequestDto dto) throws ServerErrorException {
    userService.createClient(dto);
  }

}
