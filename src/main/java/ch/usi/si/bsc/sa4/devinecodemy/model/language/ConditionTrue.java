package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ConditionTrue implements BooleanCondition {

    @JsonCreator
    public ConditionTrue() {}

    @Override
    public boolean evaluate(Context context) {
        return true;
    }
}
