package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Action to move forward. This action will move the player forward one tile.
 */
public class ActionMoveForward extends Action {

    /**
     * Creates a new action to move forward.
     * @param nextAction the next action to execute.
     */
    @JsonCreator
    public ActionMoveForward(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }

    @Override
    public void execute(Context context) throws ExecutionTimeoutException {
        context.incrementClock();
        if (!context.isDead()) {
            try {
                context.getRobot().moveForward(context.getBoard());
                context.getLevelValidation().addAnimation(EAnimation.MOVE_FORWARD);
            } catch (Exception e) {
                context.getLevelValidation().addAnimation(EAnimation.EMOTE_DEATH);
                context.setDead(true);
            }
        }
        super.executeNextAction(context);
    }

}
