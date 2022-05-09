package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;
import org.springframework.data.util.Pair;

/**
 * DTO representing the world of a level.
 */
public class EWorldDTO {
    
    private String name;
    private String descriptionMessage;
    private String congratulationsMessage;

    private int firstLevelNumber;

    private int lastLevelNumber;
    
    public EWorldDTO (EWorld world, Pair<Integer, Integer> levelNumberRange) {
        name = world.getName();
        descriptionMessage= world.getDescriptionMessage();
        congratulationsMessage = world.getCongratulationsMessage();
        this.firstLevelNumber = levelNumberRange.getFirst();
        this.lastLevelNumber = levelNumberRange.getSecond();
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescriptionMessage() {
        return descriptionMessage;
    }
    
    public String getCongratulationsMessage(){
        return congratulationsMessage;
    }

    public int getFirstLevelNumber() { return firstLevelNumber; }

    public int getLastLevelNumber() { return lastLevelNumber;}
}
