package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;

/**
 * DTO representing the world of a level.
 */
public class EWorldDTO {
    
    private String name;
    private String description;

    public EWorldDTO (EWorld world) {
        name = world.name();
        description = world.getDescription();
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription() {
        return description;
    }
}
