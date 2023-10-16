package sit.int222.nw1apisas.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int222.nw1apisas.dtos.users.CreateUserDto;
import sit.int222.nw1apisas.dtos.users.UpdateUserDto;
import sit.int222.nw1apisas.dtos.users.UsernamePasswordDto;
import sit.int222.nw1apisas.entities.Announcement;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.exceptions.UnAuthorizationException;
import sit.int222.nw1apisas.exceptions.UserForbiddenException;
import sit.int222.nw1apisas.exceptions.ValidationUniqueException;
import sit.int222.nw1apisas.repositories.AnnouncementRepository;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnouncementRepository announcementRepository;

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

    public User updateUser(UpdateUserDto updateUserDto, Integer id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("This user not found"));
        if (!existingUser.getUsername().equals(updateUserDto.getUsername().trim())) {
            if (!userRepository.existsUserByUsername(updateUserDto.getUsername().trim())) {
                existingUser.setUsername(updateUserDto.getUsername().trim());
            } else {
                System.out.println("test username not unique");
                throw new ValidationUniqueException("username", "does not unique");
            }
        }
        if (!existingUser.getName().equals(updateUserDto.getName().trim())) {
            if (!userRepository.existsUserByName(updateUserDto.getName().trim())) {
                existingUser.setName(updateUserDto.getName().trim());
            } else {
                System.out.println("test name not unique");
                throw new ValidationUniqueException("name", "does not unique");
            }
        }
        if (!existingUser.getEmail().equals(updateUserDto.getEmail().trim())) {
            if (!userRepository.existsUserByEmail(updateUserDto.getEmail().trim())) {
                existingUser.setEmail(updateUserDto.getEmail().trim());
            } else {
                System.out.println("test email not unique");
                throw new ValidationUniqueException("email", "does not unique");
            }
        }
        if (!existingUser.getRole().equals(updateUserDto.getRole().trim())) {
            existingUser.setRole(updateUserDto.getRole().trim());
        }
        userRepository.saveAndFlush(existingUser);
        userRepository.refresh(existingUser);
        return existingUser;
//        if (existingUser.getUsername().equals(updateUserDto.getUsername().trim())) {
//            System.out.println("test username not unique");
//            throw new ValidationUniqueException("username", "does not unique");
//        } else {
//            existingUser.setUsername(updateUserDto.getUsername().trim());
//        }
//        if (existingUser.getName().equals(updateUserDto.getName().trim())) {
//            System.out.println("test name not unique");
//            throw new ValidationUniqueException("name", "does not unique");
//        } else {
//            existingUser.setName(updateUserDto.getName().trim());
//        }
//        if (existingUser.getEmail().equals(updateUserDto.getEmail().trim())) {
//            System.out.println("test email not unique");
//            throw new ValidationUniqueException("email", "does not unique");
//        } else {
//            existingUser.setEmail(updateUserDto.getEmail().trim());
//        }
    }

    public String deleteUser(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        if (id == null || !(id instanceof Integer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id must be integer");
        }

        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_admin"))) {
            User user = userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("The user is not found."));
            if (!user.getUsername().equals(currentPrincipalName)) {
                List<Announcement> userAnnouncements = announcementRepository.findAllByAnnouncementOwner(user);
                User newOwner = userRepository.findUserByUsername(currentPrincipalName).orElseThrow(() -> new ItemNotFoundException("New owner not found."));
                for (Announcement announcement : userAnnouncements) {
                    announcement.setAnnouncementOwner(newOwner);
                    announcementRepository.saveAndFlush(announcement);
                }
                userRepository.deleteById(id);
                return "Delete user id: " + id + " successfully.";
            }
        }
        throw new UserForbiddenException("You cannot delete your own account");
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
