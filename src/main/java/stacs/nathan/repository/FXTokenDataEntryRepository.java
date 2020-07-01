package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stacs.nathan.dto.response.FXTokenDataEntryResponseDto;
import stacs.nathan.entity.FXTokenDataEntry;

import java.util.List;

@Repository
public interface FXTokenDataEntryRepository extends JpaRepository<FXTokenDataEntry, Long> {

    @Query("SELECT NEW stacs.nathan.dto.response.FXTokenDataEntryResponseDto(fxd.price, fxd.fxCurrency, fxd.entryDate)" +
            "FROM FXTokenDataEntry fxd")
    List<FXTokenDataEntryResponseDto> fetchAll();
}
