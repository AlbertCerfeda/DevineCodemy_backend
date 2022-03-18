package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        var allUserDTOs = new ArrayList<UserDTO>();

        // Same as in getByNameContaining() but with for-each approach
        for (User user : userService.getAll()) {
            allUserDTOs.add(user.toUserDTO());
        }

        return ResponseEntity.ok(allUserDTOs);
    }

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody CreateUserDTO createUserDTO) {
        var savedUser = userService.createUser(createUserDTO);
        return ResponseEntity.ok(savedUser.toUserDTO());
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getByNameContaining(@RequestParam("string") String string) {
        // Same as in getAll() but with functional approach
        var allUserDTOs = userService.searchByNameContaining(string).stream()
                .map(user -> user.toUserDTO())
                .collect(Collectors.toList());
        return ResponseEntity.ok(allUserDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable("id") String id) {
        var optionalUser = userService.getById(id);
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get().toUserDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
