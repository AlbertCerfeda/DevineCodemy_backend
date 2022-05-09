package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;

import java.util.List;
import java.util.Optional;

/**
 * The StatisticsService class provides all operations relating user statistics.
 */
@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;

    /**
     * Constructs a StatisticService by autowiring the dependencies.
     * @param statisticsRepository the Repository of the statistics.
     */
    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository) {
        this.statisticsRepository = statisticsRepository;
    }

    /**
     * Returns a UserStatistics for a single user, with a specific ID.
     *
     * @param id the id of the user statistics to look for.
     * @return an Optional containing the UserStatistics
     * if there exists one with the provided ID.
     */
    public Optional<UserStatistics> getById(String id) {
        return statisticsRepository.findById(id);
    }


    /**
     * Updates the statistics of a user.
     *
     * @param userId          the user whose statistics we want to keep track of.
     * @param game            the game from which to retrieve the statistics.
     * @param levelValidation the result of the validation of the game
     */
    public void addStats(String userId, GamePlayer game, LevelValidation levelValidation) {
        final Optional<UserStatistics> userStats = statisticsRepository.findById(userId);
        final UserStatistics stats = userStats.orElse(new UserStatistics(userId));

        if (game != null) {
            stats.addData(game, levelValidation);
        }

        statisticsRepository.save(stats);
    }

    /**
     * Creates an empty entry in the statistics database for a specified user.
     * Returns new or existing statistics of they exist already.
     *
     * @param userId the user whose statistics we want to keep track of.
     * @return the saved statistic in the database
     */
    public UserStatistics addStats(String userId) {
        final Optional<UserStatistics> userStats = statisticsRepository.findById(userId);
        if (userStats.isEmpty()) {
            return statisticsRepository.save(new UserStatistics(userId));
        }

        return userStats.get();
    }

    /**
     * Retrieves an attempt from a played level
     *
     * @param userId the user whose attempt we want to get
     * @param levelNumber level for which to retrieve the attempt
     * @param attemptNumber the number of the attempt to retrieve
     * @return the list of actions used in the attempt
     * @throws StatisticInexistentException if the user does not have any statistics for the level
     */
    public List<EAction> getAttempt(String userId, int levelNumber, int attemptNumber) throws StatisticInexistentException{
        final Optional<UserStatistics> userStats = statisticsRepository.findById(userId);
        if (userStats.isEmpty()) {
            throw new StatisticInexistentException();
        }
        if(attemptNumber == -1){
            return userStats.get().getLastAttemptFromLevel(levelNumber);
        }
        return userStats.get().getAttemptFromLevel(levelNumber,attemptNumber);
    }

    /**
     * Returns all statistics for all users.
     *
     * @return List containing the statistics for every user.
     */
    public List<UserStatistics> getAll() {
        return statisticsRepository.findAll();
    }


}