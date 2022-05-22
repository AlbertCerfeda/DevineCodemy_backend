package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.SAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.TAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Action to collect a coin.
 */
public class ActionCollectCoin extends Action {

    /**
     * Creates a new action to collect a coin.
     *
     * @param nextAction the next action to execute.
     */
    @JsonCreator
    public ActionCollectCoin(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }

    @Override
    public void execute(Context context)  throws ExecutionTimeoutException {
        context.incrementClock();
        if (!context.isDead()) {
            final Robot robot = context.getRobot();
            context.getLevelValidation().addAnimation(SAnimation.JUMP);

            if (context.getBoard().containsItemAt(robot.getPosX(), robot.getPosY())) {
                context.incrementCollectedCoins();

                // Activate teleports if the coins collected are enough
                context.getBoard().getGrid().forEach(tile -> {
                    if (tile.isTeleport()) {
                        TeleportTile teleport = (TeleportTile) tile;
                        if (!teleport.isActive() && teleport.getCoinsToActivate() <= context.getCollectedCoins()) {
                            teleport.setActive(true);

                            TAnimation animation = TAnimation.ACTIVATE_TELEPORT_AT;
                            animation.setTargetX(teleport.getPosX());
                            animation.setTargetY(teleport.getPosY());
                            context.getLevelValidation().addAnimation(animation); // activate teleport animation
                        }
                    }
                });
            } else {
                context.getLevelValidation().addAnimation(SAnimation.EMOTE_NO);
            }
        }

        super.executeNextAction(context);
    }
}
