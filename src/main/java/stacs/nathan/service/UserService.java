package stacs.nathan.service;

import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.entity.User;
import java.util.List;

public interface UserService {

    List<User> fetchAllClients();

    User fetchLoginUser();

    User fetchByUsername(String username);

    void createUser(ClientRequestDto dto) throws ServerErrorException;

    void updateUser(ClientRequestDto dto) throws ServerErrorException;

}
