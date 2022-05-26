package ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.Animation;

/**
 * Represents the result of the code validation done by the GamePlayer.
 * Contains the animations to be performed by the client and eventual parsing errors.
 */
public class LevelValidation {
    private boolean completed;
    private final List<String> errors;
    private final List<Animation> animations;

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

    public void addAnimation(Animation animation) {
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

    public List<Animation> getAnimations() {
        return animations;
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public List<String> getAnimationsAsStrings() {
        return animations.stream().map(Animation::toString).collect(Collectors.toList());
    }

    public LevelValidationDTO toLevelValidationDTO() {
        return new LevelValidationDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LevelValidation)) return false;
        LevelValidation that = (LevelValidation) o;
        return completed == that.completed && Objects.equals(errors, that.errors) && Objects.equals(animations, that.animations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(completed, errors, animations);
    }
}
