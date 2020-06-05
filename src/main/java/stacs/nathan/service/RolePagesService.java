package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.UserInfoDto;
import stacs.nathan.entity.RolePages;

public interface RolePagesService {

  UserInfoDto fetchUserInfo() throws ServerErrorException;

  void createRolePages(RolePages rolePages) throws ServerErrorException;

  void updateRolePages(RolePages rolePages) throws ServerErrorException;

  void deleteRolePages(long id) throws ServerErrorException;

}
