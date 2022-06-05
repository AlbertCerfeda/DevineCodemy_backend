package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.PlayLevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;


/**
 * Controller that enables the user to play a level.
 */
@RestController
@RequestMapping("/play")
public class PlayController {

    private final LevelService levelService;
    private final UserService userService;

    /**
     * Instantiates the PlayController object by autowiring the dependencies.
     * @param levelService the LevelService.
     * @param userService the UserService.
     */
    @Autowired
    public PlayController(LevelService levelService, UserService userService){
        this.levelService = levelService;
        this.userService = userService;
    }
    
    /**
     * PUT /play/
     * @param authenticationToken the users auth token.
     * @param playLevelDTO the needed parameters passed in the body.
     * @return a LevelValidationDTO containing the results of the play.
     */
    @PutMapping()
    @ResponseBody
    public ResponseEntity<LevelValidationDTO> play(OAuth2AuthenticationToken authenticationToken, @RequestBody PlayLevelDTO playLevelDTO) {
        final User user = userService.getUserByToken(authenticationToken);
        final LevelValidation playedLevel = levelService.playLevel(playLevelDTO.getLevelNumber(), user.getId(), playLevelDTO.getProgram(), playLevelDTO.getAttempt());
        return ResponseEntity.ok(playedLevel.toLevelValidationDTO());
    }

}










