package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionTurnLeft extends Action {

    @JsonCreator
    public ActionTurnLeft(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }


    @Override
    public void execute(Context context) {
        context.getRobot().turnLeft();
        context.getLevelValidation().addAnimation(EAnimation.TURN_LEFT);

        super.executeNextAction(context);
    }

}
