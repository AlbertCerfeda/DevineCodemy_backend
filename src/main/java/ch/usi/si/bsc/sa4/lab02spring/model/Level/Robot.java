package ch.usi.si.bsc.sa4.lab02spring.model.Level;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


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
}
