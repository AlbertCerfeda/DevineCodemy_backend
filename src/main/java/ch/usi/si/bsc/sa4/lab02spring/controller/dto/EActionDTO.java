package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

import ch.usi.si.bsc.sa4.lab02spring.model.EAction;

/**
 * Represents the state of a usable command that the client can use.
 */
public class EActionDTO {
    
    private String name;
    private String description;
    
    public EActionDTO (EAction action) {
        name = action.name();
        description = action.getDescription();
    }
}
