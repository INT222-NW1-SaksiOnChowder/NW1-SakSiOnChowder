package sit.int222.nw1apisas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int222.nw1apisas.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);

}
