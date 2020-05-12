package stacs.nathan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/public")
public class PublicController {

  @GetMapping("/health")
  public String getHealthCheck() {
    return "OK";
  }
}
