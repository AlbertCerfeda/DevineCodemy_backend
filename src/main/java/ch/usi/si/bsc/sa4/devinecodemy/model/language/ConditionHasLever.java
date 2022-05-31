package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.LeverTile;
import ch.usi.si.bsc.sa4.devinecodemy.model.tile.Tile;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Condition that checks if the robot can step in a given direction.
 */
public class ConditionHasLever implements BooleanCondition {

    private final String params;

    /**
     * Creates a new ConditionCanStep object that checks
     * if the robot can step in a given direction.
     * @param params the direction to check if the step is possible
     */
    @JsonCreator
    public ConditionHasLever(@JsonProperty("params") String params) {
        this.params = params;
    }

    @Override
    public boolean evaluate(Context context) {
        Tile lever = context.getBoard().getTileAt(context.getRobot().getPosX(), context.getRobot().getPosY());

        if (lever instanceof LeverTile) {
            if (params.equals("active")) {
                return ((LeverTile) lever).isActive();
            } else if (params.equals("inactive")) {
                return !((LeverTile) lever).isActive();
            }
            return true;
        }
        return false;
    }
}
