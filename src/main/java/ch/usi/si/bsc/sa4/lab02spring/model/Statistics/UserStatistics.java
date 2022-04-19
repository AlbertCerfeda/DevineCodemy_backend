package ch.usi.si.bsc.sa4.lab02spring.model.Statistics;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.UserStatisticsDTO;
import ch.usi.si.bsc.sa4.lab02spring.service.GamePlayer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;

/**
 * Represents the game statistics for a single user.
 */
@Document(collection = "statistics")
public class UserStatistics {

    @Id
    private String id;

    private HashMap<String, LevelStatistics> level_data; // maps the ID of the Level to a LevelData object

    private int levels_completed;


    // TODO: Add Javadoc
    /**
     * @param user_id the id of the user
     */
    public UserStatistics(String user_id) {
        this.id = user_id;
        this.level_data = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public HashMap<String, LevelStatistics> getData() {
        return this.level_data;
    }

    // TODO: Add Javadoc

    /**
     * Adds the commands used by a user while playing a level. If there was no existing data for that level,
     * a new LevelStatistics is created for it and then the commands are added to it. Otherwise, the commands are added
     * to the already existing one.
     *
     * @param game the game from which to retrieve the statistics.
     */
    public void addData(GamePlayer game) {
        String level_id = game.getLevel().getId();

        LevelStatistics level;
        if (level_data.containsKey(level_id)) {
            level = level_data.get(level_id);
        } else {
            level = new LevelStatistics();
        }
        level.add(game);
        level_data.put(level_id, level);
    }


    public UserStatisticsDTO toUserStatisticsDTO() {
        return new UserStatisticsDTO(this);
    }


}


