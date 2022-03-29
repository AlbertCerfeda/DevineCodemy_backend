package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.CreateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UpdateUserDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public ResponseEntity<?> addUser(@RequestBody CreateUserDTO createUserDTO,
                                     @RequestHeader(value = "accept") String accepts) {
        if(createUserDTO.getPassword() != null && createUserDTO.getName() != null) {
            if(userService.userExists(createUserDTO.getName())) {
                return new ResponseEntity<>("Username is already taken.", HttpStatus.BAD_REQUEST);
            } else {
                User savedUser = userService.createUser(createUserDTO);
                if(Objects.equals(accepts, "application/json")) {
                    return ResponseEntity.ok(savedUser.toUserDTO());
                } else if (Objects.equals(accepts, "text/html")) {
                    return ResponseEntity.ok("User created");
                } else {
                    return new ResponseEntity<>("Change accept header", HttpStatus.NOT_ACCEPTABLE);
                }
            }
        } else {
            return new ResponseEntity<>("Both username and password must be inserted.", HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * PUT /users/:id
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id ,@RequestBody UpdateUserDTO updateUserDTO) {
        Optional<User> optionalUser = userService.getById(id);

        if (optionalUser.isPresent()) {
            //TODO: Check if request is sent by the authenticated user itself
            User updatedUser = optionalUser.get();

            if (updateUserDTO.isPublicProfileInitialized()) {
                updatedUser.setPublicProfile(updateUserDTO.isPublicProfile());
            }

            //TODO: other field to be modified or updated

            updatedUser = userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUser.toUserDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * GET /users/search?name=string
     */
    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> getByNameContaining(@RequestParam("name") String name) {
        // Same as in getAll() but with functional approach
        List<UserDTO> allUserDTOs = userService.searchByNameContaining(name, true).stream()
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

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
  
      userService.deleteUserById(id);
  
      return ResponseEntity.ok(id);
    }


}
