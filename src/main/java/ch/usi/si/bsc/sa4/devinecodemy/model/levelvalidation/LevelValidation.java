package ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;

/**
 * Represents the result of the code validation done by the GamePlayer.
 * Contains the animations to be performed by the client and eventual parsing errors.
 */
public class LevelValidation {
    private boolean completed;
    private final List<String> errors;
    private final List<EAnimation> animations;

    public LevelValidation() {
        completed = false;
        errors = new ArrayList<>();
        animations = new ArrayList<>();
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void addError(String message) {
        errors.add(message);
    }

    public void addAnimation(EAnimation animation) {
        animations.add(animation);
    }

    public boolean isCompleted() {
        return completed;
    }

    public void clearAnimations() {
        animations.clear();
    }

    public List<String> getErrors() {
        return errors;
    }

    public List<EAnimation> getAnimations() {
        return animations;
    }

    public List<String> getAnimationsAsStrings() {
        return animations.stream().map(EAnimation::toString).collect(Collectors.toList());
    }

    public LevelValidationDTO toLevelValidationDTO() {
        return new LevelValidationDTO(this);
    }
}
