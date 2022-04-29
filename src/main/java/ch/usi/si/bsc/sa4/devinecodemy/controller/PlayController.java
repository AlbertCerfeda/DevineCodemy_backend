package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.PlayLevelDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.LevelValidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller for playing the level with the input commands
 */
@RestController
@RequestMapping("/play")
public class PlayController {

    private final LevelService levelService;

    @Autowired
    public PlayController(LevelService levelService){this.levelService = levelService;}

    public ResponseEntity<LevelValidationDTO>play(@RequestBody PlayLevelDTO playLevelDTO){ // Fix how parameters are passed

        LevelValidation playedLevel = levelService.playLevel(playLevelDTO.getLevelNumber(), playLevelDTO.getUserId(), playLevelDTO.getCommands());

        return ResponseEntity.ok(playedLevel.toLevelValidationDTO());

    }

}










