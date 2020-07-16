package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.TradeHistoryResponseDto;
import stacs.nathan.entity.SPToken;
import stacs.nathan.entity.TradeHistory;
import stacs.nathan.entity.User;

import java.util.Date;
import java.util.List;

@Repository
public interface TradeHistoryRepository extends JpaRepository<TradeHistory, Long> {

    @Query("SELECT NEW stacs.nathan.dto.response.TradeHistoryResponseDto(th.updatedDate, th.side, th.underlying, th.quantity, th.user, th.spToken)" +
            "FROM TradeHistory th WHERE th.updatedDate >= :startDate AND th.updatedDate <= :endDate")
    List<TradeHistoryResponseDto> findAllByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT NEW stacs.nathan.dto.response.TradeHistoryResponseDto(th.updatedDate, th.side, th.underlying, th.quantity, th.user, th.spToken)" +
              "FROM TradeHistory th WHERE th.user =:user AND th.spToken =:spToken")
    List<TradeHistoryResponseDto> findAllUserAndSPToken(@Param("user") User user, @Param("spToken") SPToken spToken);
}
