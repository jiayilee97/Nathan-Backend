package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.utils.enums.UserRole;
import stacs.nathan.entity.User;
import stacs.nathan.repository.UserRepository;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public List<User> fetchAllClients(){
        return repository.findByRole(UserRole.CLIENT);
    }

    public void createUser(ClientRequestDto dto){
        repository.save(convertToUser(dto));
    }

    public void updateUser(ClientRequestDto dto){
        repository.save(convertToUser(dto));
    }

    public void updateUserRole(ClientRequestDto dto){
        repository.save(convertToUser(dto));
    }

    private User convertToUser(ClientRequestDto dto){
        User user = new User();
        user.setUserName(dto.getUsername());
        user.setDisplayName(dto.getDisplayName());
        user.setEmail(dto.getEmail());
        List<String> roles = dto.getRoles();
        if(roles != null && roles.size() > 0){
            user.setRole(UserRole.resolveCode(roles.get(0)));
        }
        return user;
    }
}
