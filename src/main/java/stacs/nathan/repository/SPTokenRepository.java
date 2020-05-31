package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.SPToken;

@Repository
public interface SPTokenRepository extends JpaRepository<SPToken, Long> {

}
