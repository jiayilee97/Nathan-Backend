package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.CodeValue;
import stacs.nathan.utils.enums.CodeType;
import java.util.List;

@Repository
public interface CodeValueRepository extends JpaRepository<CodeValue, Long> {

  List<CodeValue> findByType(CodeType type);

}
