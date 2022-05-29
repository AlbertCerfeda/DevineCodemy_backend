package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Evaluates XOR operation on two condition objects.
 */
public class ConditionXOR implements BooleanCondition{

    private final BooleanCondition cond1;
    private final BooleanCondition cond2;

    /**
     * Constructs a new ConditionXOR object evaluating
     * to true only if the given values differ.
     * @param cond1 the first value to be checked.
     * @param cond2 the second value to be checked.
     */
    public ConditionXOR(@JsonProperty("operandA") BooleanCondition cond1, @JsonProperty("operandB") BooleanCondition cond2){
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    @Override
    public boolean evaluate(Context context) {
        return cond1.evaluate(context) ^ cond2.evaluate(context);
    }
}
