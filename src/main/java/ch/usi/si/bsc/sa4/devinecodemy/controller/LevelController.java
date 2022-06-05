package ch.usi.si.bsc.sa4.devinecodemy.controller;


import java.util.ArrayList;
import java.util.Optional;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EWorldDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.LevelInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
     * GET /levels?onlyinfo=.
     * Gets all Levels available to the user, subdivided in playable and unplayable levels.
     * The unplayable levels contain only the level infos.
     *
     * @param authenticationToken Token from GitLab after the Log-in.
     * @param onlyinfo            Boolean query parameter that indicates whether the playable levels should include only their essential infos.
     */

    @GetMapping
    public ResponseEntity<Pair<List<LevelDTO>, List<LevelDTO>>> getPlayableAndUnplayableLevels(OAuth2AuthenticationToken authenticationToken, @RequestParam(name = "onlyinfo", required = false, defaultValue = "false") boolean onlyinfo) {
        try {
            User user = userService.getUserByToken(authenticationToken);
            List<Level> playableLevels = levelService.getAllPlayableLevels(user.getId());
            List<Level> unplayableLevels = new ArrayList<>(levelService.getAll());
            unplayableLevels.removeAll(playableLevels);
            return ResponseEntity.ok(Pair.of(onlyinfo ? playableLevels.stream().map(Level::toLevelInfoDTO).collect(Collectors.toList()) :
                            playableLevels.stream().map(Level::toLevelDTO).collect(Collectors.toList()),
                    // Unplayable levels just contain the level infos
                    unplayableLevels.stream().map(Level::toLevelInfoDTO).collect(Collectors.toList())));

        } catch (UserInexistentException e) {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * GET /levels/{levelNumber}?onlyinfo=
     * If the level is not playable, returns just the level info.
     *
     * @param authenticationToken Token from GitLab after the Log-in.
     * @param onlyinfo            Boolean query parameter that indicates whether the Level should only include his essential info.
     * @return the level with the specific levelNumber
     */
    @GetMapping("/{levelNumber}")
    public ResponseEntity<LevelDTO> getByLevelNumber(OAuth2AuthenticationToken authenticationToken, @PathVariable("levelNumber") int levelNumber, @RequestParam(name = "onlyinfo", required = false, defaultValue = "false") boolean onlyinfo) {
        try {
            User user = userService.getUserByToken(authenticationToken);

            String userId = user.getId();
            Optional<Level> level = levelService.getByLevelNumberIfPlayable(levelNumber, userId);

            if (level.isPresent()) {
                return ResponseEntity.ok(onlyinfo ? level.get().toLevelInfoDTO() : level.get().toLevelDTO());
            }
            // If the level is not playable, returns just the Level info
            return ResponseEntity.ok(levelService.getByLevelNumber(levelNumber).get().toLevelInfoDTO());

        } catch (UserInexistentException | LevelInexistentException e) {
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * GET /levels/worlds.
     *
     * @param authenticationToken Token from GitLab after the Log-in.
     * @return a list containing all Level Worlds and their descriptions.
     */
    @GetMapping("/worlds")
    public ResponseEntity<List<EWorldDTO>> getLevelWorlds(OAuth2AuthenticationToken authenticationToken){
        return ResponseEntity.ok(Stream.of(EWorld.values())
                .map(world-> world.toEWorldDTO(levelService.getLevelNumberRangeForWorld(world)))
                .collect(Collectors.toList()));
    }

    /**
     * GET /levels/worlds/{worldName}.
     * @param authenticationToken Token from GitLab after the Log-in.
     * @param worldName name of the world to retrieve.
     * @return the world with the specified name.
     */
    @GetMapping("/worlds/{worldName}")
    public ResponseEntity<EWorldDTO> getWorld(OAuth2AuthenticationToken authenticationToken, @PathVariable("worldName") String worldName){
        try{
           EWorld world = EWorld.getEWorldFromString(worldName);
           return ResponseEntity.ok(world.toEWorldDTO(levelService.getLevelNumberRangeForWorld(world)));
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(404).build();
        }

    }

}
