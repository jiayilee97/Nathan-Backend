package stacs.nathan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stacs.nathan.utils.enums.UserRole;
import stacs.nathan.entity.User;
import stacs.nathan.repository.UserRepository;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    public List<User> fetchAllClients(){
        return repository.findByRole(UserRole.CLI);
    }
}
