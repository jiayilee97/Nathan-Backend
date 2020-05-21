package stacs.nathan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trinity")
public class TrinityController {

  @GetMapping
  public String test() {
    return "Hello";
  }
}
