package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.SPTokenResponseDto;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.User;
import stacs.nathan.utils.enums.SPTokenStatus;
import java.util.List;

@Repository
public interface SPTokenRepository extends JpaRepository<SPToken, Long> {


  @Query("SELECT sp FROM SPToken sp WHERE sp.user = :user AND sp.status = :status")
  List<SPTokenResponseDto> fetchAllOpenPositions(@Param("user") User user, @Param("status") SPTokenStatus status);

  @Query("SELECT sp FROM SPToken sp WHERE sp.user = :user AND sp.status != :status")
  List<SPTokenResponseDto> fetchAllClosedPositions(@Param("user") User user, @Param("status") SPTokenStatus status);



}
