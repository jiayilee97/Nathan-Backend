package stacs.nathan.controller;

import org.springframework.web.bind.annotation.*;
import stacs.nathan.dto.request.ClientRequestDto;

@RestController
@RequestMapping("/trinity")
public class TrinityController {

  @PostMapping("/user/create")
  public void createUser(@RequestBody ClientRequestDto dto) {
    System.out.println("RequestBody: " + dto);
  }

  @PostMapping("/user/update")
  public void updateUser(@RequestBody ClientRequestDto dto) {
    System.out.println("RequestBody: " + dto);
  }

  @PostMapping("/user/role/update")
  public void updateUserRole(@RequestBody ClientRequestDto dto) {
    System.out.println("RequestBody: " + dto);
  }
}
