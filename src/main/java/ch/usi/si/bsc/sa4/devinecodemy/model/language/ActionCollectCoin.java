package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ActionCollectCoin extends Action {

    @JsonCreator
    public ActionCollectCoin(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }

    @Override
    public void execute(Context context) {
        if (!context.isDead()) {
            Robot robot = context.getRobot();
            context.getLevelValidation().addAnimation(EAnimation.JUMP);

            if (context.getBoard().containsItemAt(robot.getPosX(), robot.getPosY())) {
                context.incrementCollectedCoins();
            } else {
                context.getLevelValidation().addAnimation(EAnimation.EMOTE_NO);
            }
        }

        super.executeNextAction(context);
    }
}
