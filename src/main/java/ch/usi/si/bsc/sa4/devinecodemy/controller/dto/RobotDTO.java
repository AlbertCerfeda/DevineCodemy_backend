package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Robot;

public class RobotDTO {
    private int pos_x;
    private int pos_y;
    private String orientation;
    
    
    public RobotDTO (Robot robot) {
        pos_x = robot.getPos_x();
        pos_y = robot.getPos_y();
        orientation = robot.getOrientation().name();
    }
}
