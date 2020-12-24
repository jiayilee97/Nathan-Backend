package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.InvestorRisk;

@Repository
public interface InvestorRiskRepository extends JpaRepository<InvestorRisk, Long> {

}
