package sit.int222.nw1apisas.repositories;


import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.repositories.customs.CustomRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CustomRepository<User, Integer> {
    List<User> findAllByOrderByRoleAscUsernameAsc();

    Boolean existsUserByUsername(String username);

    Boolean existsUserByName(String name);

    Boolean existsUserByEmail(String email);

    Optional<User> findUserByUsername(String username);



}
