package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.UserInfoDto;
import stacs.nathan.service.RolePagesService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/rolepages")
@PreAuthorize("hasAnyAuthority('CRO', 'OPS' ,'MKT', 'CP', 'RISK')")
public class RolePagesController {

  @Autowired
  private RolePagesService rolePagesService;

  @GetMapping("/userinfo")
  public UserInfoDto fetchUserInfo(HttpServletRequest request, HttpServletResponse response) throws ServerErrorException {
    return rolePagesService.fetchUserInfo();
  }

}
