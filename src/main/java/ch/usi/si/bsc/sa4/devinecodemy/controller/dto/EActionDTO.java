package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;

/**
 * Represents the state of a usable command that the player can use.
 */
public class EActionDTO {
    
    private String name;
    private String description;

    /**
     * Constructs a new EActionDTO object given the EAction.
     * @param action the action to be converted.
     */
    public EActionDTO (EAction action) {
        name = action.getFuncCall();
        description = action.getDescription();
    }

    /**
     * Constructs a new empty EActionDTO object.
     */
    public EActionDTO () {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
