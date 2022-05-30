package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An if statement in the language. It has a condition and a body.
 * If the condition is true, the body is executed.
 */
public class ActionIf extends Action{

    private final BooleanCondition condition;
    private final Action body;

    /**
     * Creates a new if statement.
     * @param condition the condition.
     * @param body the body.
     * @param nextAction the next action.
     */
    @JsonCreator
    public ActionIf(@JsonProperty("condition") BooleanCondition condition,
                    @JsonProperty("body") Action body,
                    @JsonProperty("next") Action nextAction) {
        super(nextAction);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public int countActions() {
        return 1 + body.countActions();
    }

    @Override
    public void execute(Context context) throws ExecutionTimeoutException {
        context.incrementClock();
        if (condition.evaluate(context)) {
            body.execute(context);
        }

        super.executeNextAction(context);
    }

}
