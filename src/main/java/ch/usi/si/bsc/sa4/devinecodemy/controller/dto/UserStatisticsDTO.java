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
    private final String id;
    private final HashMap<Integer, LevelStatistics> levelData;

    /**
     * Constructs a UserStatisticsDTO object of the given userStatistic.
     * @param userStatistics the UserStatistic to be matched.
     */
    public UserStatisticsDTO(UserStatistics userStatistics){
        this.id = userStatistics.getId();
        this.levelData = (HashMap<Integer, LevelStatistics>) userStatistics.getData();
    }

    public String getId(){
        return this.id;
    }

    public Map<Integer, LevelStatistics> getData(){
        return this.levelData;
    }

}
