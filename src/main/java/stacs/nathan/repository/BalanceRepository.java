package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.BalanceResponseDto;
import stacs.nathan.entity.Balance;
import stacs.nathan.entity.User;
import java.util.List;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

  List<Balance> findByUser(User user);

  @Query("SELECT NEW stacs.nathan.dto.response.BalanceResponseDto(b.tokenCode, b.tokenType, b.balanceAmount) " +
      "FROM Balance b where b.user.clientId = :clientId")
  List<BalanceResponseDto> findByClientId(@Param("clientId") String clientId);

//  @Query("SELECT NEW stacs.nathan.dto.response.BalanceResponseDto(b.tokenCode, b.tokenType, b.balanceAmount)" +
//          "FROM BALANCE b where b.tokenCode = :tokenCode")
//  BalanceResponseDto findByTokenCode(@Param("tokenCode") String tokenCode);

  Balance findByTokenCode(@Param("tokenCode") String tokenCode);

}
