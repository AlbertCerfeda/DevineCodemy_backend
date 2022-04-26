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
     * @param levelId the level ID string.
     * @param userId the ID of the user that is playing the level. Used or saving game statistics.
     * @param commands the list of commands to play on the level.
     * @return a LevelValidationDTO object containing the result of the gameplay.
     * @throws IllegalArgumentException if the levelId is not valid.
     */
    public LevelValidation playLevel(String levelId, String userId, List<String> commands) throws IllegalArgumentException {
        Optional<Level> optionalLevel = getById(levelId);
        if(optionalLevel.isEmpty())
            throw new IllegalArgumentException("Level does not exist");

        GamePlayer gameplayer = new GamePlayer(optionalLevel.get());

        LevelValidation validation = gameplayer.play(commands);

        // Here we create the new statistics for the user after playing the game.
        statisticsService.addStats(userId, gameplayer);

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
        
        /*
            TODO: Wrong. Needs to add the next unplayed level aswell.
                Otherwise, we wont ever be able to play even the first level.
        */
        
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
     * Returns a level with a specific ID only if playable for the given user
     * @param levelId the levelId of the level to look for
     * @param userId the userID of the user to match
     * @return The level with the given levelId
     */
    public Optional<Level> getByIdIfPlayable(String levelId, String userId) {
        Optional<Level> l = getById(levelId);
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
     * Returns a Level with a specific ID.
     * @param levelId the level_id of the level to look for.
     * @return an Optional containing the Level if there is one with the provided ID.
     */
    public Optional<Level> getById(String levelId) {
        return levelRepository.findById(levelId);
    }



    /**
     * Returns a Level with a specific name.
     * @param levelName the level_name of the level to look for.
     * @return an Optional containing the Level if there is one with the provided name.
     */
    public Optional<Level> getByName(String levelName) {return levelRepository.findByNameContaining(levelName);}

    /**
     * Deletes a Level with a specific ID.
     * @param levelId the level_id of the level to delete.
     */
    public void deleteLevelById(String levelId){levelRepository.deleteById(levelId);}
}
