package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Condition that checks if the board contains a coin at the robot's position.
 */
public class ConditionContainsCoin implements BooleanCondition {

    /**
     * Creates a new ConditionContainsCoin object that checks
     * if the board contains a coin at the robot's position.
     */
    @JsonCreator
    public ConditionContainsCoin() {
        // Empty constructor needed to help Jackson to create objects from json.
    }

    @Override
    public boolean evaluate(Context context) {
        final Robot robot = context.getRobot();

        return context.getBoard().containsItemAt(robot.getPosX(), robot.getPosY());
    }
}
