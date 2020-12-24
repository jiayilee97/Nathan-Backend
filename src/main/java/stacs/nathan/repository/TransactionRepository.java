package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.TransactionHistoryResponseDto;
import stacs.nathan.entity.TransactionHistory;
import stacs.nathan.entity.User;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionHistory, Long> {

    @Query("SELECT NEW stacs.nathan.dto.response.TransactionHistoryResponseDto(tx.tokenCode, tx.amount, tx.toAddress, tx.status, tx.ctxId, tx.updatedDate)" +
            "FROM TransactionHistory tx WHERE tx.updatedDate >= :startDate AND tx.updatedDate <= :endDate AND tx.isVisible = true")
    List<TransactionHistoryResponseDto> findAllByCreatedBy(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
