package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionWhile extends Action {
    private final BooleanCondition condition;
    private final Action body;

    @JsonCreator
    public ActionWhile(@JsonProperty("condition") BooleanCondition condition,
                       @JsonProperty("body") Action body,
                       @JsonProperty("next") Action nextAction) {
        super(nextAction);
        this.condition = condition;
        this.body = body;
    }

    public void execute(Context context) {
        while (condition.evaluate(context)) {
            body.execute(context);
        }
    }
}
