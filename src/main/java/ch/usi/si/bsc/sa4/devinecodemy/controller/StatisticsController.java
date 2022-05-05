package ch.usi.si.bsc.sa4.devinecodemy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserStatisticsDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.service.StatisticsService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Request router for /stats
 */
@RestController
@RequestMapping("/stats")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService){ this.statisticsService = statisticsService;}

    /**
     * GET  /stats
     * @return returns all the stats as DTOs.
     */
    @GetMapping
    public ResponseEntity<List<UserStatisticsDTO>> getAll() {
        List<UserStatisticsDTO> allData;

        allData = statisticsService.getAll().stream().map(UserStatistics::toUserStatisticsDTO).collect(Collectors.toList());

        return ResponseEntity.ok(allData);
    }

    /**
     * GET /stats/{id}
     * @param id id of the user of stat to retrieve.
     * @return an Optional<UserStatistics> containing the UserStatistics if present.
     */
    @GetMapping("/{id}")
    public Optional<UserStatistics> getById(@PathVariable String id) {
        return statisticsService.getById(id);
    }

    //TODO GET /username?levelname - all data for a specific user, for a specific level



}
