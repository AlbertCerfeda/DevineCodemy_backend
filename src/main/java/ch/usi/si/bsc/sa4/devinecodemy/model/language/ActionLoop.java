package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionLoop extends Action {

    private final int loopCount;
    private final Action body;

    @JsonCreator
    public ActionLoop(@JsonProperty("count") final int count,
                      @JsonProperty("body") final Action body,
                      @JsonProperty("next") final Action nextAction) {
        super(nextAction);
        this.loopCount = count;
        this.body = body;
    }

    @Override
    public void execute(Context context) {
        for (int i = 0; i < loopCount; i++) {
            body.execute(context);
        }

        super.executeNextAction(context);
    }
}
