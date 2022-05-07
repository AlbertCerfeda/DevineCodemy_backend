package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;

/**
 * DTO representing the world of a level.
 */
public class EWorldDTO {
    
    private String name;
    private String descriptionMessage;
    private String congratulationsMessage;
    
    public EWorldDTO (EWorld world) {
        name = world.name();
        descriptionMessage= world.getDescriptionMessage();
        congratulationsMessage = world.getCongratulationsMessage();
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
}
