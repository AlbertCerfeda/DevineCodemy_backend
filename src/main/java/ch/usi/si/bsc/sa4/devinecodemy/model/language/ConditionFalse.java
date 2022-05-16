package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * ConditionFalse is a condition that always returns false.
 */
public class ConditionFalse implements BooleanCondition {

    /**
     * Creates a new ConditionFalse.
     */
    @JsonCreator
    public ConditionFalse() {}

    @Override
    public boolean evaluate(Context context) {
        return false;
    }
}
