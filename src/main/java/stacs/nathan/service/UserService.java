package stacs.nathan.service;

import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.entity.User;
import java.util.List;

public interface UserService {

    List<User> fetchAllClients();

    void createUser(ClientRequestDto dto);

    void updateUser(ClientRequestDto dto);

    void updateUserRole(ClientRequestDto dto);

}
