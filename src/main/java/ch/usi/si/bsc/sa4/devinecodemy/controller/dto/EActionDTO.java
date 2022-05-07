package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;

/**
 * Represents the state of a usable command that the player can use.
 */
public class EActionDTO {
    
    private final String name;
    private final String description;

    /**
     * Constructs a new EActionDTO given the EAction.
     * @param action the action to be converted.
     */
    public EActionDTO (EAction action) {
        name = action.getFuncCall();
        description = action.getDescription();
    }



    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
