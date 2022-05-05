package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EWorld;

/**
 * DTO representing the world of a level
 */
public class EWorldDTO {

    private String description;

    public EWorldDTO (EWorld world) {
        description = world.getDescription();
    }

    public String getDescription() {
        return description;
    }
}
