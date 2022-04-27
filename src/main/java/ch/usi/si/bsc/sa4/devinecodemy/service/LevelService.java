package ch.usi.si.bsc.sa4.devinecodemy.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.LevelValidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.repository.LevelRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Provides all operations relating game levels.
 */
@Service
public class LevelService {
    private LevelRepository levelRepository;
    private StatisticsService statisticsService;

    @Autowired
    public LevelService(LevelRepository levelRepository, StatisticsService statisticsService) {
        this.levelRepository = levelRepository;
        this.statisticsService = statisticsService;
    }


    /**
     * Simulates a gameplay on a specific level.
     * @param levelNumber the level number.
     * @param userId the ID of the user that is playing the level. Used or saving game statistics.
     * @param commands the list of commands to play on the level.
     * @return a LevelValidationDTO object containing the result of the gameplay.
     * @throws IllegalArgumentException if the levelNumber is not valid.
     */
    public LevelValidation playLevel(int levelNumber, String userId, List<String> commands) throws IllegalArgumentException {
        Optional<Level> optionalLevel = getByLevelNumber(levelNumber);
        if(optionalLevel.isEmpty())
            throw new IllegalArgumentException("Level does not exist");

        GamePlayer gameplayer = new GamePlayer(optionalLevel.get());

        LevelValidation validation = gameplayer.play(commands);

        // Here we create the new statistics for the user after playing the game.
        statisticsService.addStats(userId, gameplayer, validation);

        return validation;
    }





    /**
     * Returns all the Levels that are playable by the User.
     * @param userId the User ID string.
     * @return Pair object with:
     *  - The List of all playable game Levels by the user.
     *  - An Integer representing the number of Levels in the database.
     * @throws IllegalArgumentException if the user with userId doesn't exist.
     */
    public Pair<List<Level>,Integer> getAllPlayableLevels(String userId) throws IllegalArgumentException{
        Optional<UserStatistics> stats = statisticsService.getById(userId);
        if (stats.isEmpty()) {
            throw new IllegalArgumentException("Statistics for user ID do not exist");
        }

        int max = 1;
        UserStatistics statistics = stats.get();
        List<Integer> keys = statistics.getData().keySet().stream().collect(Collectors.toList());

        for (Integer key : keys) {
            if (key > max) {
                max = key;
            }
        }

        if (keys.size() > 0 && statistics.getData().get(max).isCompleted()) {
           if (getByLevelNumber(max + 1).isPresent()) {
               max = max + 1;
           }
        }

        int size = getAll().size();
        return Pair.of(getRange(1, max), size);
    }



    /**
     * Returns all levels in the game.
     * @return List containing all the levels in the game.
     */
    private List<Level> getAll() {
        return levelRepository.findAll();
    }
    
    /**
     * Returns a list of Levels whose levelNumber lies in between the provided range.
     * @param start the lower bound of the range.
     * @param end the upper bound of the range.
     * @throws IllegalArgumentException if the provided range is not valid.
     * @return list of Levels whose levelNumber lies in between the provided range.
     */
    private List<Level> getRange(int start, int end) throws IllegalArgumentException {
        if(start > end) {
            throw new IllegalArgumentException("Parameter 'start' needs to be less or equal to 'end'");
        }
        
        return getAll().stream().filter((Level l)->l.getLevelNumber() >= start && l.getLevelNumber() <= end).collect(Collectors.toList());
    }

    /**
     * Returns all level info in the game. The returned levels do not contain data like the game board.
     *  Useful for having a more lightweight message when displaying just the level info.
     * @return List containing all the level infos in the game.
     */
    public List<Level> getAllInfo() {
        return levelRepository.findAllInfo();
    }

    /**
     * Returns a level with a specific levelNumber if playable for the given user
     * @param levelNumber the levelNumber of the level to look for
     * @param userId the userID of the user to match
     * @return The level with the given levelNumber
     */
    public Optional<Level> getByLevelNumberIfPlayable(int levelNumber, String userId) {
        Optional<Level> l = getByLevelNumber(levelNumber);
        if (l.isEmpty()) {
            return Optional.empty();
        }

        Level level = l.get();
        for (Level lev : getAllPlayableLevels(userId).getFirst()) {
            if (lev.equals(level)) {
                return Optional.of(level);
            }
        }
        return Optional.empty();
    }

    /**
     * Returns a Level with a specific level number.
     * @param levelNumber the level number of the level to look for.
     * @return an Optional containing the Level if there is one with the provided level number.
     */
    public Optional<Level> getByLevelNumber(int levelNumber) {
        return levelRepository.findByLevelNumber(levelNumber);
    }
    
    
    /**
     * Deletes a Level with a specific level number.
     * @param levelNumber the number of the level to delete.
     */
    public void deleteByLevelNumber(int levelNumber){levelRepository.deleteByLevelNumber(levelNumber);}
}
