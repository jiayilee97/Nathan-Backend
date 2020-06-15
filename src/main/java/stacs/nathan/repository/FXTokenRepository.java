package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.FXToken;
import stacs.nathan.utils.enums.FXTokenStatus;

import java.util.List;

@Repository
public interface FXTokenRepository extends JpaRepository<FXToken, Long> {

    @Query("SELECT fx FROM FXToken fx WHERE fx.status = :status")
    List<FXToken> fetchAllUnconfirmedChain(@Param("status") FXTokenStatus status);
}
