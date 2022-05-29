package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;
import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Condition that checks if the robot can step in a given direction.
 */
public class ConditionCanStep implements BooleanCondition {

    private final String direction;

    /**
     * Creates a new ConditionCanStep object that checks
     * if the robot can step in a given direction.
     * @param direction the direction to check if the step is possible
     */
    @JsonCreator
    public ConditionCanStep(@JsonProperty("orientation") String direction) {
        this.direction = direction;
    }

    @Override
    public boolean evaluate(Context context) {
        final Robot robot = context.getRobot();
        EOrientation orientation = robot.getOrientation();

        switch (direction) {
            case "FORWARD":
                // the robot is already facing forward
                break;
            case "LEFT":
                orientation = orientation.turnLeft();
                break;
            case "RIGHT":
                orientation = orientation.turnRight();
                break;
            case "BACKWARD":
                orientation = orientation.getOpposite();
                break;
            default:
                // this should never happen
                context.getLevelValidation().addError("Unknown direction: " + direction);
        }


        return context.getBoard().canStep(robot.getPosX(), robot.getPosY(), orientation);
    }
}
