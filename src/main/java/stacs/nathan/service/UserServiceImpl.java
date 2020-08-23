package stacs.nathan.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stacs.nathan.core.audit.action.AudibleActionImplementation;
import stacs.nathan.core.audit.action.annotation.AudibleActionTrail;
import stacs.nathan.core.exception.BadRequestException;
import stacs.nathan.core.exception.ServerErrorException;
import stacs.nathan.dto.response.*;
import stacs.nathan.dto.request.ClientRequestDto;
import stacs.nathan.dto.request.CreateClientRequestDto;
import stacs.nathan.dto.request.LoggedInUser;
import stacs.nathan.utils.CommonUtils;
import stacs.nathan.utils.constancs.AuditActionConstants;
import stacs.nathan.utils.enums.AccreditedStatus;
import stacs.nathan.utils.enums.CodeType;
import stacs.nathan.utils.enums.UserRole;
import stacs.nathan.entity.User;
import stacs.nathan.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private BlockchainService blockchainService;

    @Autowired
    private SPTokenService spTokenService;

    @Autowired
    private CodeValueService codeValueService;

    public List<ClientSPPositionResponseDto> fetchClientSPPositions() {
        LOGGER.debug("Entering fetchClientSPPositions().");
        List<ClientSPPositionResponseDto> clientResponseDtos = new ArrayList<>();
        List<User> users = repository.findByRole(UserRole.CLIENT);
        for(User user : users) {
            ClientSPPositionResponseDto dto = new ClientSPPositionResponseDto();
            dto.setId(user.getId());
            dto.setClientId(user.getClientId());
            List<SPTokenResponseDto> spToken = spTokenService.fetchAllOpenPositionsByClientId(user.getClientId());
            dto.setOpenPositions(spToken == null ? 0 : spToken.size() );
            spToken = spTokenService.fetchAllClosedPositionsByClientId(user.getClientId());
            dto.setClosePositions(spToken == null ? 0 : spToken.size());
            dto.setInvestorRisk(null);
            clientResponseDtos.add(dto);
        }
        return clientResponseDtos;
    }

    public User fetchLoginUser() {
        String username = ((LoggedInUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        User user = fetchByUsername(username);
        user.setPrivateKey(null);
        return user;
    }

    public User fetchByUsername(String username) {
        return repository.findByUsername(username);
    }

    public User fetchById(long id) {
        return repository.findById(id);
    }

    public List<ClientResponseDto> fetchAllClients(){
        LOGGER.debug("Entering fetchAllClients().");
        return repository.fetchByRole(UserRole.CLIENT);
    }

    public ClientResponseDto fetchByClientId(String clientId){
        LOGGER.debug("Entering fetchByClientId().");
        return repository.findClientById(clientId);
    }

    public User fetchUserByClientId(String clientId) {
        return repository.findByClientId(clientId);
    }

    public CreateClientInitDto fetchInitForm(){
        LOGGER.debug("Entering fetchInitForm().");
        CreateClientInitDto dto = new CreateClientInitDto();
        dto.setNationalities(codeValueService.findByType(CodeType.NATIONALITY));
        dto.setAccreditedStatus(AccreditedStatus.getValuesSelection());
        return dto;
    }

    public List<String> fetchAllClientIds(){
        LOGGER.debug("Entering fetchAllClientIds().");
        return repository.fetchAllClientIds(UserRole.CLIENT);
    }

    @AudibleActionTrail(module = AuditActionConstants.CLIENT_MODULE, action = AuditActionConstants.CREATE_CLIENT)
    public AudibleActionImplementation<User> createClient(CreateClientRequestDto dto) throws ServerErrorException {
        LOGGER.debug("Entering createClient().");
        try{
            User user = repository.findByClientId(dto.getClientId());
            if(user != null){
                throw new BadRequestException("Client ID already exists.");
            }
            user = new User();
            user.setUuid(CommonUtils.generateRandomUUID());
            user.setRole(UserRole.CLIENT);
            user.setClientId(dto.getClientId());
            user.setDisplayName(dto.getDisplayName());
            user.setNationality(dto.getNationality());
            user.setAccreditedStatus(AccreditedStatus.resolveCode(dto.getAccreditedStatus()));
            user.setRiskToleranceRating(dto.getRiskToleranceRating());
            blockchainService.createWallet(user);
            repository.save(user);
            return new AudibleActionImplementation<>(user);
        }catch (Exception e){
            LOGGER.error("Exception in createClient().", e);
            throw new ServerErrorException("Exception in createClient().", e);
        }
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

    public User fetchByWalletAddressAndRole(String walletAddress, UserRole userRole) {
        return repository.fetchIdByWalletAddressAndRole(walletAddress, userRole);
    }

    public User fetchAppAddress() {
        return repository.fetchAppAddress(UserRole.APP);
    }

    public String fetchOpsWalletAddress() {
        User ops = repository.findByRole(UserRole.OPS).get(0);
        return ops.getWalletAddress();
    }
}
