package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.PlayLevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.LevelValidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.User.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for playing the level with the input commands
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/play")
public class PlayController {

    private final LevelService levelService;
    private final UserService userService;


    @Autowired
    public PlayController(LevelService levelService, UserService userService){
        this.levelService = levelService;
        this.userService = userService;
    }

    @PutMapping()
    @ResponseBody
    public ResponseEntity<LevelValidationDTO>play(OAuth2AuthenticationToken authenticationToken, @RequestBody PlayLevelDTO playLevelDTO) { // Fix how parameters are passed
        User user = userService.getUserByToken(authenticationToken);
        LevelValidation playedLevel = levelService.playLevel(playLevelDTO.getLevelNumber(), user.getId(), playLevelDTO.getCommands());

        return ResponseEntity.ok(playedLevel.toLevelValidationDTO());

    }

}










