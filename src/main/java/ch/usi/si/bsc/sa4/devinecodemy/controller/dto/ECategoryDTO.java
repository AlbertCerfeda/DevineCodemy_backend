package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.ECategory;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents the state of a usable command that the player can use.
 */
public class ECategoryDTO {
    
    private final String name;
    private final String description;

    /**
     * Constructs a new EActionDTO object given the EAction.
     * @param action the action to be converted.
     */
    public ECategoryDTO(ECategory action) {
        name = action.getName();
        description = action.getDescription();
    }

    /**
     * Constructs a new EActionDTO with the given values.
     * @param name the name of the EAction.
     * @param description the description of the EAction.
     */
    @JsonCreator
    public ECategoryDTO(@JsonProperty("name") String name,
                        @JsonProperty("description") String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
