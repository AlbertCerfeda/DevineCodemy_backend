package ch.usi.si.bsc.sa4.lab02spring.controller;


import ch.usi.si.bsc.sa4.lab02spring.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.model.User.User;
import ch.usi.si.bsc.sa4.lab02spring.service.LevelService;

import java.util.Objects;
import java.util.Optional;

import ch.usi.si.bsc.sa4.lab02spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Request router for /levels
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
        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);
        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(levelService.getAllPlayableLevels(optionalUser.get().getId()).getFirst().stream().map(Level::toLevelDTO).collect(Collectors.toList()));
    }
    
    /**
     * GET /levels/{id}
     * Gets the level with the specific id
     */
    @GetMapping("/{id}")
    public ResponseEntity<LevelDTO> getById(OAuth2AuthenticationToken authenticationToken, @PathVariable("id") String id) {
        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        
        String userId = optionalUser.get().getId();
        Optional<Level> level = levelService.getByIdIfPlayable(id,userId);
        return level.isPresent() ? ResponseEntity.ok(level.get().toLevelDTO()) : ResponseEntity.status(405).build();
    }
    
    /**
     * GET /levels/search?name={name}
     * Gets the level with the specific name if playable
     */
    @GetMapping("/search")
    public ResponseEntity<?> getByName(OAuth2AuthenticationToken authenticationToken, @RequestParam("name") String name){
        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }

        String userId = optionalUser.get().getId();
        Optional<Level> optionalLevel = levelService.getByName(name);
        if (optionalLevel.isPresent()){
            optionalLevel = levelService.getByIdIfPlayable(optionalLevel.get().getId(), userId);
            if (optionalLevel.isEmpty()) {
                return new ResponseEntity<>("This profile is private.", HttpStatus.UNAUTHORIZED);
            }
            return ResponseEntity.ok(optionalLevel.get().toLevelDTO());
        } else {
            return ResponseEntity.notFound().build();
        }
        
    }
    
}
