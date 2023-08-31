package sit.int222.nw1apisas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.dtos.users.CreateUpdateUserDto;
import sit.int222.nw1apisas.entities.User;
import sit.int222.nw1apisas.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllOfUsers() {
        return userService.getAllOfUsers();
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public User createUser(@RequestBody @Valid CreateUpdateUserDto newUser) {
        return userService.createUser(newUser);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@RequestBody @Valid CreateUpdateUserDto newUser, @PathVariable Integer id) {
        return userService.updateUser(newUser, id);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "Delete id : " + id + " successfully";
    }


}
