package ch.usi.si.bsc.sa4.lab02spring.service;
import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;
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
    private static LevelRepository levelRepository;
    
    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }
    
    /**
     * Returns all the Levels that are playable by the User.
     * @param user_id the User ID string.
     * @return Pair object with:
     *  - The List of all playable game Levels by the user.
     *  - An Integer representing the number of Levels in the database.
     */
    public static Pair<List<Level>,Integer> getAllPlayableLevels(String user_id) {
        // TODO: Implement getAllPlayable that returns only the levels playable by the user based on his statistics
        List<Level> levels = getAll();
        return Pair.of(levels, levels.size());
    }
    
    /**
     * Simulates a set of actions on a specific level.
     * @param level_id the level ID string.
     * @param actions the array of actions to be simulated on the Level.
     */
    public static void validateMoves(String level_id, EAction[] actions) {
        // TODO: Change signature and return list of all the TileDTO state changes after performing each move.
        Optional<Level> level = getLevelById(level_id);
        
        if(level.isPresent()) {
            level.get().validateActions(actions);
        } else {
            // throw custom exception
        }
    }
    
    
    
    /**
     * Returns all levels in the game.
     * @return List containing all the levels in the game
     */
    public static List<Level> getAll() {
        return levelRepository.findAll();
    }
    
    /**
     * Returns a Level with a specific ID.
     * @param level_id the level_id of the level to look for.
     * @return an Optional containing the Level if there is one with the provided ID.
     */
    public static Optional<Level> getLevelById(String level_id) {
        return levelRepository.findById(level_id);
    }
}
