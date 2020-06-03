package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.utils.CommonUtils;
import stacs.nathan.utils.enums.UserRole;
import stacs.nathan.entity.User;
import stacs.nathan.repository.UserRepository;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private BlockchainService blockchainService;

    public List<User> fetchAllClients() {
        return repository.findByRole(UserRole.CLIENT);
    }

    public User fetchLoginUser() {
        String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return fetchByUsername(username);
    }

    public User fetchByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void createUser(ClientRequestDto dto) throws ServerErrorException {
        LOGGER.debug("Entering createUser().");
        try{
            User user =  convertToUser(dto, false);
            user.setUuid(CommonUtils.generateRandomUUID());
            repository.save(user);
        }catch (Exception e){
            LOGGER.error("Exception in createUser().", e);
            throw new ServerErrorException("Exception in createUser().", e);
        }
    }

    public void updateUser(ClientRequestDto dto) throws ServerErrorException {
        LOGGER.debug("Entering updateUser().");
        try{
            User user =  convertToUser(dto, true);
            if(user.getRole() != null && UserRole.CRO != user.getRole()){
                blockchainService.createWallet(user);
            }
            repository.save(user);
        }catch (Exception e){
            LOGGER.error("Exception in updateUser().", e);
            throw new ServerErrorException("Exception in updateUser().", e);
        }
    }

    private User convertToUser(ClientRequestDto dto, boolean existingUser) {
        User user = new User();
        if(existingUser){
            user = repository.findByUsername(dto.getUsername());
        }else{
            user.setUsername(dto.getUsername());
        }
        user.setDisplayName(dto.getDisplayName());
        user.setEmail(dto.getEmail());
        List<String> roles = dto.getRoles();
        if(roles != null && roles.size() > 0){
            user.setRole(UserRole.resolveCode(roles.get(0)));
        }
        return user;
    }
}
