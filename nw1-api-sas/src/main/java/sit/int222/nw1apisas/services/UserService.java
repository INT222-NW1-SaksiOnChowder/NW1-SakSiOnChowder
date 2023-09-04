package sit.int222.nw1apisas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.dtos.users.CreateUserDto;
import sit.int222.nw1apisas.dtos.users.UpdateUserDto;
import sit.int222.nw1apisas.dtos.users.UsernamePasswordDto;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;

    public List<User> getAllOfUsers() {
        return userRepository.findAllByOrderByRoleAscUsernameAsc();
    }

    public User createUser(CreateUserDto newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername().trim());
        Argon2PasswordEncoder arg2 = new Argon2PasswordEncoder(16, 32, 1, 60000, 10);
        user.setPassword(arg2.encode(newUser.getPassword()));
        user.setName(newUser.getName().trim());
        user.setEmail(newUser.getEmail().trim());
        user.setRole(newUser.getRole().trim());
        userRepository.save(user);
        userRepository.refresh(user);
        return user;
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User id: " + id + " not found"));
    }

    public User updateUser(UpdateUserDto newUser, Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("The user is not found."));
        existingUser.setUsername(newUser.getUsername());
        existingUser.setName(newUser.getName().trim());
        existingUser.setEmail(newUser.getEmail().trim());
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

    public String matchPassword(UsernamePasswordDto usernamePasswordDto) {
        User existUser = userRepository.findUserByUsername(usernamePasswordDto.getUsername());
        if (existUser == null) {
            throw new ItemNotFoundException("This specified username DOES NOT exist");
        }
        String storedPassword = existUser.getPassword();
        if (argon2PasswordEncoder.matches(usernamePasswordDto.getPassword(), storedPassword)) {
            return "Password Matched";
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password NOT Matched");
        }
    }
}
