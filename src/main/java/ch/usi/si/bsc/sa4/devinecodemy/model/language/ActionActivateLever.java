package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.animation.CoordinatesAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ECoordinatesAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.LeverTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Action to collect a coin.
 */
public class ActionActivateLever extends Action {

    /**
     * Creates a new action to collect a coin.
     *
     * @param nextAction the next action to execute.
     */
    @JsonCreator
    public ActionActivateLever(@JsonProperty("next") Action nextAction) {
        super(nextAction);
    }

    @Override
    public void execute(Context context)  throws ExecutionTimeoutException {
        context.incrementClock();
        if (!context.isDead()) {
            final Robot robot = context.getRobot();

            Tile lever = context.getBoard().getTileAt(robot.getPosX(), robot.getPosY());
            if (lever instanceof LeverTile) {
                LeverTile leverTile = (LeverTile) lever;
                Tile teleport = context.getBoard().getTileAt(leverTile.getTeleportX(), leverTile.getTeleportY());
                if (teleport instanceof TeleportTile) {
                    TeleportTile teleportTile = (TeleportTile) teleport;
                    teleportTile.setActive(true);

                    context.getLevelValidation().addAnimation(new CoordinatesAnimation(ECoordinatesAnimation.ACTIVATE_LEVER, teleportTile.getTargetX(), teleportTile.getTargetY(), teleportTile.getTargetZ()));
                }

            } else {
                context.getLevelValidation().addAnimation(ERobotAnimation.EMOTE_NO);
            }
        }

        super.executeNextAction(context);
    }
}