package ch.usi.si.bsc.sa4.devinecodemy.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;
import ch.usi.si.bsc.sa4.devinecodemy.model.LevelValidation.LevelValidation;
import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.LevelStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.UserStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.repository.LevelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        ArrayList<Level> playableLevels = new ArrayList<>();
        Optional<UserStatistics> stats = statisticsService.getById(userId);
        List<Level> allLevels = getAll();
        if (stats.isEmpty()) {
            throw new IllegalArgumentException("Statistics for user ID do not exist");
        }

        UserStatistics statistics = stats.get();
        for (Level level : allLevels) {
            LevelStatistics actualLevel = statistics.getData().get(level.getId());
            if (actualLevel != null) {
                playableLevels.add(level);
            }
        }


        
        return Pair.of(playableLevels, allLevels.size());
    }



    /**
     * Returns all levels in the game.
     * @return List containing all the levels in the game.
     */
    private List<Level> getAll() {
        return levelRepository.findAll();
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
    public Optional<Level> getByIdIfPlayable(int levelNumber, String userId) {
        Optional<Level> l = getByLevelNumber(levelNumber);
        if (l.isEmpty())
            return Optional.empty();
        
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
