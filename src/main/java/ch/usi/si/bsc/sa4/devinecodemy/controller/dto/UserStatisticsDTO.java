package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.HashMap;
import java.util.Map;

import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.LevelStatistics;
import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.UserStatistics;

public class UserStatisticsDTO {
    private final String id;
    private final HashMap<Integer, LevelStatistics> levelData;

    public UserStatisticsDTO(UserStatistics userStatistics){
        this.id = userStatistics.getId();
        this.levelData = userStatistics.getData();
    }

    public String getId(){
        return this.id;
    }

    public Map<Integer, LevelStatistics> getData(){
        return this.levelData;
    }

}
