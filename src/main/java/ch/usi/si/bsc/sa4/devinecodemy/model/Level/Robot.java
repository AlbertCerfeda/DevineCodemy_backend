package ch.usi.si.bsc.sa4.devinecodemy.model.Level;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EOrientation;

import java.util.Objects;


public class Robot {
    private final int posX;
    private final int posY;
    private final EOrientation orientation;

    @JsonCreator
    public Robot(@JsonProperty("start_x") int posX,
                 @JsonProperty("start_y") int posY,
                 @JsonProperty("orientation") EOrientation orientation) {
        this.posX = posX;
        this.posY = posY;
        this.orientation = orientation;
    }
    
    public RobotDTO toRobotDTO() { return new RobotDTO(this); }
    
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
    
    public EOrientation getOrientation(){ return orientation; }

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
