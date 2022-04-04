package ch.usi.si.bsc.sa4.lab02spring.model.Level;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EOrientation;


public class Robot {
    private int pos_x;
    private int pos_y;
    private EOrientation orientation = EOrientation.UP;

    public Robot() {
    }

    public Robot(int start_x, int start_y, EOrientation orientation) {
        this.pos_x= start_x;
        this.pos_y= start_y;
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
