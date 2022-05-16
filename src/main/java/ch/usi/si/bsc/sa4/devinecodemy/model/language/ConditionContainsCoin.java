package ch.usi.si.bsc.sa4.devinecodemy.model.language;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;

public class ConditionContainsCoin implements BooleanCondition {

    @JsonCreator
    public ConditionContainsCoin() {}

    @Override
    public boolean evaluate(Context context) {
        Robot robot = context.getRobot();

        return context.getBoard().containsItemAt(robot.getPosX(), robot.getPosY());
    }
}
