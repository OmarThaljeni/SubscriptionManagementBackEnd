package Application.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import Application.Models.Role;
import Application.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  List<User> findByRoles(Role roles);

}
