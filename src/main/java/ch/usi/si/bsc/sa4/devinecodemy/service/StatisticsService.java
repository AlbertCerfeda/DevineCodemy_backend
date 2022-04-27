package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.LevelValidation.LevelValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;

import java.util.List;
import java.util.Optional;

/**
 * Provides all operations relating user statistics.
 */
@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository) {this.statisticsRepository = statisticsRepository;}

    /**
     * Returns a UserStatistics for a single user, with a specific ID.
     * @param id the id of the user statistics to look for.
     * @return an Optional containing the UserStatistics if there exists one with the provided ID.
     */

    public Optional<UserStatistics> getById(String id) {return statisticsRepository.findById(id);}


    /**
     * Creates an entry in the statistics database for a specified user
     *
     * @param userId the user whose statistics we want to keep track of.
     * @param game the game from which to retrieve the statistics.
     *
     */
    public UserStatistics addStats(String userId, GamePlayer game, LevelValidation levelValidation) {
        Optional<UserStatistics> userStats = statisticsRepository.findById(userId);
        UserStatistics stats;
        if(userStats.isPresent()) {
            stats = userStats.get();
        } else {
            stats = new UserStatistics(userId);
        }

        if (game != null) {
            stats.addData(game, levelValidation);
        }
        
        return statisticsRepository.save(stats);
    }

    /**
     * Returns all statistics for all users.
     *
     * @return List containing the statistics for every user.
     */
    public List<UserStatistics> getAll() {return statisticsRepository.findAll();}

}