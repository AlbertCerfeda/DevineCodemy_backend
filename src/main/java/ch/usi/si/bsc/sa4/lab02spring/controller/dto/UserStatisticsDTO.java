package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

import ch.usi.si.bsc.sa4.lab02spring.model.Statistics.UserStatistics;

import java.util.HashMap;

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
