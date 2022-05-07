package ch.usi.si.bsc.sa4.devinecodemy.model.level;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;

import java.util.Objects;

/**
 * The Robot class represents the robot playing the level.
 */
public class Robot {
    private final int posX;
    private final int posY;
    /**
     * An enum of all the Orientations the Robot can have.
     */
    private final EOrientation orientation;

    /**
     * Constructs a new Robot object with the given values.
     * @param posX the x position of the robot.
     * @param posY the y position of the robot.
     * @param orientation the starting EOrientation of the robot.
     */
    @JsonCreator
    public Robot(@JsonProperty("startX") int posX,
                 @JsonProperty("startY") int posY,
                 @JsonProperty("orientation") EOrientation orientation) {
        this.posX = posX;
        this.posY = posY;
        this.orientation = orientation;
    }

    /**
     * Returns the RobotDTO whose values are
     * the ones of this Robot.
     * @return the RobotDTO of this.
     */
    public RobotDTO toRobotDTO() {
        return new RobotDTO(this);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public EOrientation getOrientation() {
        return orientation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Robot)) return false;
        final Robot robot = (Robot) o;
        return posX == robot.posX && posY == robot.posY && orientation == robot.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY, orientation);
    }
}