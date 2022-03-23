package ch.usi.si.bsc.sa4.lab02spring.controller.dto.Tile;


import ch.usi.si.bsc.sa4.lab02spring.controller.dto.BoardDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.EActionDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;


public class LevelDTO {
    private String name;
    private String description;
    
    private BoardDTO board;
    private RobotDTO robot;
    
    private EActionDTO[] allowed_commands;
    
    public LevelDTO(Level level) {
        name = level.getName();
        description = level.getDescription();
        
        board = level.getBoard().toBoardDTO();
        
        robot = level.getRobot().toRobotDTO();
        
        EAction[] commands = level.getAllowed_commands();
        for(int i = 0; i<commands.length;i++) {
            allowed_commands[i] = commands[i].toEActionDTO();
        }
        
    }
}
