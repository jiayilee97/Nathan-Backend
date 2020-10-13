package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stacs.nathan.service.JWTService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@PreAuthorize("hasAnyAuthority('CRO', 'OPS', 'MKT', 'CP', 'RISK')")
public class AuthController {

  @Autowired
  private JWTService jwtService;

  @GetMapping("/login")
  public void login(HttpServletRequest request, HttpServletResponse response) {
    jwtService.setIdTokenCookie(request, response);
    jwtService.setRefreshTokenCookie(request, response);
  }

  @GetMapping("/logout")
  public void logout(HttpServletRequest request, HttpServletResponse response) {
    jwtService.logout(request, response);
  }

}
