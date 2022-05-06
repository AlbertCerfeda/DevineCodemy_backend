package ch.usi.si.bsc.sa4.devinecodemy.model.statistics;

import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserStatisticsDTO;
import ch.usi.si.bsc.sa4.devinecodemy.service.GamePlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the game statistics for a single user.
 */
@Document(collection = "statistics")
public class UserStatistics {

    @Id
    private String id;

    /**
     * Maps the levelNumber of the Level to a LevelData object
     */
    private final HashMap<Integer, LevelStatistics> levelData;


    /**
     * Constructs a UserStatistic object that stores all recorded data
     * for each level and game played by a single user.
     * The user's id is used in creating the object,
     * so that it can also be identified by it.
     *
     * @param id the id of the user.
     */
    public UserStatistics(String id) {
        this.id = id;
        this.levelData = new HashMap<>();
    }

    /**
     * Constructs a UserStatistic object that stores all recorded data
     * for each level and game played by a single user.
     * Needed by the db on retrieval to create a object with all the parameters.
     * The user's id is used in creating the object,
     * so that it can also be identified by it.
     *
     * @param id the id of the user.
     * @param levelData the levelData of the user.
     */
    @PersistenceConstructor
    UserStatistics(String id, HashMap<Integer, LevelStatistics> levelData) {
        this.id = id;
        this.levelData = levelData;
    }

    public String getId() {
        return this.id;
    }

    public Map<Integer, LevelStatistics> getData() {
        return this.levelData;
    }


    /**
     * Adds the commands used by a user while playing a level. If there was no existing data for that level,
     * a new LevelStatistics is created for it and then the commands are added to it. Otherwise, the commands are added
     * to the already existing one.
     *
     * @param game the game from which to retrieve the statistics.
     */
    public void addData(GamePlayer game, LevelValidation levelValidation) {
        Integer levelNumber = game.getLevel().getLevelNumber();

        LevelStatistics level;
        if (levelData.containsKey(levelNumber)) {
            level = levelData.get(levelNumber);
            if(!level.isCompleted()){
                level.setCompleted(levelValidation.isCompleted());
            }
        } else {
            level = new LevelStatistics(levelValidation.isCompleted());
        }
        level.add(game);
        levelData.put(levelNumber, level);
    }


    public UserStatisticsDTO toUserStatisticsDTO() {
        return new UserStatisticsDTO(this);
    }


}


