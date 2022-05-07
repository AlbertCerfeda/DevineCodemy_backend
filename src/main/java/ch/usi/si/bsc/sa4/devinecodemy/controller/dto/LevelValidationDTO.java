package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;

import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;

/**
 * The LevelValidationDTO class represents the validation state
 * of the Level after being played, to be read by the player after
 * executing commands.
 */
public class LevelValidationDTO {
    /**
     * A boolean indicating whether the level was completed or not.
     */
    private final boolean completed;
    /**
     * A list of errors thrown when the level was played.
     * Possible errors:
     *  - Command not allowed.
     *  - The command doesn't exist.
     *  - Too many commands inserted.
     */
    private final List<String> errors;
    /**
     * A list of animations to be played on the client to show
     * the level execution.
     */
    private final List<String> animations;

    /**
     * Constructs a LevelValidationDTO object matching the given levelValidation.
     *
     * @param levelValidation the LevelValidation object to convert into a DTO.
     */
    public LevelValidationDTO(LevelValidation levelValidation) {
        completed = levelValidation.isCompleted();
        errors = levelValidation.getErrors();
        animations = levelValidation.getAnimationsAsStrings();
    }

    /**
     * To get if the level is completed.
     *
     * @return true if the level is completed, false otherwise.
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * To get the list of errors.
     *
     * @return the list of errors.
     */
    public List<String> getErrors() {
        return errors;
    }

    /**
     * To get the list of animations.
     *
     * @return the list of animations.
     */
    public List<String> getAnimations() {
        return animations;
    }
}
