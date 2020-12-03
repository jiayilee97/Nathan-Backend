package stacs.nathan.service;

import stacs.nathan.core.audit.action.AudibleActionImplementation;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.dto.request.CreateClientRequestDto;
import stacs.nathan.dto.response.ClientResponseDto;
import stacs.nathan.dto.response.ClientSPPositionResponseDto;
import stacs.nathan.dto.response.CreateClientInitDto;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.UserRole;
import java.util.List;

public interface UserService {

    List<ClientSPPositionResponseDto> fetchClientSPPositions();

    User fetchLoginUser();

    User fetchByUsername(String username);

    User fetchById(long id);

    List<ClientResponseDto> fetchAllClients();

    ClientResponseDto fetchByClientId(String clientId);

    User fetchUserByClientId(String clientId);

    CreateClientInitDto fetchInitForm();

    List<String> fetchAllClientIds();

    AudibleActionImplementation<User> createClient(CreateClientRequestDto dto) throws ServerErrorException;

    void createUser(ClientRequestDto dto) throws ServerErrorException;

    void updateUser(ClientRequestDto dto) throws ServerErrorException;

    User fetchByWalletAddressAndRole(String walletAddress, UserRole userRole);

    User fetchAppAddress();

    List<String>  fetchOpsWalletAddress();

}
