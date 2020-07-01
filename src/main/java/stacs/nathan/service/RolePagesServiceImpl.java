package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.dto.response.UserInfoDto;
import stacs.nathan.entity.RolePages;
import stacs.nathan.entity.User;
import stacs.nathan.repository.RolePagesRepository;
import java.util.List;

@Service
public class RolePagesServiceImpl implements RolePagesService {
  private static final Logger LOGGER = LoggerFactory.getLogger(RolePagesServiceImpl.class);

  @Autowired
  RolePagesRepository repository;

  @Autowired
  UserService userService;

  public UserInfoDto fetchUserInfo() throws ServerErrorException {
    LOGGER.debug("Entering fetchUserInfo().");
    try{
      LoggedInUser loggedInUser = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      User user = userService.fetchByUsername(loggedInUser.getUsername());
      List<String> roles = loggedInUser.getRoles();
      UserInfoDto userInfoDto = new UserInfoDto();
      userInfoDto.setUsername(user.getUsername());
      userInfoDto.setName(user.getDisplayName());
      userInfoDto.setPageNames(repository.findByRoles(roles));
      userInfoDto.setRoleName(roles);
      return userInfoDto;
    }catch (Exception e){
      LOGGER.error("Exception in fetchUserInfo().", e);
      throw new ServerErrorException("Exception in fetchUserInfo().", e);
    }
  }

  public void createRolePages(RolePages rolePages) throws ServerErrorException {
    LOGGER.debug("Entering createRolePages().");
    try{
      LoggedInUser loggedInUser = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      rolePages.setCreatedBy(loggedInUser.getUsername());
      repository.save(rolePages);
    }catch (Exception e){
      LOGGER.error("Exception in createRolePages().", e);
      throw new ServerErrorException("Exception in createRolePages().", e);
    }
  }

  public void updateRolePages(RolePages rolePages) throws ServerErrorException {
    LOGGER.debug("Entering updateRolePages().");
    try{
      LoggedInUser loggedInUser = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      rolePages.setUpdatedBy(loggedInUser.getUsername());
      repository.save(rolePages);
    }catch (Exception e){
      LOGGER.error("Exception in updateRolePages().", e);
      throw new ServerErrorException("Exception in updateRolePages().", e);
    }
  }

  public void deleteRolePages(long id) throws ServerErrorException {
    LOGGER.debug("Entering deleteRolePages().");
    try{
      RolePages rolePages = new RolePages();
      LoggedInUser loggedInUser = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
      rolePages.setUpdatedBy(loggedInUser.getUsername());
      rolePages = repository.findById(id);
      repository.delete(rolePages);
    }catch (Exception e){
      LOGGER.error("Exception in deleteRolePages().", e);
      throw new ServerErrorException("Exception in deleteRolePages().", e);
    }
  }
}
