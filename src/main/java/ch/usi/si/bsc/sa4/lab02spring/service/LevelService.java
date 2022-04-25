package ch.usi.si.bsc.sa4.lab02spring.service;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
import ch.usi.si.bsc.sa4.lab02spring.model.LevelValidation.LevelValidation;
import ch.usi.si.bsc.sa4.lab02spring.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

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
     * @param level_id the level ID string.
     * @param user_id the ID of the user that is playing the level. Used or saving game statistics.
     * @param commands the list of commands to play on the level.
     * @return a LevelValidationDTO object containing the result of the gameplay.
     * @throws IllegalArgumentException if the level_id is not valid.
     */
    public LevelValidation playLevel(String level_id, String user_id, List<String> commands) throws IllegalArgumentException {
        Optional<Level> optionalLevel = getById(level_id);
        if(optionalLevel.isEmpty())
            throw new IllegalArgumentException("Level does not exist");

        GamePlayer gameplayer = new GamePlayer(optionalLevel.get());

        LevelValidation validation = gameplayer.play(commands);

        // Here we create the new statistics for the user after playing the game.
        statisticsService.addStats(user_id, gameplayer);

        return validation;
    }





    /**
     * Returns all the Levels that are playable by the User.
     * @param user_id the User ID string.
     * @return Pair object with:
     *  - The List of all playable game Levels by the user.
     *  - An Integer representing the number of Levels in the database.
     */
    public Pair<List<Level>,Integer> getAllPlayableLevels(String user_id) {
        // TODO: Implement getAllPlayable that returns only the levels playable by the user based on his statistics
        List<Level> levels = getAll();
        return Pair.of(levels, levels.size());
    }



    /**
     * Returns all levels in the game.
     * @return List containing all the levels in the game
     */
    public List<Level> getAll() {
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
     * Returns a Level with a specific ID.
     * @param level_id the level_id of the level to look for.
     * @return an Optional containing the Level if there is one with the provided ID.
     */
    public Optional<Level> getById(String level_id) {
        return levelRepository.findById(level_id);
    }



    /**
     * Returns a Level with a specific name.
     * @param level_name the level_name of the level to look for.
     * @return an Optional containing the Level if there is one with the provided name.
     */
    public Optional<Level> getByName(String level_name) {return levelRepository.findByNameContaining(level_name);}

    /**
     * Deletes a Level with a specific ID.
     * @param level_id the level_id of the level to delete.
     */
    public void deleteLevelById(String level_id){levelRepository.deleteById(level_id);}
}
