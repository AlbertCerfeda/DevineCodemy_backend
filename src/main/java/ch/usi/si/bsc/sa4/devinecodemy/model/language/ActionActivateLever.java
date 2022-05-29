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

            final Tile lever = context.getBoard().getTileAt(robot.getPosX(), robot.getPosY());
            if (lever instanceof LeverTile) {
                final LeverTile leverTile = (LeverTile) lever;
                final Tile teleport = context.getBoard().getTileAt(leverTile.getTeleportX(), leverTile.getTeleportY());
                if (teleport instanceof TeleportTile) {
                    final TeleportTile teleportTile = (TeleportTile) teleport;
                    teleportTile.setActive(!teleportTile.isActive());
                    final Tile targetTile = context.getBoard().getTileAt(teleportTile.getTargetX(), teleportTile.getTargetY());
                    if (targetTile instanceof TeleportTile) {
                        final TeleportTile teleportTarget = ((TeleportTile) targetTile);
                        teleportTarget.setActive(teleportTile.isActive());
                    }
                    context.getLevelValidation().addAnimation(
                            new CoordinatesAnimation(
                                    teleportTile.isActive()
                                            ? ECoordinatesAnimation.ACTIVATE_LEVER
                                            : ECoordinatesAnimation.DEACTIVATE_LEVER,
                                    leverTile.getPosX(),
                                    leverTile.getPosY(),
                                    leverTile.getPosZ()));
                    context.getLevelValidation().addAnimation(
                            new CoordinatesAnimation(
                                    teleportTile.isActive()
                                            ? ECoordinatesAnimation.ACTIVATE_TELEPORT_AT
                                            : ECoordinatesAnimation.DEACTIVATE_TELEPORT_AT,
                                    teleportTile.getPosX(),
                                    teleportTile.getPosY(),
                                    teleportTile.getPosZ()
                            )
                    );
                    context.getLevelValidation().addAnimation(
                            new CoordinatesAnimation(
                                    teleportTile.isActive()
                                            ? ECoordinatesAnimation.ACTIVATE_TELEPORT_AT
                                            : ECoordinatesAnimation.DEACTIVATE_TELEPORT_AT,
                                    teleportTile.getTargetX(),
                                    teleportTile.getTargetY(),
                                    teleportTile.getTargetZ()
                            )
                    );
                }
            } else {
                context.getLevelValidation().addAnimation(ERobotAnimation.EMOTE_NO);
            }
        }

        super.executeNextAction(context);
    }
}
