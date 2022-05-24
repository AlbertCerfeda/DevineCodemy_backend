package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A condition that can be evaluated to a boolean value.
 * Evaluates the AND of two conditions.
 */

public class ConditionAND implements BooleanCondition{

    private final BooleanCondition condition1;
    private final BooleanCondition condition2;

    @JsonCreator
    public ConditionAND(@JsonProperty("condition1") BooleanCondition condition1, @JsonProperty("condition2") BooleanCondition condition2) {
        this.condition1 = condition1;
        this.condition2 = condition2;
    }

    @Override
    public boolean evaluate(Context context) {
        return condition1.evaluate(context) && condition2.evaluate(context);
    }
}
