package ch.usi.si.bsc.sa4.lab02spring.controller;


import ch.usi.si.bsc.sa4.lab02spring.controller.dto.LevelDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.service.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Request router for /levels
 */
@RestController
@RequestMapping("/levels")
public class LevelController {
    private final LevelService levelService;

    @Autowired
    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    /**
     * GET /levels
     */
    @GetMapping
    public ResponseEntity<List<LevelDTO>> getAll() {
        ArrayList<LevelDTO> allLevelDTOs = new ArrayList<LevelDTO>();

        for(Level level : levelService.getAll()) {
            allLevelDTOs.add(level.toLevelDTO());
        }

        return ResponseEntity.ok(allLevelDTOs);
    }

}
