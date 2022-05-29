package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A turn left action. This action is used to turn the robot left.
 */
public class ActionTurnLeft extends Action {

    /**
     * Creates a new turn left action.
     *
     * @param nextAction the next action to execute.
     */
    @JsonCreator
    public ActionTurnLeft(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }


    @Override
    public void execute(Context context) throws ExecutionTimeoutException {
        context.incrementClock();
        if (!context.isDead()) {
            context.getRobot().turnLeft();
            context.getLevelValidation().addAnimation(ERobotAnimation.TURN_LEFT);
        }
        super.executeNextAction(context);
    }

}
