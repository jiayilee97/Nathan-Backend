package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.entity.User;
import stacs.nathan.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

  @Autowired
  private UserService userService;

  @GetMapping("/fetch/clients")
  public List<User> fetchAllClients() {
    return userService.fetchAllClients();
  }


}
