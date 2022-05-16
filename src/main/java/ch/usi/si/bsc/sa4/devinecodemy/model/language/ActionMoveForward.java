package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.levelvalidation.LevelValidation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

public class ActionMoveForward extends Action {

    @JsonCreator
    public ActionMoveForward(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }

    @Override
    public void execute(Context context) {
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
