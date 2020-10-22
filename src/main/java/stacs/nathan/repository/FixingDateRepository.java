package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.FixingDate;

@Repository
public interface FixingDateRepository extends JpaRepository<FixingDate, Long> {

}
