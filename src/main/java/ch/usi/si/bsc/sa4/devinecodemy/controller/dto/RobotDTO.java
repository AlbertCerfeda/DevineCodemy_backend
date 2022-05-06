package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;

/**
 * The RobotDTO class represents the robot state to be used
 * by a client.
 */
public class RobotDTO {
    private final int posX;
    private final int posY;
    /**
     * An enum of all the Orientations the Robot can have.
     */
    private final String orientation;

    /**
     * Constructs a RobotDTO object of the given robot.
     * @param robot the robot to be matched.
     */
    public RobotDTO (Robot robot) {
        posX = robot.getPosX();
        posY = robot.getPosY();
        orientation = robot.getOrientation().name();
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getOrientation() {
        return orientation;
    }
}
