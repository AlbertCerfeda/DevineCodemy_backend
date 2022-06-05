package ch.usi.si.bsc.sa4.devinecodemy.controller;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.LBUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserStatisticsDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.service.StatisticsService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Request router for /stats
 */
@RestController
@RequestMapping("/stats")
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final UserService userService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, UserService userService){
        this.userService = userService;
        this.statisticsService = statisticsService;
    }

    /**
     * GET  /stats
     * @return returns all the stats as DTOs.
     */
    @GetMapping
    public ResponseEntity<List<UserStatisticsDTO>> getAll() {
        return ResponseEntity.ok(statisticsService.getAll().stream().map(UserStatistics::toUserStatisticsDTO).collect(Collectors.toList()));
    }
    
    /**
     * GET  /stats/leaderboard
     * @return returns all the stats as DTOs.
     */
    @GetMapping("/leaderboard")
    public ResponseEntity<List<LBUserDTO>> getLBUsers() {
        return ResponseEntity.ok(statisticsService.sortedLeaderboardUsers());
    }


    /**
     * GET /stats/{id}
     * @param id id of the user of stat to retrieve.
     * @return an Optional<UserStatistics> containing the UserStatistics if present.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserStatisticsDTO> getById(@PathVariable String id) {
        return ResponseEntity.ok(statisticsService.addStats(id).toUserStatisticsDTO());
    }


    /**
     * GET /stats/level/{levelNumber}?attemptNumber=
     * @param levelNumber the level for which to retrieve the attempt.
     * @param attemptNumber the number of the attempt to retrieve, for -1 retrieves the last one.
     * @return an Optional<UserStatistics> containing the UserStatistics if present.
     * @throws StatisticInexistentException if the statistics for the level and attempt are not present.
     */
    @GetMapping("/level/{levelNumber}")
    public ResponseEntity<String> getAttempt(OAuth2AuthenticationToken authenticationToken, @PathVariable("levelNumber") int levelNumber,@RequestParam(name = "attemptNumber", defaultValue = "-1") Integer attemptNumber){

        try {
            User user = userService.getUserByToken(authenticationToken);
            String userId = user.getId();

            String attempt = statisticsService.getAttempt(userId,levelNumber,attemptNumber);
            return ResponseEntity.ok(attempt);

        } catch (StatisticInexistentException | UserInexistentException e) {
            return ResponseEntity.status(404).build();
        }
    }
}
