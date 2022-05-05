package ch.usi.si.bsc.sa4.devinecodemy.service;
import ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions.LevelInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions.UserInexistentException;
import ch.usi.si.bsc.sa4.devinecodemy.model.Exceptions.UserNotAllowedException;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserService userService;
    private LevelRepository levelRepository;
    private StatisticsService statisticsService;

    @Autowired
    public LevelService(LevelRepository levelRepository, StatisticsService statisticsService, UserService userService) {
        this.levelRepository = levelRepository;
        this.statisticsService = statisticsService;
        this.userService = userService;
    }


    /**
     * Simulates a gameplay on a specific level.
     * @param levelNumber the level number.
     * @param userId the ID of the user that is playing the level. Used or saving game statistics.
     * @param commands the list of commands to play on the level.
     * @return a LevelValidationDTO object containing the result of the gameplay.
     * @throws LevelInexistentException if the level does not exist.
     * @throws UserInexistentException if the user with the given userId does not exist.
     * @throws UserNotAllowedException if the user is not allowed to play this level.
     */
    public LevelValidation playLevel(int levelNumber, String userId, List<String> commands) throws LevelInexistentException, UserInexistentException, UserNotAllowedException {
        Optional<Level> optionalLevel = getByLevelNumber(levelNumber);
        if(optionalLevel.isEmpty()) {
            throw new LevelInexistentException(levelNumber);
        } else if(!userService.userIdExists(userId)) {
            throw new UserInexistentException(userId);
        } else if(!isLevelPlayable(levelNumber, userId)) {
            throw new UserNotAllowedException("Level #"+levelNumber+" is not playable by user '"+userId+"' !");
        }

        GamePlayer gameplayer = new GamePlayer(optionalLevel.get());

        LevelValidation validation = gameplayer.play(commands);

        // Here we create the new statistics for the user after playing the game.
        statisticsService.addStats(userId, gameplayer, validation);

        return validation;
    }





    /**
     * Returns all the Levels playable by a user.
     * @param userId the User ID string.
     * @return a List of all playable game Levels.
     * @throws UserInexistentException if the user with userId doesn't exist.
     */
    public List<Level> getAllPlayableLevels(String userId) throws UserInexistentException {
        if(!userService.userIdExists(userId)) {
            throw new UserInexistentException(userId);
        }
        
        Optional<UserStatistics> stats = statisticsService.getById(userId);
        // If there no stats yet for this user, create empty statistics for the user in the db.
        if (stats.isEmpty()) {
            statisticsService.addStats(userId);
        }
        
        UserStatistics statistics = stats.get();
        int max = 0;
        // Finds the highest levelNumber among the completed levels.
        for (Integer key : statistics.getData().keySet()) {
            if (statistics.getData().get(key).isCompleted() && key > max) {
                max = key;
            }
        }

        return getRange(1, max+1);
    }



    /**
     * Returns all levels in the game.
     * @return List containing all the levels in the game.
     */
    public List<Level> getAll() {
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
     * Returns a level with a specific levelNumber if playable for the given user
     * @param levelNumber the levelNumber of the level to look for
     * @param userId the userID of the user to match
     * @return The level with the given levelNumber
     * @throws LevelInexistentException if level with specified number is not found.
     * @throws UserInexistentException if the user does not exist.
     */
    public Optional<Level> getByLevelNumberIfPlayable(int levelNumber, String userId) throws LevelInexistentException, UserInexistentException {
        Optional<Level> l = getByLevelNumber(levelNumber);
        if (l.isEmpty()) {
            throw new LevelInexistentException(levelNumber);
        } else if(!userService.userIdExists(userId)) {
            throw new UserInexistentException(userId);
        }

        if(isLevelPlayable(levelNumber, userId)) {
            return Optional.of(getByLevelNumber(levelNumber).get());
        }
        return Optional.empty();
    }
    
    /**
     * Returns whether a level is playable by a user.
     * @param levelNumber the number of the level.
     * @param userId the ID of the user.
     * @return whether the level is playable by a user.
     * @throws LevelInexistentException if the level does not exist.
     * @throws UserInexistentException if the user does not exist.
     */
    public boolean isLevelPlayable(int levelNumber, String userId) throws LevelInexistentException, UserInexistentException {
        if(!levelExists(levelNumber)) {
            throw new LevelInexistentException(levelNumber);
        } else if(!userService.userIdExists(userId)) {
            throw new UserInexistentException(userId);
        }
    
        for (Level lev : getAllPlayableLevels(userId)) {
            if (lev.getLevelNumber() == levelNumber) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns whether a level exists given the levelNumber.
     * @param levelNumber the number of the level.
     * @return whether a level exists given the levelNumber.
     */
    public boolean levelExists(int levelNumber) {
        return levelRepository.existsByLevelNumber(levelNumber);
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
     * @throws LevelInexistentException if the level with the given levelNumber does not exist.
     * @param levelNumber the number of the level to delete.
     */
    public void deleteByLevelNumber(int levelNumber) throws LevelInexistentException {
        if(!levelExists(levelNumber)) {
            throw new LevelInexistentException(levelNumber);
        }
        
        levelRepository.deleteByLevelNumber(levelNumber);
    }
}
