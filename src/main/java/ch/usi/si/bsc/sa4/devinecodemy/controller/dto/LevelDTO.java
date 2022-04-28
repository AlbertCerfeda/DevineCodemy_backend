package ch.usi.si.bsc.sa4.devinecodemy.controller.dto;


import java.util.List;
import java.util.stream.Collectors;

import ch.usi.si.bsc.sa4.devinecodemy.model.EAction;
import ch.usi.si.bsc.sa4.devinecodemy.model.Level.Level;


public class LevelDTO {
    private final String name;
    private final String description;
    
    private final BoardDTO board;
    private final RobotDTO robot;
    
    private final List<EActionDTO> allowed_commands;
    
    private final int maxCommandsNumber;

    private int levelNumber;
    
    public LevelDTO(Level level) {
        this.name = level.getName();
        this.description = level.getDescription();
        
        this.board = level.getBoard().toBoardDTO();
        
        this.robot = level.getRobot().toRobotDTO();
        
        this.maxCommandsNumber = level.getMaxCommandsNumber();

        this.levelNumber = level.getLevelNumber();
        
        allowed_commands = level.getAllowed_commands().stream().map(EAction::toEActionDTO).collect(Collectors.toList());
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public RobotDTO getRobot() {
        return robot;
    }

    public List<EActionDTO> getAllowed_commands() {
        return allowed_commands;
    }

    public int getMaxCommandsNumber() {
        return maxCommandsNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }
}
