package ch.usi.si.bsc.sa4.devinecodemy.model;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.ECategoryDTO;

/**
 * The EAction class represents the actions at the disposal of the player.
 */
public enum ECategory {
    BASIC_COMMANDS("Basic commands", "To move the robot."),
    LOGIC("Logic", "To execute code if a condition is true."),
    CONDITIONS("Conditions","Boolean conditions to eventually execute code."),
    LOOPS("Loops", "To execute the same code several times in a row."),
    FUNCTIONS("Functions","To define a block of code inside a separate body");

    /** The function call for this command (eg 'moveForward' ) */
    private final String name;
    /** The description for the command */
    private final String description;

    /**
     * Creates an EAction with the given funcCall and description.
     * @param name the name of the func to be called when the action is executed.
     * @param description the description of the action.
     */
    ECategory(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Returns the EActionDTO of this EAction object.
     * @return the EActionDTO of this EAction object.
     */
    public ECategoryDTO toEActionDTO() {
        return new ECategoryDTO(this);
    }
}
