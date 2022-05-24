package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Evaluates NOT operation a condition object.
 */
public class ConditionNOT implements BooleanCondition{

    private final BooleanCondition condition;

    public ConditionNOT(@JsonProperty("condition") BooleanCondition condition){
        this.condition = condition;
    }

    @Override
    public boolean evaluate(Context context) {
        return !(condition.evaluate(context));
    }
}
