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

    /**
     * Constructs a new ConditionAND object with the given values.
     * @param condition1 the first boolean condition.
     * @param condition2 the second boolean condition.
     */
    @JsonCreator
    public ConditionAND(@JsonProperty("operandA") BooleanCondition condition1, @JsonProperty("operandB") BooleanCondition condition2) {
        this.condition1 = condition1;
        this.condition2 = condition2;
    }

    @Override
    public boolean evaluate(Context context) {
        return condition1.evaluate(context) && condition2.evaluate(context);
    }
}
