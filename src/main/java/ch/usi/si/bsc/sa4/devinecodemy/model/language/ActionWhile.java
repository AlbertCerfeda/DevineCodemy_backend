package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A while loop in the language. It has a condition and a body.
 */
public class ActionWhile extends Action {
    private final BooleanCondition condition;
    private final Action body;

    /**
     * Creates a new while loop.
     *
     * @param condition the condition of the loop.
     * @param body the body of the loop.
     * @param nextAction the next action to execute.
     */
    @JsonCreator
    public ActionWhile(@JsonProperty("condition") BooleanCondition condition,
                       @JsonProperty("body") Action body,
                       @JsonProperty("next") Action nextAction) {
        super(nextAction);
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(Context context) throws RuntimeException {
        // start timer, if the method takes longer than the timeout, the method will be aborted
        while (condition.evaluate(context)) {
            context.incrementClock();
            body.execute(context);
        }

        super.executeNextAction(context);
    }
}
