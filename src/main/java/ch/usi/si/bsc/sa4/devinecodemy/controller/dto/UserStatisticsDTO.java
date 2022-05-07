package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.HashMap;
import java.util.Map;

import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.LevelStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.statistics.UserStatistics;

/**
 * The UserStatisticsDTO class represents the UserStatistics state
 * to be used by the client.
 */
public class UserStatisticsDTO {
    /**
     * The id of the user of whose statistics belong to.
     */
    private final String id;
    /**
     * The collection of the levels played mapping from the levelNumber
     * to the LevelStatistics of the Level played by the user.
     */
    private final HashMap<Integer, LevelStatistics> levelData;

    /**
     * Constructs a UserStatisticsDTO object of the given userStatistic.
     * @param userStatistics the UserStatistic to build the DTO from.
     */
    public UserStatisticsDTO(UserStatistics userStatistics){
        this.id = userStatistics.getId();
        this.levelData = (HashMap<Integer, LevelStatistics>) userStatistics.getData();
    }

    /**
     * Returns the id of the UserStatistic, that is the id of the user.
     * @return the id of the UserStatistic.
     */
    public String getId(){
        return this.id;
    }

    /**
     * Returns the levelData of the User.
     * @return the Map of LevelStatistics played.
     */
    public Map<Integer, LevelStatistics> getData(){
        return this.levelData;
    }

}
