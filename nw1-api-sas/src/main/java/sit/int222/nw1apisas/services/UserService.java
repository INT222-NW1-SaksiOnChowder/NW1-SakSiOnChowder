package sit.int222.nw1apisas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.dtos.users.CreateUpdateUserDto;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAllOfUsers() {
        return userRepository.findAllByOrderByRoleAscUsernameAsc();
    }

    public User createUser(CreateUpdateUserDto newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername().trim());
        user.setName(newUser.getName().trim());
        user.setEmail(newUser.getEmail().trim());
        user.setRole(newUser.getRole().trim());
        userRepository.save(user);
        userRepository.refresh(user);
        return user;
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User id: " + id + "not found"));
    }

    public User updateUser(CreateUpdateUserDto newUser, Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("The user is not found."));
        existingUser.setUsername(newUser.getUsername().trim());
        existingUser.setName(newUser.getName().trim());
        existingUser.setEmail(newUser.getEmail().trim());
        existingUser.setRole(newUser.getRole().trim());
        userRepository.save(existingUser);
        userRepository.refresh(existingUser);
        return existingUser;
    }

    public void deleteUser(Integer id) {
        if (id == null && !(id instanceof Integer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id must be integer");
        } else {
            userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("The announcement is not found."));
            userRepository.deleteById(id);
        }
    }

}
