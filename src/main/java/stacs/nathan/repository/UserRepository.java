package stacs.nathan.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import stacs.nathan.Utils.enums.UserRole;
import stacs.nathan.entity.User;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByRole(UserRole role);
}
