package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.service.UserService;

@RestController
@RequestMapping("/trinity")
@PreAuthorize("hasAuthority('TRINITY_ADMIN')")
public class TrinityController {

  @Autowired
  UserService userService;

  @PostMapping("/user/create")
  public void createUser(@RequestBody ClientRequestDto dto) throws ServerErrorException {
    userService.createUser(dto);
  }

  @PostMapping("/user/update")
  public void updateUser(@RequestBody ClientRequestDto dto) throws ServerErrorException {
    userService.updateUser(dto);
  }
}
