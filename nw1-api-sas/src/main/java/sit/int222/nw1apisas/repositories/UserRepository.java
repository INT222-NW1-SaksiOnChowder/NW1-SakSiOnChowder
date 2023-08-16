package sit.int222.nw1apisas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int222.nw1apisas.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u ORDER BY u.role, u.username ASC ")
    List<User> findAllUserByRoleAscAndUsernameAsc();
}
