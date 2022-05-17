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
    public void execute(Context context) {
        // start timer, if the method takes longer than the timeout, the method will be aborted
        long start = System.currentTimeMillis();
        while (condition.evaluate(context)) {
            body.execute(context);
            if (System.currentTimeMillis() - start > 1000) { // 1 second
                context.getLevelValidation()
                        .addError("Method execution timed out. It seems that the method is infinite.");
                break;
            }
        }

        super.executeNextAction(context);
    }
}
