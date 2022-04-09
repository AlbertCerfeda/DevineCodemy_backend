package ch.usi.si.bsc.sa4.lab02spring.model.LevelValidation;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EAnimation;

import java.util.ArrayList;
import java.util.List;

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
        List<String> result = new ArrayList<>();
        animations.forEach(animation -> result.add(animation.toString()));
        return result;
    }

    public LevelValidationDTO toLevelValidationDTO() {
        return new LevelValidationDTO(this);
    }
}
