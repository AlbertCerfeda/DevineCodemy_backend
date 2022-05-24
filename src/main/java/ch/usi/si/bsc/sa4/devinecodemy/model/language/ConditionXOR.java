package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Evaluates XOR operation on two condition objects.
 */
public class ConditionXOR implements BooleanCondition{

    private final BooleanCondition cond1;
    private final BooleanCondition cond2;


    public ConditionXOR(@JsonProperty("cond1") BooleanCondition cond1, @JsonProperty("cond2") BooleanCondition cond2){
        this.cond1 = cond1;
        this.cond2 = cond2;
    }

    @Override
    public boolean evaluate(Context context) {
        return cond1.evaluate(context) ^ cond2.evaluate(context);
    }
}
