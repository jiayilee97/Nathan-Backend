package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.BaseCurrencyToken;

@Repository
public interface BCTokenRepository extends JpaRepository<BaseCurrencyToken, Long> {

}
