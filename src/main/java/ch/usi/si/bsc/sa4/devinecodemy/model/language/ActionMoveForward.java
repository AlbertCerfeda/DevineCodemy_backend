package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.animation.CoordinatesAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ECoordinatesAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.animation.ERobotAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.exceptions.ExecutionTimeoutException;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Board;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.TeleportTile;
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
                final Board board = context.getBoard();
                final Robot robot = context.getRobot();
                robot.moveForward(context.getBoard());
                context.getLevelValidation().addAnimation(ERobotAnimation.MOVE_FORWARD);

                if (board.containsTeleportAt(context.getRobot().getPosX(), context.getRobot().getPosY())) {
                    final TeleportTile teleport = (TeleportTile)board.getTileAt(context.getRobot().getPosX(), context.getRobot().getPosY());
                    if (teleport.isActive()) {
                        robot.teleportTo(teleport.getTargetX(), teleport.getTargetY());

                        context.getLevelValidation().addAnimation(
                                new CoordinatesAnimation(ECoordinatesAnimation.TOGGLE_TELEPORT,
                                        teleport.getPosX(),
                                        teleport.getPosY(),
                                        teleport.getPosZ()));

                        context.getLevelValidation().addAnimation(
                                new CoordinatesAnimation(ECoordinatesAnimation.TELEPORT_TO,
                                                        teleport.getTargetX(),
                                                        teleport.getTargetY(),
                                                        teleport.getTargetZ()));
                        context.getLevelValidation().addAnimation(
                                new CoordinatesAnimation(ECoordinatesAnimation.TOGGLE_TELEPORT,
                                        teleport.getTargetX(),
                                        teleport.getTargetY(),
                                        teleport.getTargetZ()));
                    }
                }
            } catch (IllegalStateException e) {
                context.getLevelValidation().addAnimation(ERobotAnimation.EMOTE_DEATH);
                context.setDead(true);
            }
        }
        super.executeNextAction(context);
    }

}
