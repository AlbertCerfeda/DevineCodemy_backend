package ch.usi.si.bsc.sa4.lab02spring.model.Level;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;


public class Robot {
    private final int pos_x;
    private final int pos_y;
    private EOrientation orientation = EOrientation.UP;

    @JsonCreator
    public Robot(@JsonProperty("start_x") int pos_x,
                 @JsonProperty("start_y") int pos_y,
                 @JsonProperty("orientation") EOrientation orientation) {
        this.pos_x= pos_x;
        this.pos_y= pos_y;
        this.orientation = orientation;
    }
    
    public RobotDTO toRobotDTO() { return new RobotDTO(this); }
    
    public int getPos_x() {
        return pos_x;
    }

    public int getPos_y() {
        return pos_y;
    }
    
    public EOrientation getOrientation(){ return orientation; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Robot)) return false;
        Robot robot = (Robot) o;
        return pos_x == robot.pos_x && pos_y == robot.pos_y && orientation == robot.orientation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos_x, pos_y, orientation);
    }
}
