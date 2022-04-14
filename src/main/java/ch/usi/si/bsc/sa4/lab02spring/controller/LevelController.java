package ch.usi.si.bsc.sa4.lab02spring.controller;


import ch.usi.si.bsc.sa4.lab02spring.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.service.LevelService;
import java.util.Optional;

import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Request router for /levels
 * Classes affected: Level, LevelDTO
 */
@RestController
@RequestMapping("/levels")
public class LevelController {
    private final LevelService levelService;
    private final UserService userService;

    @Autowired
    public LevelController(UserService userService, LevelService levelService) {
        this.levelService = levelService;
        this.userService = userService;
    }

    /**
     * GET /levels
     * Gets all levels available in LevelDTO representation
     */
    @GetMapping
    public ResponseEntity<List<LevelDTO>> getAll(OAuth2AuthenticationToken authenticationToken) {
        ArrayList<LevelDTO> allLevelDTOs = new ArrayList<LevelDTO>();

        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);
        if (optionalUser.isPresent()) {
            // TODO - check which levels can be seen
        }

        for(Level level : levelService.getAll()) {
            allLevelDTOs.add(level.toLevelDTO());
        }

        return ResponseEntity.ok(allLevelDTOs);
    }

    /**
     * GET /levels/{id}
     * Gets the level with the specific id
     */
    @GetMapping("/{id}")
    public ResponseEntity<LevelDTO> getById(OAuth2AuthenticationToken authenticationToken, @PathVariable("id") String id) {
        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);
        if (optionalUser.isPresent()) {
            // TODO - check which levels can be seen
        }
        Optional<Level> optionalLevel = levelService.getById(id);
        return optionalLevel.map(level -> ResponseEntity.ok(level.toLevelDTO()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * GET /levels/search?name={name}
     * Gets the level with the specific name
     */
    @GetMapping("/search")
    public ResponseEntity<LevelDTO> getByName(OAuth2AuthenticationToken authenticationToken, @RequestParam("name") String name){
        Optional<Level> optionalLevel = levelService.getByName(name);
        if (optionalLevel.isPresent()){
            return ResponseEntity.ok(optionalLevel.get().toLevelDTO());
        }else {
            return ResponseEntity.notFound().build();
        }

    }

}
