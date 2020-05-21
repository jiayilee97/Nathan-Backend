package stacs.nathan.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trinity")
public class TrinityController {

  @PostMapping("/user/create")
  public String createUser() {
    return "Hello";
  }
}
