package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;


import java.util.ArrayList;
import java.util.List;

import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.BoardDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.EActionDTO;
import ch.usi.si.bsc.sa4.devinecodemy.controller.dto.RobotDTO;
import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;


public class LevelDTO {
    private final String name;
    private final String description;
    
    private final BoardDTO board;
    private final RobotDTO robot;
    
    private final List<EActionDTO> allowed_commands;

    private final String src;
    
    private final int maxCommandsNumber;
    
    public LevelDTO(Level level) {
        this.name = level.getName();
        this.description = level.getDescription();
        
        this.board = level.getBoard().toBoardDTO();
        
        this.robot = level.getRobot().toRobotDTO();

        this.allowed_commands = new ArrayList<>();
        
        this.maxCommandsNumber = level.getMaxCommandsNumber();
        
        List<EAction> commands = level.getAllowed_commands();
        for(EAction command : commands) {
            allowed_commands.add(command.toEActionDTO());
        }

        this.src = level.getSrc();
    }
}
