package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;

public class ConditionFalse implements BooleanCondition {

    @JsonCreator
    public ConditionFalse() {}

    @Override
    public boolean evaluate(Context context) {
        return false;
    }
}
