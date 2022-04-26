package ch.usi.si.bsc.sa4.devinecodemy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserStatisticsDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.service.StatisticsService;

import java.util.ArrayList;
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
     */
    @GetMapping
    public ResponseEntity<List<UserStatisticsDTO>> getAll() {
        List<UserStatisticsDTO> allData = new ArrayList<UserStatisticsDTO>();

        allData = statisticsService.getAll().stream().map((stat)->stat.toUserStatisticsDTO()).collect(Collectors.toList());

        return ResponseEntity.ok(allData);
    }

    @GetMapping("/{id}")
    public Optional<UserStatistics> getById(@PathVariable String id) {
        return statisticsService.getById(id);
    }

    //TODO GET /username?levelname - all data for a specific user, for a specific level



}
