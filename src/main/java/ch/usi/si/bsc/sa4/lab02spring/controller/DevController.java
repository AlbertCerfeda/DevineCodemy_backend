package ch.usi.si.bsc.sa4.lab02spring.controller;

import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.service.LevelService;
import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Router for developer requests
 * Classes affected: User, UserDTO, Level, LevelDTO
 */
@RestController
@RequestMapping("/dev")
public class DevController {
    private final UserService userService;
    private final LevelService levelService;

    @Autowired
    public DevController(UserService userService, LevelService levelService) {
        this.userService = userService;
        this.levelService = levelService;
    }

    /**
     * DELETE /users/:id
     * Deletes from the database the user with an id if it exists.
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Optional<User> optionalUser = userService.getById(id);
        if (optionalUser.isPresent()) {
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted successfully.",
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User with this id does not exist.",
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE /levels/{id}
     * Deletes the level with the give id, if it exists
     */
    @DeleteMapping("/levels/{id}")
    public ResponseEntity<String> deleteLevel(@PathVariable String id){
        Optional<Level> optionalLevel = levelService.getById(id);
        if(optionalLevel.isPresent()){
            levelService.deleteLevelById(id);
            return new ResponseEntity<>("Level deleted successfully.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Level with the given id does not exist.",HttpStatus.BAD_REQUEST);
        }
    }

}
