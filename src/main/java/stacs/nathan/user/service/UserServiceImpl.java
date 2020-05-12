package stacs.nathan.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stacs.nathan.Utils.enums.UserRole;
import stacs.nathan.entity.User;
import stacs.nathan.user.repository.UserRepository;
import java.util.List;

@Service("userService")
@Transactional(readOnly = false)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public List<User> fetchAllClients(){
        return repository.findByRole(UserRole.CLI);
    }
}
