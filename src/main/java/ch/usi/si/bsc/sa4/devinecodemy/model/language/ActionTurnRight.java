package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A turn right action. This action will turn the robot to the right.
 */
public class ActionTurnRight extends Action {

    /**
     * Creates a new turn right action.
     *
     * @param nextAction the next action to execute.
     */
    @JsonCreator
    public ActionTurnRight(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }

    @Override
    public void execute(Context context) throws ExecutionTimeoutException {
        context.incrementClock();
        if (!context.isDead()) {
            context.getRobot().turnRight();
            context.getLevelValidation().addAnimation(ERobotAnimation.TURN_RIGHT);
        }
        super.executeNextAction(context);
    }
}
