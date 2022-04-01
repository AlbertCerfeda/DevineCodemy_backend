package ch.usi.si.bsc.sa4.lab02spring.model.LevelValidation;

import ch.usi.si.bsc.sa4.lab02spring.controller.dto.LevelValidationDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EAnimation;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class LevelValidation {
    private boolean completed;
    private final List<Pair<Integer, String>> errors;
    private final List<Pair<Integer, EAnimation>> animations;

    public LevelValidation() {
        completed = false;
        errors = new ArrayList<>();
        animations = new ArrayList<>();
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void addError(int line, String message) {
        errors.add(Pair.of(line, message));
    }

    public void addAnimation(int line, EAnimation animation) {
        animations.add(Pair.of(line, animation));
    }

    public boolean isCompleted() {
        return completed;
    }

    public void clearAnimations() {
        animations.clear();
    }

    public List<Pair<Integer, String>> getErrors() {
        return errors;
    }

    public List<Pair<Integer, EAnimation>> getAnimations() {
        return animations;
    }

    public List<Pair<Integer, String>> getAnimationsAsStrings() {
        List<Pair<Integer, String>> result = new ArrayList<>();
        animations.forEach(pair -> result.add(Pair.of(pair.getFirst(), pair.getSecond().toString())));
        return result;
    }

    public LevelValidationDTO toLevelValidationDTO() {
        return new LevelValidationDTO(this);
    }
}
