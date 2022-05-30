package ch.usi.si.bsc.sa4.devinecodemy.service;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.user.LBUserDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.StatisticInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.user.User;
import ch.usi.si.bsc.sa4.devinecodemy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.repository.StatisticsRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The StatisticsService class provides all operations relating user statistics.
 */
@Service
public class StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final UserRepository userRepository;
    
    /**
     * Constructs a StatisticService by autowiring the dependencies.
     * @param statisticsRepository the Repository of the statistics.
     * @param userRepository the Repository of the users.
     */
    @Autowired
    public StatisticsService(StatisticsRepository statisticsRepository, UserRepository userRepository) {
        this.statisticsRepository = statisticsRepository;
        this.userRepository = userRepository;
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
     * Updates the statistics of a user with a new attempt.
     * @param userId the id of the user statistics to look for.
     * @param levelNumber the level number of the level that was attempted.
     * @param attempt the attempt that was made.
     * @param isLevelCompleted whether the level was completed.
     * @return the updated statistics.
     */
    public UserStatistics addStats(String userId, int levelNumber, String attempt, boolean isLevelCompleted) {
        final Optional<UserStatistics> userStats = statisticsRepository.findById(userId);
        final UserStatistics stats = userStats.orElse(new UserStatistics(userId));

        if (attempt != null) {
            stats.addData(levelNumber, attempt, isLevelCompleted);
        }

        return statisticsRepository.save(stats);
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
     * Retrieves an attempt from a played level.
     * @param userId the user whose attempt we want to get.
     * @param levelNumber level for which to retrieve the attempt.
     * @param attemptNumber the number of the attempt to retrieve.
     *                      if -1 returns the last attempt.
     * @return the requested attempt.
     * @throws StatisticInexistentException if there is no statistic for the user
     *                                      or the level.
     */
    public String getAttempt(String userId, int levelNumber, int attemptNumber) throws StatisticInexistentException{
        final Optional<UserStatistics> userStats = statisticsRepository.findById(userId);
        if (userStats.isEmpty()) {
            throw new StatisticInexistentException();
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
    
    /**
     * Returns the number of completed levels by a certain user.
     * @param u the user.
     * @return the number of completed levels by the user.
     */
    public int getCompletedLevels(User u) {
        Optional<UserStatistics> us = getById(u.getId());
        if(us.isPresent()) {
            UserStatistics uss = us.get();
            return uss.getData().size();
        } else {
            return 0;
        }
    }
    
    /**
     * Returns the unsorted leaderboard.
     * @return the unsorted leaderboard.
     */
    public List<LBUserDTO> getLeaderboardUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map((User u)-> u.toLBUserDTO(getCompletedLevels(u))).collect(Collectors.toList());
    }
    
    /**
     * Returns the leaderboard, sorted by number of completed levels.
     * @return the leaderboard sorted by number of completed levels.
     */
    public List<LBUserDTO> sortedLeaderboardUsers() {
        List<LBUserDTO> unorderedList = getLeaderboardUsers();
        return unorderedList.stream()
                .sorted(Comparator.comparing(LBUserDTO::getCompletedLevels).reversed())
                .collect(Collectors.toList());
    }
}