package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.level.Robot;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String orientation;

    /**
     * Constructs a new RobotDTO object with the given values.
     * @param posX the initial x position of the Robot.
     * @param posY the initial y position of the Robot.
     */
    @JsonCreator
    public RobotDTO (@JsonProperty("posX") int posX,
                     @JsonProperty("posY") int posY) {
        this.posX = posX;
        this.posY = posY;
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
