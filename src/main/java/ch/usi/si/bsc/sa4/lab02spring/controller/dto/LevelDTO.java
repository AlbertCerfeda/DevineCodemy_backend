package ch.usi.si.bsc.sa4.lab02spring.controller.dto;


import ch.usi.si.bsc.sa4.lab02spring.controller.dto.BoardDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.EActionDTO;
import ch.usi.si.bsc.sa4.lab02spring.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.lab02spring.model.EAction;
import ch.usi.si.bsc.sa4.lab02spring.model.Level.Level;

import java.util.ArrayList;
import java.util.List;


public class LevelDTO {
    private final String name;
    private final String description;
    
    private final BoardDTO board;
    private final RobotDTO robot;
    
    private final List<EActionDTO> allowed_commands;
    
    public LevelDTO(Level level) {
        this.name = level.getName();
        this.description = level.getDescription();
        
        this.board = level.getBoard().toBoardDTO();
        
        this.robot = level.getRobot().toRobotDTO();

        this.allowed_commands = new ArrayList<>();
        List<EAction> commands = level.getAllowed_commands();
        for(EAction command : commands) {
            allowed_commands.add(command.toEActionDTO());
        }
    }
}
