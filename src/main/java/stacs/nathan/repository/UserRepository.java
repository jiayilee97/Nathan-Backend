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

    List<User> findByRole(UserRole role);

    User findByUsername(String username);

    User findById(long id);

    User findByClientId(String clientId);

    @Query("SELECT NEW stacs.nathan.dto.response.ClientResponseDto(u.clientId, u.displayName, u.nationality, u.accreditedStatus, u.riskToleranceRating, u.walletAddress) FROM User u where u.role = :role")
    List<ClientResponseDto> fetchByRole(@Param("role") UserRole role);

    @Query("SELECT NEW stacs.nathan.dto.response.ClientResponseDto(u.clientId, u.displayName, u.nationality, u.accreditedStatus, u.riskToleranceRating, u.walletAddress) FROM User u where u.clientId = :clientId")
    ClientResponseDto findClientById(@Param("clientId") String clientId);

    @Query("SELECT u.clientId FROM User u where u.role = :role")
    List<String> fetchAllClientIds(@Param("role") UserRole role);

    @Query("SELECT u FROM User u where u.walletAddress = :walletAddress")
    User fetchIdByWalletAddress(@Param("walletAddress") String walletAddress);

}
