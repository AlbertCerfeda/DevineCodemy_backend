package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * ConditionTrue represents a condition that is always true.
 */
public class ConditionTrue implements BooleanCondition {

    /**
     * Creates a new ConditionTrue.
     */
    @JsonCreator
    public ConditionTrue() {}

    @Override
    public boolean evaluate(Context context) {
        return true;
    }
}
