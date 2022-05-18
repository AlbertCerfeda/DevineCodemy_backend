package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;

/**
 * The RobotDTO class represents the robot state to be used
 * by a client.
 */
public class RobotDTO {
    private int posX;
    private int posY;
    /**
     * An enum of all the Orientations the Robot can have.
     */
    private String orientation;

    /**
     * Constructs a RobotDTO object of the given robot.
     */
    public RobotDTO () {
    }

    /**
     * Constructs a RobotDTO object of the given robot.
     * @param robot the robot to build the DTO from.
     */
    public RobotDTO (Robot robot) {
        posX = robot.getPosX();
        posY = robot.getPosY();
        orientation = robot.getOrientation().name();
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
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
