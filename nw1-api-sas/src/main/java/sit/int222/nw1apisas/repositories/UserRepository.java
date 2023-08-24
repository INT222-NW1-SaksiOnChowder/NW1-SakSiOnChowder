package sit.int222.nw1apisas.repositories;


import sit.int222.nw1apisas.entities.User;

import java.util.List;

public interface UserRepository extends CustomRepository<User, Integer> {
    List<User> findAllByOrderByRoleAscUsernameAsc();
}
