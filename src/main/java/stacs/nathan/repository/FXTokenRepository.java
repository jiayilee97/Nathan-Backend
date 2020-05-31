package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.FXToken;

@Repository
public interface FXTokenRepository extends JpaRepository<FXToken, Long> {

}
