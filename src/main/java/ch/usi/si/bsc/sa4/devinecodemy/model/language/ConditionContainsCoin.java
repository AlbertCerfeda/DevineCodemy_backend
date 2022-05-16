package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Condition that checks if the board contains a coin at the robot's position.
 */
public class ConditionContainsCoin implements BooleanCondition {

    /**
     * Creates a new condition that checks if the board contains a coin at the robot's position.
     */
    @JsonCreator
    public ConditionContainsCoin() {}

    @Override
    public boolean evaluate(Context context) {
        Robot robot = context.getRobot();

        return context.getBoard().containsItemAt(robot.getPosX(), robot.getPosY());
    }
}
