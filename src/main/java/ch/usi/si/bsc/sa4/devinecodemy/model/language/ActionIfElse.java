package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * An if-else statement in the language.
 * If the condition is satisfied, the ifTrue action is executed,
 * otherwise the ifFalse action is executed.
 */
public class ActionIfElse extends Action{

    private final BooleanCondition condition;
    private final Action ifTrue;
    private final Action ifFalse;

    /**
     * Creates a new if statement.
     * @param condition the condition.
     * @param ifTrue the body if the condition is true.
     * @param ifFalse the body if the condition is false.
     * @param nextAction the next action.
     */
    @JsonCreator
    public ActionIfElse(@JsonProperty("condition") BooleanCondition condition,
                        @JsonProperty("ifTrue") Action ifTrue,
                        @JsonProperty("ifFalse") Action ifFalse,
                        @JsonProperty("next") Action nextAction) {
        super(nextAction);
        this.condition = condition;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    @Override
    public int countActions() {
        return 1 + ifTrue.countActions() + ifFalse.countActions();
    }

    @Override
    public void execute(Context context) throws ExecutionTimeoutException {
        context.incrementClock();
        if (condition.evaluate(context)) {
            ifTrue.execute(context);
        } else {
            ifFalse.execute(context);
        }

        super.executeNextAction(context);
    }

}
