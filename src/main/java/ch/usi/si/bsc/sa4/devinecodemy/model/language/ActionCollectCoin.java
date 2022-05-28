package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.animation.CoordinatesAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ECoordinatesAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
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
            context.getLevelValidation().addAnimation(ERobotAnimation.JUMP);

            if (context.getBoard().containsItemAt(robot.getPosX(), robot.getPosY())) {
                context.incrementCollectedCoins();

                // Activate teleports if the coins collected are enough
                context.getBoard().getGrid().forEach(tile -> {
                    if (tile.isTeleport()) {
                        TeleportTile teleport = (TeleportTile) tile;
                        if (!teleport.isActive() && teleport.getCoinsToActivate() == context.getCollectedCoins() && context.getCollectedCoins() > 0) {
                            teleport.setActive(true);

                            context.getLevelValidation().addAnimation(
                                    new CoordinatesAnimation(
                                            ECoordinatesAnimation.ACTIVATE_TELEPORT_AT,
                                            teleport.getPosX(),
                                            teleport.getPosY(),
                                            teleport.getPosZ())); // activate teleport animation
                        }
                    }
                });
            } else {
                context.getLevelValidation().addAnimation(ERobotAnimation.EMOTE_NO);
            }
        }

        super.executeNextAction(context);
    }
}
