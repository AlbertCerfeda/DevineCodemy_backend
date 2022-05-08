package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;

/**
 * DTO representing the world of a level.
 */
public class EWorldDTO {
    
    private String name;
    private String descriptionMessage;
    private String congratulationsMessage;

    private int firstLevelNumber;

    private int lastLevelNumber;
    
    public EWorldDTO (EWorld world) {
        name = world.getName();
        descriptionMessage= world.getDescriptionMessage();
        congratulationsMessage = world.getCongratulationsMessage();
        firstLevelNumber = world.getFirstLevelNumber();
        lastLevelNumber = world.getLastLevelNumber();
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
