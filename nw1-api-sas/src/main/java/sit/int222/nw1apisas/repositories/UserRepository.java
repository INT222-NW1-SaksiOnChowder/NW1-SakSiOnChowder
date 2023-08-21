package sit.int222.nw1apisas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.nw1apisas.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByOrderByRoleAscUsernameAsc();
}
