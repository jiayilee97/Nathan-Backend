package stacs.nathan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import stacs.nathan.entity.RolePages;
import java.util.List;

@Repository
public interface RolePagesRepository extends JpaRepository<RolePages, Long> {

  @Query("SELECT r FROM RolePages r WHERE r.roleName IN :roles")
  List<RolePages> findByRoles(@Param("roles") List<String> roles);

  RolePages findById(long id);

}
