package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import java.util.List;

import ch.usi.si.bsc.sa4.devinecodemy.model.LevelValidation.LevelValidation;

public class LevelValidationDTO {
    private final boolean completed;
    private final List<String> errors;
    private final List<String> animations;

    /**
     * Constructor for LevelValidationDTO
     * @param levelValidation the LevelValidation object to convert into a DTO
     */
    public LevelValidationDTO(LevelValidation levelValidation) {
        completed = levelValidation.isCompleted();
        errors = levelValidation.getErrors();
        animations = levelValidation.getAnimationsAsStrings();
    }

}