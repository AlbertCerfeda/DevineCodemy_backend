package ch.usi.si.bsc.sa4.devinecodemy.controller;


import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;

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
     * GET /levels?onlyinfo=
     * Gets all Levels available to the user, subdivided in playable and unplayable levels.
     * @param authenticationToken Token from GitLab after the Log-in.
     * @param onlyinfo Boolean query parameter that indicates whether the playable levels should include only their essential infos.
     */
    @GetMapping
    public ResponseEntity<Pair<List<LevelDTO>,List<LevelDTO>>> getPlayableAndUnplayableLevels(OAuth2AuthenticationToken authenticationToken,@RequestParam(name="onlyinfo", required=false, defaultValue="false") boolean onlyinfo) {
        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);
        if(optionalUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        List<Level> playableLevels = levelService.getAllPlayableLevels(optionalUser.get().getId());
        List<Level> unplayableLevels = new ArrayList<>(levelService.getAll());
        unplayableLevels.removeAll(playableLevels);
        return onlyinfo ?
                        ResponseEntity.ok(Pair.of(
                                playableLevels.stream().map(Level::toLevelDTO).collect(Collectors.toList()),
                                unplayableLevels.stream().map(Level::toLevelDTO).collect(Collectors.toList()))):
                        ResponseEntity.ok(Pair.of(
                            playableLevels.stream().map(Level::toLevelDTO).collect(Collectors.toList()),
                            unplayableLevels.stream().map(Level::toLevelDTO).collect(Collectors.toList())));
    }

    /**
     * GET /levels/{levelNumber}?onlyinfo=
     * @param authenticationToken Token from GitLab after the Log-in.
     * @param onlyinfo Boolean query parameter that indicates whether the Level should only include his essential info.
     * @return the level with the specific levelNumber
     */
    @GetMapping("/{levelNumber}")
    public ResponseEntity<LevelDTO> getByLevelNumber(OAuth2AuthenticationToken authenticationToken, @PathVariable("levelNumber") int levelNumber,@RequestParam(name="onlyinfo", required=false, defaultValue="false") boolean onlyinfo) {
        Optional<User> optionalUser = userService.getUserByToken(authenticationToken);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        
        String userId = optionalUser.get().getId();
        Optional<Level> level;
        try {
            level = levelService.getByLevelNumberIfPlayable(levelNumber,userId);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
        return level.isPresent() ?  ResponseEntity.ok(onlyinfo? level.get().toLevelDTO():
                                                                level.get().toLevelDTO()):
                                    ResponseEntity.status(401).build();
    }
}
