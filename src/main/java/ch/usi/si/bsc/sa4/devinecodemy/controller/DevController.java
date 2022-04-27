package ch.usi.si.bsc.sa4.devinecodemy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;

import java.util.Optional;

/**
 * Router for developer requests
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
     * DELETE /levels/{levelNumber}
     * Deletes the level with the given level number, if it exists
     */
    @DeleteMapping("/levels/{levelNumber}")
    public ResponseEntity<String> deleteLevel(@PathVariable int levelNumber){
        Optional<Level> optionalLevel = levelService.getByLevelNumber(levelNumber);
        if(optionalLevel.isPresent()){
            levelService.deleteByLevelNumber(levelNumber);
            return new ResponseEntity<>("Level deleted successfully.", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Level with the given id does not exist.",HttpStatus.BAD_REQUEST);
        }
    }

}
