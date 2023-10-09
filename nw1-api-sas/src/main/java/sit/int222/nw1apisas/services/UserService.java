package sit.int222.nw1apisas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.dtos.users.CreateUserDto;
import sit.int222.nw1apisas.dtos.users.UpdateUserDto;
import sit.int222.nw1apisas.dtos.users.UsernamePasswordDto;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.exceptions.UnAuthorizationException;
import sit.int222.nw1apisas.exceptions.ValidationUniqueException;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllOfUsers() {
        return userRepository.findAllByOrderByRoleAscUsernameAsc();
    }

    public User createUser(CreateUserDto newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername().trim());
        Argon2PasswordEncoder arg2 = new Argon2PasswordEncoder(16, 16, 1, 4096, 3);
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

    public User updateUser(UpdateUserDto updateUserDto, Integer id)  {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("This user not found"));
        if (!existingUser.getUsername().equals(updateUserDto.getUsername())) {
            if (!userRepository.existsUserByUsername(updateUserDto.getUsername())) {
                existingUser.setUsername(updateUserDto.getUsername());
            }else{
                throw new ValidationUniqueException("username","does not unique");
            }
        }
        if (!existingUser.getName().equals(updateUserDto.getName())) {
            if (!userRepository.existsUserByName(updateUserDto.getName())) {
                existingUser.setName(updateUserDto.getName());
            } else {
                throw new ValidationUniqueException("name","does not unique");
            }
        }
        if (!existingUser.getEmail().equals(updateUserDto.getEmail())) {
            if (!userRepository.existsUserByEmail(updateUserDto.getEmail())) {
                existingUser.setEmail(updateUserDto.getEmail());
            }else {
                throw new ValidationUniqueException("email","does not unique");
            }
        }
        existingUser.setRole(updateUserDto.getRole());
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
        Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 16, 1, 4096, 3);
        User existUser = userRepository.findUserByUsername(usernamePasswordDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("The specified username DOES NOT exist"));
        String storedPassword = existUser.getPassword();
        if (argon2PasswordEncoder.matches(usernamePasswordDto.getPassword(), storedPassword)) {
            return "Password Matched";
        } else {
            throw new UnAuthorizationException("Password NOT Matched");
        }
    }
}
