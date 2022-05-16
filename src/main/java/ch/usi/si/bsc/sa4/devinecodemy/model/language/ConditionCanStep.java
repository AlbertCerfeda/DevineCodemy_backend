package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ConditionCanStep implements BooleanCondition {

    @JsonCreator
    public ConditionCanStep() {}

    @Override
    public boolean evaluate(Context context) {
        Robot robot = context.getRobot();
        boolean result = context.getBoard().canStep(robot.getPosX(), robot.getPosY(), robot.getOrientation());

        return result;
    }
}
