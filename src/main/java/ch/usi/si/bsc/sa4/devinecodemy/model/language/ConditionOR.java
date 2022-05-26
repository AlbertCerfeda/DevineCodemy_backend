package ch.usi.si.bsc.sa4.devinecodemy.model.language;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Evaluates OR operation on two condition objects.
 */

public class ConditionOR implements BooleanCondition{

    private final BooleanCondition cond1;
    private final BooleanCondition cond2;

    @JsonCreator
    public ConditionOR(@JsonProperty("operandA") BooleanCondition cond1, @JsonProperty("operandB") BooleanCondition cond2){
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    @Override
    public boolean evaluate(Context context) {
        return cond1.evaluate(context) || cond2.evaluate(context);
    }
}
