package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Evaluates NOT operation a condition object.
 */
public class ConditionNOT implements BooleanCondition{

    private final BooleanCondition condition;

    /**
     * Constructs a new ConditionNOT object that reverts the value
     * of the given condition.
     * @param condition the condition to be reverted.
     */
    public ConditionNOT(@JsonProperty("body") BooleanCondition condition){
        this.condition = condition;
    }

    @Override
    public boolean evaluate(Context context) {
        return !(condition.evaluate(context));
    }
}
