package sit.int222.nw1apisas.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import sit.int222.nw1apisas.dtos.users.CreateUserDto;
import sit.int222.nw1apisas.dtos.users.ResponseUserDto;
import sit.int222.nw1apisas.dtos.users.UpdateUserDto;
import sit.int222.nw1apisas.dtos.users.UsernamePasswordDto;
import sit.int222.nw1apisas.services.UserService;
import sit.int222.nw1apisas.utils.ListMapper;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://ip22nw1.sit.kmutt.ac.th", "http://intproj22.sit.kmutt.ac.th", "https://intproj22.sit.kmutt.ac.th", "http://localhost:5173"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    @GetMapping("")
    public List<ResponseUserDto> getAllOfUsers() {
        return listMapper.mapList(userService.getAllOfUsers(), ResponseUserDto.class, modelMapper);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseUserDto createUser(@RequestBody @Valid CreateUserDto newUser) {
        return modelMapper.map(userService.createUser(newUser), ResponseUserDto.class);
    }

    @GetMapping("/{id}")
    public ResponseUserDto getUserById(@PathVariable Integer id) {
        return modelMapper.map(userService.getUserById(id), ResponseUserDto.class);
    }

    @PutMapping("/{id}")
    public ResponseUserDto updateUser(@RequestBody @Valid UpdateUserDto newUser, @PathVariable Integer id) {
        return modelMapper.map(userService.updateUser(newUser, id), ResponseUserDto.class);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "Delete id : " + id + " successfully";
    }

    @PostMapping("/match")
    @ResponseStatus(HttpStatus.OK)
    public String matchPassword(@RequestBody UsernamePasswordDto usernamePasswordDto) {
        return userService.matchPassword(usernamePasswordDto);
    }

}
