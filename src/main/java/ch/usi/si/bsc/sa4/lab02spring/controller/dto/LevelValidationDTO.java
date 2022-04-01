package ch.usi.si.bsc.sa4.lab02spring.controller.dto;

import ch.usi.si.bsc.sa4.lab02spring.model.LevelValidation.LevelValidation;
import org.springframework.data.util.Pair;

import java.util.List;

public class LevelValidationDTO {
    private final boolean completed;
    private final List<Pair<Integer, String>> errors;
    private final List<Pair<Integer, String>> animations;

    public LevelValidationDTO(LevelValidation levelValidation) {
        completed = levelValidation.isCompleted();
        errors = levelValidation.getErrors();
        animations = levelValidation.getAnimationsAsStrings();
    }

}
