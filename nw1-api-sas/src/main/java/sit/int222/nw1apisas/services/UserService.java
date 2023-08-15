package sit.int222.nw1apisas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import sit.int222.nw1apisas.dtos.CreateUpdateUserDto;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.exceptions.ItemNotFoundException;
import sit.int222.nw1apisas.repositories.UserRepository;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllOfUsers() {
        return userRepository.findAll();
    }

    public User createUser(@RequestBody CreateUpdateUserDto newUser) {
        User user = new User();
        user.setUsername(newUser.getUsername());
        user.setName(newUser.getName());
        user.setEmail(newUser.getEmail());
        user.setRole(newUser.getRole());
        return userRepository.saveAndFlush(user);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("User id: " + id + "not found"));
    }

    public User updateUser(CreateUpdateUserDto newUser, Integer id) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("The user is not found."));

        existingUser.setUsername(newUser.getUsername());
        existingUser.setName(newUser.getName());
        existingUser.setEmail(newUser.getEmail());
        existingUser.setRole(newUser.getRole());

        return userRepository.saveAndFlush(existingUser);
    }

    public void deleteUser(Integer id){
        userRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("The announcement is not found."));
        userRepository.deleteById(id);
    }

}
