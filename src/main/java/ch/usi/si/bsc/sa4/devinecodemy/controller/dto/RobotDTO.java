package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;

import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Robot;

public class RobotDTO {
    private final int posX;
    private final int posY;
    private final String orientation;
    
    
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
