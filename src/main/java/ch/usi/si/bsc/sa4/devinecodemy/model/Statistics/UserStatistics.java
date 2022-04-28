package ch.usi.si.bsc.sa4.devinecodemy.model.Statistics;

import ch.usi.si.bsc.sa4.devinecodemy.model.LevelValidation.LevelValidation;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.UserStatisticsDTO;
import ch.usi.si.bsc.sa4.devinecodemy.service.GamePlayer;

import java.util.HashMap;

/**
 * Represents the game statistics for a single user.
 */
@Document(collection = "statistics")
public class UserStatistics {

    @Id
    private String id;

    private HashMap<Integer, LevelStatistics> level_data; // maps the ID of the Level to a LevelData object


    /**
     * Creates object that stores all recorded data for each level and game played by a single user.
     * The users' id is used in creating the object, so that it can also be identified by it.
     *
     * @param id the id of the user
     */
    public UserStatistics(String id) {
        this.id = id;
        this.level_data = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public HashMap<Integer, LevelStatistics> getData() {
        return this.level_data;
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
        if (level_data.containsKey(levelNumber)) {
            level = level_data.get(levelNumber);
            if(!level.isCompleted()){
                level.setCompleted(levelValidation.isCompleted());
            }
        } else {
            level = new LevelStatistics(levelValidation.isCompleted());
        }
        level.add(game);
        level_data.put(levelNumber, level);
    }


    public UserStatisticsDTO toUserStatisticsDTO() {
        return new UserStatisticsDTO(this);
    }


}


