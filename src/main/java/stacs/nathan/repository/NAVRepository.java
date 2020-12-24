package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.NAV;
import java.util.Date;
import java.util.List;

@Repository
public interface NAVRepository extends JpaRepository<NAV, Long> {

  NAV findFirstByOrderByIdDesc();

  // fetch all except current NAV
//  @Query("SELECT nav FROM NAV nav WHERE nav.id NOT IN (SELECT MAX(n.id) FROM NAV n) and " +
//      "nav.updatedDate >= :startDate AND nav.updatedDate <= :endDate ORDER BY nav.id DESC")
  @Query("SELECT nav FROM NAV nav WHERE " +
      "nav.updatedDate >= :startDate AND nav.updatedDate <= :endDate ORDER BY nav.id DESC")
  List<NAV> fetchAllNAV(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
