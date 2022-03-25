package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *  Request router for  /users
 */
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * GET  /users
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        ArrayList<UserDTO> allUserDTOs = new ArrayList<UserDTO>();

        for (User user : userService.getAllPublic()) {
                allUserDTOs.add(user.toUserDTO());
        }

        return ResponseEntity.ok(allUserDTOs);
    }
    
    /**
     * POST /users
     */
    @PostMapping
    public ResponseEntity<UserDTO> addUser(@RequestBody CreateUserDTO createUserDTO) {
        User savedUser = userService.createUser(createUserDTO);
        return ResponseEntity.ok(savedUser.toUserDTO());
    }
    
    /**
     * GET /users/search?name=string
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getByNameContaining(@RequestParam("name") String name) {
        // Same as in getAll() but with functional approach
        List<UserDTO> allUserDTOs = userService.searchByNameContainingAndPublicProfileTrue(name).stream()
                .map(User::toUserDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(allUserDTOs);
    }
    
    /**
     * GET /users/:id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isPresent()) {
            //TODO: Check if request is sent by the authenticated user itself

            if (optionalUser.get().isProfilePublic()) {
                return ResponseEntity.ok(optionalUser.get().toUserDTO());
            } else {
                return new ResponseEntity<>("This profile is private.", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
