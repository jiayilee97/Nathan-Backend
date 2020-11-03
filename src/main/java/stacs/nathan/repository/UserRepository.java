package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.ClientResponseDto;
import stacs.nathan.utils.enums.UserRole;
import stacs.nathan.entity.User;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByRoleAndIsVisible(UserRole role, Boolean isVisible);

    User findByUsernameAndIsVisible(String username, Boolean isVisible);

    User findByIdAndIsVisible(long id, Boolean isVisible);

    User findByClientIdAndIsVisible(String clientId, Boolean isVisible);

    @Query("SELECT NEW stacs.nathan.dto.response.ClientResponseDto(u.clientId, u.displayName, u.nationality, u.accreditedStatus, u.riskToleranceRating, u.walletAddress) FROM User u where u.role = :role and u.isVisible = true")
    List<ClientResponseDto> fetchByRole(@Param("role") UserRole role);

    @Query("SELECT NEW stacs.nathan.dto.response.ClientResponseDto(u.clientId, u.displayName, u.nationality, u.accreditedStatus, u.riskToleranceRating, u.walletAddress) FROM User u where u.clientId = :clientId and u.isVisible = true")
    ClientResponseDto findClientById(@Param("clientId") String clientId);

    @Query("SELECT u.clientId FROM User u where u.role = :role and u.isVisible = true")
    List<String> fetchAllClientIds(@Param("role") UserRole role);

    @Query("SELECT u FROM User u where u.walletAddress = :walletAddress and u.role = :role and u.isVisible = true")
    User fetchIdByWalletAddressAndRole(@Param("walletAddress") String walletAddress, @Param("role") UserRole role);

    @Query("SELECT u FROM User u where u.role = :role and u.isVisible = true")
    User fetchUserByRole(@Param("role") UserRole role);

    @Query("SELECT u FROM User u WHERE u.clientId = :clientId and u.isVisible = true")
    User fetchByClientId(@Param("clientId") String clientId);

    @Query("SELECT u FROM User u WHERE u.role = :role and u.isVisible = true")
    User fetchAppAddress(@Param("role") UserRole role);
}
