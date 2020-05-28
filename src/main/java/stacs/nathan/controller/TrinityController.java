package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.service.UserService;

@RestController
@RequestMapping("/trinity")
public class TrinityController {

  @Autowired
  UserService userService;

  @PostMapping("/user/create")
  public void createUser(@RequestBody ClientRequestDto dto) {
    userService.createUser(dto);
  }

  @PostMapping("/user/update")
  public void updateUser(@RequestBody ClientRequestDto dto) {
    userService.updateUser(dto);
  }

  @PostMapping("/user/role/update")
  public void updateUserRole(@RequestBody ClientRequestDto dto) {
    userService.updateUserRole(dto);
  }
}
