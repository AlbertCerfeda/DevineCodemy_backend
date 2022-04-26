package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.HashMap;

import ch.usi.si.bsc.sa4.devinecodemy.model.Statistics.UserStatistics;

public class UserStatisticsDTO {
    private final String id;
    private final HashMap level_data;

    public UserStatisticsDTO(UserStatistics userStatistics){
        this.id = userStatistics.getId();
        this.level_data = userStatistics.getData();

    }

    public String getId(){
        return this.id;
    }

    public HashMap getData(){
        return this.level_data;
    }

}
