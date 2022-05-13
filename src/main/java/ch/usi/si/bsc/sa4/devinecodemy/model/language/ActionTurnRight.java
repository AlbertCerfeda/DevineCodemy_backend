package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionTurnRight extends Action {

    @JsonCreator
    public ActionTurnRight(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }

    @Override
    public void execute(Context context) {
        context.getRobot().turnRight();
        context.getLevelValidation().addAnimation(EAnimation.TURN_RIGHT);

        super.executeNextAction(context);
    }
}
