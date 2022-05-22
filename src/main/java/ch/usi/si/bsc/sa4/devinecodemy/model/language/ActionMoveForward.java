package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.SAnimation;
import ch.usi.si.bsc.sa4.devinecodemy.model.TAnimation;
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
                Board board = context.getBoard();
                Robot robot = context.getRobot();
                robot.moveForward(context.getBoard());
                context.getLevelValidation().addAnimation(SAnimation.MOVE_FORWARD);

                if (board.containsTeleportAt(context.getRobot().getPosX(), context.getRobot().getPosY())) {
                    TeleportTile teleport = (TeleportTile)board.getTileAt(context.getRobot().getPosX(), context.getRobot().getPosY());
                    if (teleport.isActive()) {
                        robot.teleportTo(teleport.getTargetX(), teleport.getTargetY());
                        //SAnimation animation = SAnimation.TELEPORT_TO;
                        TAnimation animation = TAnimation.TELEPORT_TO;
                        animation.setTargetX(teleport.getTargetX());
                        animation.setTargetY(teleport.getTargetY());
                        context.getLevelValidation().addAnimation(animation);
                    }
                }
            } catch (Exception e) {
                context.getLevelValidation().addAnimation(SAnimation.EMOTE_DEATH);
                context.setDead(true);
            }
        }
        super.executeNextAction(context);
    }

}
