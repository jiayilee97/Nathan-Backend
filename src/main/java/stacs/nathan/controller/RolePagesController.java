package stacs.nathan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.UserInfoDto;
import stacs.nathan.entity.RolePages;
import stacs.nathan.service.RolePagesService;

@RestController
@RequestMapping("/rolepages")
@PreAuthorize("hasAuthority('CRO') or hasAuthority('OPS') or hasAuthority('MKT') or hasAuthority('CP') or hasAuthority('RISK')")
public class RolePagesController {

  @Autowired
  RolePagesService rolePagesService;

  @GetMapping("/userinfo")
  public UserInfoDto fetchUserInfo() throws ServerErrorException {
    return rolePagesService.fetchUserInfo();
  }

//  @PostMapping("/create")
//  public void createRolePages(@RequestBody RolePages rolePages) throws ServerErrorException {
//    rolePagesService.createRolePages(rolePages);
//  }
//
//  @PostMapping("/update")
//  public void updateRolePages(@RequestBody RolePages rolePages) throws ServerErrorException {
//    rolePagesService.updateRolePages(rolePages);
//  }
//
//  @PostMapping("/delete")
//  public void deleteRolePages(@RequestBody long id) throws ServerErrorException {
//    rolePagesService.deleteRolePages(id);
//  }

}
