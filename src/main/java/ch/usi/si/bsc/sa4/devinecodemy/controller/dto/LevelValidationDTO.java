package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;

import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;

/**
 *
 */
public class LevelValidationDTO {
    private final boolean completed;
    private final List<String> errors;
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
