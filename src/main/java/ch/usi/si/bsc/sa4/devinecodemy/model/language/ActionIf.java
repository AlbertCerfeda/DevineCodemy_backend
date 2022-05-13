package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionIf extends Action{

    private final BooleanCondition condition;
    private final Action body;

    @JsonCreator
    public ActionIf(@JsonProperty("condition") BooleanCondition condition,
                    @JsonProperty("body") Action body,
                    @JsonProperty("next") Action nextAction) {
        super(nextAction);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Context context) {
        if (condition.evaluate(context)) {
            body.execute(context);
        }

        super.executeNextAction(context);
    }

}
