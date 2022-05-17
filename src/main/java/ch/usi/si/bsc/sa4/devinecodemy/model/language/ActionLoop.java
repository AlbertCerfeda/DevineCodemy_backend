package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A loop in the language. The loop is executed a number of times.
 */
public class ActionLoop extends Action {

    private final int loopCount;
    private final Action body;

    /**
     * Constructor for the loop.
     *
     * @param count the number of times the loop is executed.
     * @param body the loop body.
     * @param nextAction the next action in the chain.
     */
    @JsonCreator
    public ActionLoop(@JsonProperty("count") final int count,
                      @JsonProperty("body") final Action body,
                      @JsonProperty("next") final Action nextAction) {
        super(nextAction);
        this.loopCount = count;
        this.body = body;
    }

    @Override
    public void execute(Context context) throws RuntimeException {
        context.incrementClock();
        for (int i = 0; i < loopCount; i++) {
            body.execute(context);
        }

        super.executeNextAction(context);
    }
}
